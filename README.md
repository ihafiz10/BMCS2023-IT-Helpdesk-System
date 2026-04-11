# TARUMT IT Helpdesk Ticket Management System

## 📌 Overview
This project is a console-based IT Helpdesk Ticket Management System developed using Java.  
It simulates a real-world helpdesk environment where customers can submit issues, staff can manage tickets, and managers can monitor system performance.

---

## 🎯 Objectives
- To apply Object-Oriented Programming (OOP) concepts
- To design a structured ticket management workflow
- To simulate real-life IT support operations

---

## 🧩 System Modules

### 👤 Customer Module
- Sign up and login system
- Create support tickets with description and priority
- View personal tickets and their status
- Close tickets after issue resolution
- Provide feedback (rating and comments) for closed tickets

---

### 🛠️ Staff Module
- Staff login with Staff ID (S1 – S4)
- View all tickets or only assigned tickets
- Update ticket status (Open, Pending, In Progress, Resolved, Closed)
- Add responses to tickets
- Search and filter tickets

---

### 🧑‍💼 Manager Module
- Monitor ticket statistics (Open, Closed, etc.)
- Identify critical (high priority) tickets
- Assign and reassign tickets to staff
- View all tickets in the system
- Generate monthly report
- View customer feedback
- Calculate average response time

---

## 🔧 Features
- Ticket creation with auto-generated ID
- Ticket assignment system
- Ticket response history (multiple responses stored)
- Input validation (username, password, priority, rating)
- Feedback system with duplicate prevention
- Role-based access (Customer, Staff, Manager)

---

## 🧠 OOP Concepts Used
- **Encapsulation**: Data hidden using private fields and getters/setters
- **Inheritance**: `Customer` and `Staff` extend `User`
- **Abstraction**: Service classes handle business logic
- **Polymorphism**: Method usage across different classes

---

## 🗂️ Project Structure
src/
└── com/mycompany/assignment/
├── Main.java
├── Customer.java
├── Staff.java
├── Ticket.java
├── TicketService.java
├── Feedback.java
├── CustomerModule.java
├── StaffModule.java
├── TicketManager.java

---

## ▶️ How to Run
1. Open the project in IntelliJ IDEA or any Java IDE
2. Run `Main.java`
3. Follow the menu instructions in the console

---

## ⚠️ Limitations
- Data is stored in memory using ArrayList (no database)
- System resets when program is restarted

---

## 🚀 Future Improvements
- Integrate database using JDBC
- Add graphical user interface (GUI)
- Improve email validation and security
- Implement automatic ticket prioritization

---

## 👥 Developed By
- Sukah  
- Adeline  
- Chloe  
- Ikhwan  

---

## 📅 Date
11th April 2026
