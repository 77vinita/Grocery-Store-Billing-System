# Grocery Store Billing System 🛒📜

## Overview 🚀
The **Grocery Store Billing System** is a Java Spring Boot-powered application designed to streamline the billing process for walk-in customers. The system is **admin-controlled**, allowing easy inventory and transaction management.

## Features 🔹
✅ Add, Update, Delete products from inventory  
✅ Generate Bills dynamically based on selected products  
✅ Automatic Inventory Update upon bill generation  
✅ Dynamic Price Calculation as quantity increases  
✅ Delete Orders from the order list if needed  
✅ Save Bills for record-keeping  
✅ Secure Payment Processing (currently in test mode)  

## Tech Stack 🔗
📌 **Backend:** Java Spring Boot  
📌 **Frontend:** HTML, CSS, JavaScript, Thymeleaf  
📌 **Database:** MySQL  
📌 **IDE Used:** Eclipse  
📌 **Spring Boot Initialization:** Spring Initializr  

## Dependencies Added 🛠️
While initializing the Spring Boot project using **Spring Initializr**, the following dependencies were added:
- **Spring Web** (for building web applications)
- **Spring Boot DevTools** (for development and debugging)
- **Spring Data JPA** (for database interaction)
- **MySQL Driver** (for MySQL database connectivity)
- **Thymeleaf** (for templating engine)
- **Spring Security** (for authentication and authorization)

## Installation & Setup 🏗️
1. **Clone the repository**:
   ```sh
   git clone https://github.com/YOUR_GITHUB_USERNAME/Grocery-Store-Billing-System.git
   ```
2. **Open in Eclipse** and import as a **Maven Project**.
3. **Configure the Database:**
   - Create a **MySQL database** named `grocery_billing_system`.
   - Update `application.properties` with your MySQL credentials.
4. **Run the application**:
   - Navigate to the project directory.
   - Run the Spring Boot application from Eclipse or using:
     ```sh
     mvn spring-boot:run
     ```
5. **Access the system:**
   - Open **http://localhost:8080** in your browser.
   - Use the following credentials to log in:
     - **Username:** `admin`
     - **Password:** `admin123`

## License 📜
This project is open-source and available under the [MIT License](LICENSE).

## Contributing 🤝
Feel free to contribute by forking the repository, making changes, and submitting a pull request.
