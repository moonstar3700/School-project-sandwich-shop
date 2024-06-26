# Sandwich Shop Application

This project is a Java application for a sandwich shop, designed with three distinct views for different roles: customer/cashier, kitchen staff, and manager. The focus of this project was to implement the correct use of Object-Oriented Programming (OOP) principles.

## Project Overview

The Sandwich Shop application is built using Java and JavaFX for the user interface. The application demonstrates a well-structured approach to OOP by implementing various design patterns and adhering to MVC architecture.

### Features

- **Customer/Cashier View:**
  - Interface for placing orders and handling transactions.

- **Kitchen Staff View:**
  - Interface for managing and processing orders in the kitchen.

- **Manager View:**
  - Interface for managing inventory, and viewing reports.

### OOP Design Patterns Used

The project incorporates the following OOP design patterns:

1. **Observable:** For implementing the observer pattern to allow objects to be notified of changes.
2. **Strategy:** For defining a family of algorithms, encapsulating each one, and making them interchangeable.
3. **Factory:** For creating objects without specifying the exact class of object that will be created.
4. **Facade:** For providing a simplified interface to a complex subsystem.
5. **Singleton:** For ensuring a class has only one instance and providing a global point of access to it.
6. **State:** For allowing an object to alter its behavior when its internal state changes.
7. **MVC (Model-View-Controller):** For separating the application logic into three interconnected components.
8. **Template:** For defining the skeleton of an algorithm in an operation, deferring some steps to subclasses.
9. **Enum:** For defining a fixed set of constants.
10. **Properties:** For managing configuration and setting properties.
11. **Reflection:** For inspecting and manipulating classes, methods, and fields at runtime.

## Setting Up and Running the Application

### Prerequisites

- Ensure you have Java Development Kit (JDK) installed.
- Ensure you have a JavaFX library included in your project dependencies.
- It is recommended to use an Integrated Development Environment (IDE) such as IntelliJ IDEA or Eclipse for easy project setup and management.

### Steps to Run the Application

1. **Clone the Repository:**
   - Clone the project repository to your local machine.

2. **Open the Project in an IDE:**
   - Open the project in your preferred IDE (e.g., IntelliJ IDEA or Eclipse).

3. **Configure JavaFX:**
   - Ensure that JavaFX is properly configured in your IDE. Add the JavaFX library to your project dependencies if necessary.

4. **Build the Project:**
   - Build the project to ensure all dependencies are resolved and there are no compilation errors.

5. **Run the Application:**
   - Set the main class (the entry point of the application) and run the application from your IDE.
   - The application will launch with three views for the customer/cashier, kitchen staff, and manager.

### Example Usage

- **Customer/Cashier View:**
  - Place orders for sandwiches.

- **Kitchen Staff View:**
  - View incoming orders, and mark orders as in progress or completed.

- **Manager View:**
  - Manage inventory, and view sales reports.
