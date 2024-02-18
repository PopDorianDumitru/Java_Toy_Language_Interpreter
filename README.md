Java Toy Language Interpreter
Welcome to the Java Toy Language Interpreter! This project aims to provide a simple yet extensible interpreter for a toy language written in Java. The interpreter supports the execution of programs written in the toy language, and it includes branches for different synchronization mechanisms for threads as well as procedures.

Table of Contents
Getting Started
Toy Language Features
Branches
Threads
Procedures
Usage
Examples
Contributing
License
Getting Started
To get started with the Java Toy Language Interpreter, follow these steps:

Clone the repository:

bash
Copy code
git clone https://github.com/your-username/toy-language-interpreter.git
cd toy-language-interpreter
Build the project:

bash
Copy code
./gradlew build
Explore the branches for different features:

bash
Copy code
git checkout threads-branch
or

bash
Copy code
git checkout procedures-branch
Toy Language Features
The toy language supported by this interpreter includes basic programming constructs such as variables, loops, conditionals, and more. It is designed to be simple and easy to understand, making it suitable for educational purposes.

Branches
Threads Branch
The threads-branch includes synchronization mechanisms for threads. It allows for concurrent execution of multiple threads and provides synchronization features to avoid race conditions and ensure thread safety.

To switch to the threads branch, use the following command:

bash
Copy code
git checkout threads-branch
Procedures Branch
The procedures-branch introduces support for procedures and functions in the toy language. It allows modularization of code by defining reusable blocks of code.

To switch to the procedures branch, use the following command:

bash
Copy code
git checkout procedures-branch
Usage
To use the Java Toy Language Interpreter, you can run it from the command line and provide the path to your toy language program as an argument:

bash
Copy code
java -jar toy-language-interpreter.jar path/to/your/program.toy
Examples
Check the examples directory for sample programs written in the toy language. Each branch may have specific examples demonstrating the features introduced in that branch.

Contributing
Contributions are welcome! If you have ideas for improvements, new features, or bug fixes, please open an issue or submit a pull request.
