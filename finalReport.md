# Final Project Report: Library Management System
### 1. Deliverable 1: Basic Structure
   - Designed UML Diagram: Created a class diagram to map out the system structure
   - Built Base project structure and packages: added gitignore file, Lombok and Junit in pom.xml file, etc.
   - created domain classes and constructors
   - Implemented the Reportable interface for the Admin class
   
### 2. Deliverable 2: Core Implementation
   - Borrowing Rules: using Validation methods limit Students to borrow up to 5 books and Teachers up to 10 total items
   - Added Exception Handling: Created custom exceptions like ItemUnavailableException to prevent users from borrowing unavailable or lost items
   - Built Search method: Developed both recursive and stream search methods.
   - enabled Sorting: Implemented distinct sorting strategies using Java Collections to organize the User and Item lists

### 3. Deliverable 3: Testing & Data Consistency
   - CSV Integration: Programmed the system to initialize data by loading from CSV files, and allowed Admins to back up current data into items.csv and users.csv
   - Ensured ID Consistency: created read-only getters for items/users lists and custom addItem()/addUser() methods 
   - Wrote JUnit Tests: unit tests for all methods for various possible errors or crashes

### 4. Deliverable 4: Final Tasks
   - Updated the Deliverable 1 Class Diagram: class diagram perfectly matches the final Java code
   - ensured all Validation helper methods were properly documented with JavaDoc
   - Completed README and user Guide with screenshots
   - removed all TODO text, unnecessary comments and corrected formatting