# http-pulse

![http-pulse Logo](/assets/logo.png)

**http-pulse** is an innovative and powerful command-line HTTP client designed to simplify and enhance API testing. 
Leveraging its own custom-built **Pulse Language**, http-pulse aims to replace the need for tools like Postman or 
IntelliJ IDEA HTTP Client, providing a seamless and efficient experience for developers and testers.

## Demo

- pulse init
- pulse 0
- pulse 1
- pulse 2

![](/assets/http-pulse-demo.gif)

## Features

- **Full-fledged REST Client**: Execute GET, POST, PUT, DELETE, PATCH and other HTTP methods with ease.
- **Pulse Language**: Our custom language simplifies API testing, making it intuitive and efficient.
- **CLI Convenience**: Run http-pulse as a standalone application from your terminal.
- **Scripting and Automation**: Automate your API tests and integrate them into your CI/CD pipelines effortlessly. (Coming up...)
- **Response Handling**: Easily parse and manipulate API responses. (Coming up..)
- **Environment Management**: Manage different environments for your API tests seamlessly. (Coming up...)
- **Extensive Documentation**: Comprehensive documentation to help you get started and master http-pulse. (Coming up...)

## Why http-pulse?

http-pulse is designed to be a versatile and powerful tool for developers and testers who need a robust solution for 
API testing. By using the custom Pulse Language, you can:

- **Streamline Your Workflow**: No more switching between multiple tools. Test your APIs directly from the command line.
- **Increase Efficiency**: Write and execute tests faster with our intuitive language and powerful features.
- **Enhance Collaboration**: Share your tests and results easily with your team via .pulse files.
- **Boost Productivity**: Automate repetitive tasks and focus on what really matters—building great APIs.

## Getting Started

### Installation

Currently the CLI needs to be installed manually. If http-pulse becomes mature enough an automatic build distribution
can be implemented.

* Clone the project
```
git clone https://github.com/SkerdjanG/http-pulse.git
```

* Build
```
mvn clean install -DskipTests
```

* Run **http-pulse-0.0.1-SNAPSHOT.jar**
```
java -jar /target/http-pulse-0.0.1-SNAPSHOT.jar
```

* http-pulse cli will start executing in your terminal. Run **help** to get to know with the commands.
```
shell:> help
```

### Usage

See the [documentation](https://github.com/SkerdjanG/http-pulse/wiki) for more details on the commands available.
