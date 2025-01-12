# Simple HTTP Server in Java

Welcome to my **Simple HTTP Server** project! This application is a lightweight HTTP server built using Java and Maven. It demonstrates modern development practices, including dependency management, static content serving, unit testing, and the use of libraries like Jackson and Lombok.

---

## Features
- **Static Page Serving**: Serves a single static HTML page.
- **Annotations with Lombok**: Simplifies boilerplate code such as getters, setters, and constructors.
- **Thorough Unit Tests**: Comprehensive test suite using JUnit to ensure code quality and reliability.
- **Maven for Dependency Management**: Handles libraries and project build.
- **Version Control**: GitHub is used for version control and collaboration.

---

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Setup](#setup)
3. [Usage](#usage)
4. [Technologies](#technologies)
5. [Running Unit Tests](#running-unit-tests)
6. [Project Structure](#project-structure)
7. [Contributing](#contributing)
8. [License](#license)

---

## Prerequisites
Before you start, ensure you have the following installed:
- [Java 17+](https://jdk.java.net/)
- [Maven 3.8+](https://maven.apache.org/)
- [Git](https://git-scm.com/)

---

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/simple-http-server.git
   cd simple-http-server
   ```

2. Build the project with Maven:
   ```bash
   mvn clean install
   ```

3. Run the server:
   ```bash
   mvn exec:java -Dexec.mainClass="org.example.HttpServer"
   ```

4. The server will be running at [http://localhost:8080](http://localhost:8080).

---

## Usage

### Serving the Static Page
Once the server is running, navigate to:
[http://localhost:8080](http://localhost:8080)

You will see a single static HTML page being served by the server.

---

## Technologies

| Technology      | Purpose                                      |
|-----------------|----------------------------------------------|
| **Java**        | Core programming language                   |
| **Maven**       | Dependency management and build automation  |
| **GitHub**      | Version control and collaboration           |
| **Jackson**     | JSON parsing and serialization              |
| **Lombok**      | Boilerplate code reduction                  |
| **JUnit 5**     | Unit testing framework                      |

---

## Running Unit Tests

To run all unit tests, execute:
```bash
mvn test
```

Tests are written using **JUnit 5** and include:
- Parsing HTTP requests.
- Validating edge case handling.

---

## Project Structure

```
├── Request.txt
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── org
│   │   │       └── example
│   │   │           ├── HttpServer.java
│   │   │           ├── http
│   │   │           │   ├── HttpMessage.java
│   │   │           │   ├── HttpMethod.java
│   │   │           │   ├── HttpParser.java
│   │   │           │   ├── HttpParsingException.java
│   │   │           │   ├── HttpRequest.java
│   │   │           │   └── HttpStatusCode.java
│   │   │           └── httpserver
│   │   │               ├── config
│   │   │               │   ├── Configuration.java
│   │   │               │   ├── ConfigurationManager.java
│   │   │               │   └── HttpConfigurationException.java
│   │   │               ├── core
│   │   │               │   ├── HttpConnectionWorkerThread.java
│   │   │               │   └── ServerListenerThread.java
│   │   │               └── util
│   │   │                   └── JsonParser.java
│   │   └── resources
│   │       └── http.json
│   └── test
│       └── java
│           └── org
│               └── example
│                   └── http
│                       └── HttpParserTest.java
└── target
    ├── classes
    │   ├── http.json
    │   └── org
    │       └── example
    │           ├── HttpServer.class
    │           ├── http
    │           │   ├── HttpMessage.class
    │           │   ├── HttpMethod.class
    │           │   ├── HttpParser.class
    │           │   ├── HttpParsingException.class
    │           │   ├── HttpRequest.class
    │           │   └── HttpStatusCode.class
    │           └── httpserver
    │               ├── config
    │               │   ├── Configuration.class
    │               │   ├── ConfigurationManager.class
    │               │   └── HttpConfigurationException.class
    │               ├── core
    │               │   ├── HttpConnectionWorkerThread.class
    │               │   └── ServerListenerThread.class
    │               └── util
    │                   └── JsonParser.class
    ├── generated-sources
    │   └── annotations
    ├── generated-test-sources
    │   └── test-annotations
    └── test-classes
        └── org
            └── example
                └── http
                    └── HttpParserTest.class
```

---

## Contributing

We welcome contributions! To contribute:
1. Fork the repository.
2. Create a new branch: `git checkout -b feature-name`.
3. Commit your changes: `git commit -m 'Add feature name'`.
4. Push to the branch: `git push origin feature-name`.
5. Open a pull request.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

Happy coding! 🚀

