# Consecutive Character Conversions Processor

A Java library for processing strings containing consecutive identical characters with advanced removal and replacement strategies.

[![Java 17](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()
[![Test Coverage](https://img.shields.io/badge/coverage-100%25-brightgreen.svg)]()

## Overview

This library provides efficient processing of strings to handle sequences of 3 or more consecutive identical characters through two primary strategies:
- **Removal Strategy**: Removes any sequence of 3 or more consecutive identical characters
- **Replacement Strategy**: Replaces any sequence of 3 or more consecutive identical characters with the preceding character in the alphabet

The processing continues iteratively until no more qualifying sequences exist, handling cases where removal or replacement creates new sequences that need processing.

## Features

- **High Performance**: Optimized algorithms with O(n) time complexity
- **Java 17+ Support**: Leverages modern Java features (records, sealed interfaces, enhanced switch)
- **Robust Validation**: Ensures input contains only valid lowercase alphabetic characters
- **Comprehensive Testing**: 100% test coverage with extensive test cases
- **Clean API**: Simple, intuitive interface for easy integration
- **Interactive CLI**: Included command-line interface for quick testing

## Examples

### Removal Strategy
String result = StringProcessor.processWithRemoveStrategy("aabcccbbad");
// Result: "d"
Processing steps:
1. "aabcccbbad" → "aabbbad" (remove "ccc")
2. "aabbbad" → "aaad" (remove "bbb")
3. "aaad" → "d" (remove "aaa")

### Replacement Strategy
String result = StringProcessor.processWithReplaceStrategy("abcccbad");
// Result: "d"
Processing steps:
1. "abcccbad" → "abbbad" (replace "ccc" with "b")
2. "abbbad" → "aaad" (replace "bbb" with "a")
3. "aaad" → "d" (replace "aaa" by removing it, as 'a' has no preceding character)

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+ (for building from source)

### Installation

#### From Source
# Clone the repository
git clone https://github.com/mindrock/consecutive_char_processor.git
cd consecutive_char_processor

# Build the project
```bash
mvn clean package
```

# Install to local Maven repository
```bash
mvn clean install -U -f pom.xml
```

#### Maven Dependency

Add this dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>org.stringtools</groupId>
    <artifactId>consecutive_char_processor</artifactId>
    <version>1.0.0</version>
</dependency>
```
### Usage

#### Running the CLI
After building the project, you can use the interactive command-line interface:
```bash
java -jar target/consecutive-character-processor-1.0.0.jar
```
#### Interactive Workflow

- 1 Welcome Screen: The application starts with a welcome message explaining its functionality.
- 2 Input String: When prompted, enter the string you want to process:
```plaintext
Enter the string to process: [your-string-here]
```
- 3 Select Strategy: Choose between the two processing strategies:
```plaintext
Choose processing strategy:
1 - Remove sequences of 3+ consecutive characters
2 - Replace sequences of 3+ consecutive characters with previous letter
Enter your choice (1 or 2): [1/2]
```

- 4 View Results: The application displays:
```plaintext
- Original input string
- Selected processing strategy
- Transformed result
```

- 5 Continue or Exit: After viewing results, choose to process another string:
```plaintext
- Process another string? (y/n): [y/n]
```

## Example Sessions

### Example 1: Using Removal Strategy
```plaintext
==============================================
      Consecutive Character Conversions Processor   
==============================================
This tool processes strings by handling sequences
of 3 or more consecutive identical characters.
Only lowercase letters (a-z) are supported.

Enter the string to process: aabcccbbad

Choose processing strategy:
1 - Remove sequences of 3+ consecutive characters
2 - Replace sequences of 3+ consecutive characters with previous letter
Enter your choice (1 or 2): 1

Processing Results:
-------------------
Original string: aabcccbbad
Strategy used:   ConsecutiveCharRemoverStrategy
Processed string: d
-------------------

Process another string? (y/n): n

Thank you for using Consecutive Character Processor!

```
### Example 2: Using Replacement Strategy

```plaintext
Enter the string to process: abcccbad

Choose processing strategy:
1 - Remove sequences of 3+ consecutive characters
2 - Replace sequences of 3+ consecutive characters with previous letter
Enter your choice (1 or 2): 2

Processing Results:
-------------------
Original string: abcccbad
Strategy used:   ConsecutiveCharReplacerStrategy
Processed string: d
-------------------

Process another string? (y/n): n

```

## Technical Details

### Architecture

The library uses a strategy pattern to encapsulate the different processing algorithms:

- `StringProcessingStrategy`: Sealed interface defining the processing contract
- `ConsecutiveCharRemoverStrategy`: Implementation that removes qualifying sequences
- `ConsecutiveCharReplacerStrategy`: Implementation that replaces qualifying sequences
- `StringProcessor`: Factory class providing easy access to strategies

### Performance Characteristics

- Time Complexity: O(n) where n is the length of the input string
- Space Complexity: O(n) in the worst case for storing character sequences
- Efficient handling of very long strings (tested with 1,000,000+ characters)

### Java 17 Features Used

- **Sealed Interfaces**: `StringProcessingStrategy` is a sealed interface with restricted implementations
- **Records**: `CharacterSequence` uses a record for immutable data storage
- **Enhanced `switch` Expressions**: Used in input handling
- **`String.repeat()`**: For efficient string construction
- **Varargs with `TextBlocks`**: For cleaner test cases
- **Try-with-resources**: In the CLI implementation

## Use Cases

- **Text Processing**: Cleaning up repeated characters in user input
- **Data Validation**: Ensuring strings meet specific formatting requirements
- **Gaming**: Processing character sequences in word games
- **Natural Language Processing**: Normalizing text by reducing character repetitions
- **Password Processing**: Identifying and handling repeated characters in passwords

## Testing

The library includes an extensive test suite with 100% code coverage:
# Run tests
mvn test

# Generate coverage report
mvn jacoco:report
# Report will be available at target/site/jacoco/index.html
Tests cover:
- Basic functionality
- Edge cases (empty strings, null values, minimal/maximal sequences)
- Error conditions and invalid inputs
- Performance with large inputs
- Chain processing scenarios

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

Please ensure your code:
- Follows the existing coding style
- Includes tests for new functionality
- Maintains 100% test coverage
- Updates documentation as needed

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
    