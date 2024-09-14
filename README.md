# JDP20240901 (Online Shop API)

## Table of Contents
1. [Project Overview](#project-overview)
2. [Technologies Used](#technologies-used)
3. [Requirements](#requirements)
4. [Installation](#installation)
5. [Configuration](#configuration)
6. [Running the Application](#running-the-application)
7. [API Endpoints](#api-endpoints)
8. [Testing](#testing)
9. [Contributors](#contributors)
10. [Future development](#future)

## Project Overview
The **JDP240901 (Online Shop API)** is a backend service developed using **Java**, 
**Spring Boot**, and **Hibernate** to support an e-commerce platform. It allows managing products, 
products groups, orders, users, shopping carts, show user operation history and generate one-hour token for authorization. The API can be integrated with frontend 
applications and other services to provide a seamless shopping experience.

### Features:
- **Products Management**: Product catalogue with create, read, update, and delete (CRUD) operations.
- **Products groups Management**: Group catalogue with create, read and update operations.
- **Cart Management**: Create carts, add items to carts, read and delete items from carts, and create orders from carts.
- **Orders Management**: Place orders, read orders, manage order statuses, and delete orders.
- **Users Management**: Users catalogue with create and profile status management.
- **Authentication and Authorization**: Generate **JWT tokens** for user authorization.

## Technologies Used
- **Java 17**
- **Spring Boot 3.x**
- **Hibernate** as ORM
- **MySQL** as the database
- **Gradle** for build and dependency management
- **Lombok** for reducing boilerplate code
- **JUnit 5** for unit testing
- **Swagger** for documentation

## Requirements
Before running the application, ensure you have the following installed:
- **Java 17** or higher
- **Gradle 7.x** or higher
- **MySQL** database
- A tool like **Postman** to test the API endpoints

## Installation

1. Clone the repository:
```
   git clone https://github.com/piotr-grosicki/project-jdp-2409-01
```
2. Build the project with Gradle:
```
   ./gradlew build
```
3. Ensure you have a running database and create a new database for the application:
```
   CREATE DATABASE eshop_project.db;
```
##Configuration

Configure the database and application properties in 

src/main/resources/application.properties:

```
spring:
  datasource:
    url: 'jdbc:mysql://localhost:3306/eshop_project?allowPublicKeyRetrieval=true&useSSL=false'
    username: your_db_user
    password: your_db_password
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
```

###Running the Application

You can run the application using Gradle.
The application will be available at http://localhost:8080 by default.
Swagger documentation will be available at http://localhost:8080/swagger-ui/index.html#/.

###API Endpoints

**Products:**
- GET /v1/products – Get all products.
- GET /v1/products/{productId} – Get product by ID.
- POST /v1/products – Add a new product.
- PUT /v1/products/modify/{productId} – Update product details by ID (authenticated users).
- DELETE /v1/products/{productId} – Delete a product by ID.

**Groups:**
- GET /v1/groups - Get all groups.
- GET /v1/groups/{groupId} - Get group by ID.
- POST /v1/groups - Add a new group.
- PUT /v1/groups/modify/{groupId} - Update group details by ID (authenticated users).

**Carts:**
- POST /v1/carts – Add an empty cart.
- GET /v1/carts/{cartId}/items – Get current cart contents by ID.
- POST /v1/carts/{cartId}/items - Add item to the cart by ID.
- DELETE /v1/carts/{cartId}/items/{productId} – Remove item by product ID from the cart by cart ID.
- POST /v1/carts/{cartId}/orders - Create order from cart by ID.

**Orders:**
- GET /v1/orders – Get all orders.
- GET /v1/orders/{orderId} – Get order by ID.
- POST /v1/orders - Add a new order.
- PUT /v1/orders/modify/{orderId} - Update order details by ID (authenticated users).
- DELETE /v1/orders/{orderId} - Delete order by ID.

**Users:**
- POST /v1/users – Add a new user.
- PATCH /v1/users/modify/{userId}/status – Update user status by ID (authenticated users).

**Authentication**
- POST /v1/users/key - Generate a one-hour token for the user.

##Testing
To run tests for the project, use the following command:
```
  ./gradlew test
```
##Contributors
- Piotr Grosicki(PM) - [GitHub](https://github.com/piotr-grosicki)
- Bogdan Brandys - [GitHub](https://github.com/BogdanBrandys)
- Krzysztof Fit – [GitHub](https://github.com/krzysztoffit)
- Roman Khudoley – [GitHub](https://github.com/cdroma)
- Marta Kowalczyk - [GitHub](https://github.com/martkow)


##Future development
The Online Shop API has been designed with scalability in mind and is open for further development.

This `README.md` provides a complete overview of the API, with information on installation, configuration, and usage, designed for a project that uses **Gradle** as the build tool. You can adjust the file to reflect the specific details of your project as necessary.


