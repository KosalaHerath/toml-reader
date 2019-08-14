[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# TOML Reader for JAVA

Welcome to the TOML configuration file reader sample.


This sample uses [tom4j](https://github.com/mwanji/toml4j) TOML reader and toml4j is a TOML 0.4.0 parser for Java.

### How to run the program

Run following command in the distriution home directory.

`$ mvn clean install`

After bulding the executables go to target directory

`<TOML-READER-HOME>/target/`

Run executable with configuration file path

`$ java -jar toml-reader-0.1.0.jar --file <PATH-TO-TOML-FILE>`

This will show the needed details in the configuration file. This is implemented to exract details from sample configuration file which is about university details as following example.

### Example Run

As an example following command will show example TOML file's data. (example TOML file located at "<TOML-READER-HOME>/resources"

`$ java -jar toml-reader-0.1.0.jar --file university-details.config`
