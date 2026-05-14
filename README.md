# Library Management System - Java Project
### Description
The Library Management System is a Java-based application that allows users to borrow, return, and search 
for various library items Built using Object-Oriented Programming (OOP) principles such as inheritance,
polymorphism, abstract classes, and interfaces, the system handles library operations while utilizing 
exception handling and standard Java collections

### Core Features:
##### User Management: Supports three distinct user roles: Students, Teachers, and Admins
The system enforces specific borrowing limits, such as a maximum of 5 books for 
Students and up to 10 total items for Teachers.

##### Item Management: Manages an inventory of Books, DVDs, and Magazines
It tracks multiple copies of items and monitors their status (e.g., IN_STORE, BORROWED, or LOST).

##### Advanced Searching: 
 Includes both recursive and stream search methods that allow users to find 
items by title or author. The search is case-insensitive and filters out duplicate copies
to return a clean result list.

##### Data Persistence: 
Supports loading initial system data from CSV files and allows Admin 
users to back up current users and item inventories directly into CSV files.

##### Admin Reporting: 
Admins can generate comprehensive reports of all items and their statuses 
by using the Reportable interface

### Installation
This project is built and managed using Maven
Clone the public Git repository to your local machine
Ensure you have Java and Maven installed on your system.
Verify that your csv files exist. The system expects default CSV files
to be located at src/main/resources/items.csv and src/main/resources/users.csv.
Run standard Maven commands (e.g., mvn clean install) to compile the project and 
download dependencies such as JUnit (for unit testing) and Lombok.

### Usage
The primary entry point for managing the system is the LibraryManagementSystem class

1. Initializing and Loading Data You can populate the system's inventory by calling the LibraryManagementSystem's
add methods:
   LibraryManagementSystem system = new LibraryManagementSystem();
   system.loadCsvFiles();
   system.addStudent(...);
   system.addBook(...);
   ... 
2. Borrowing and Returning Items Users can interact with items, but the system will validate the 
operation using the Validation class and throw exceptions like ItemUnavailableException 
if a limit is reached or the item is missing.

3. Searching the Library You can search the system using either the recursive or stream methods
   Both methods return a list containing one instance of a matching item, ignoring case.

4. Admin Tools Admin users have exclusive access to generate console reports and back up data by calling
   admin.generateReport(system); // Prints inventory report
   admin.backupData(system);     // Saves current lists to CSV

### Authors
   Liam Bohdjalian (Student ID: 2535004)