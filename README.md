# GStock - Stock Management Application

GStock is a comprehensive stock management application built with a Spring Boot backend and an Angular frontend. It provides a user-friendly interface for managing companies, contacts, inventory items, categories, and quotes.

## Features

*   **Company Management:** Create, read, update, and delete company information.
*   **Contact Management:** Manage contact details associated with companies.
*   **Inventory Management:** Track inventory items, including categories and quantities.
*   **Quote Management:** Generate and manage product quotes.
*   **User Authentication and Authorization:** Secure access with user registration, login, and role-based permissions using Spring Security and JWT.

## Technologies Used

### Backend (Spring Boot)

*   **Java:** Version 17
*   **Spring Boot:** Version 3.4.2
*   **Maven:** Build tool
*   **Spring Data JPA:** Data persistence
*   **Spring Security:** Authentication and authorization
*   **JWT (Java-JWT):** Version 4.5.0 for secure token-based authentication
*   **H2 Database:** In-memory database (for development)

### Frontend (Angular)

*   **TypeScript**
*   **Angular:** Version 19.0.0
*   **Angular CLI:** Version 19.0.6
*   **PrimeNG:** UI component library (Version 19.0.5)
*   **Tailwind CSS:** Styling

## Development Setup

### Backend (Spring Boot)

1.  **Prerequisites:**
    *   Java Development Kit (JDK) version 17
    *   Maven

2.  **Build and Run:**

    ```bash
    cd backend
    mvn spring-boot:run
    ```

### Frontend (Angular)

1.  **Prerequisites:**
    *   Node.js
    *   npm (Node Package Manager) or yarn

2.  **Installation:**

    ```bash
    cd frontend
    npm install
    ```

3.  **Run Development Server:**

    ```bash
    npm run start
    # or
    ng serve
    ```

    The frontend application will be accessible at `http://localhost:4200`.

## Configuration

### Backend

*   **application.properties:** Located in [resources](https://github.com/Thescattypotty/GStock/blob/main/backend/src/main/resources/application.properties).  Configure database settings, JWT secrets, and other application properties.

### Frontend

*   **angular.json:** Located in [frontend](https://github.com/Thescattypotty/GStock/blob/main/frontend/angular.json). Configure application settings, build options, and environment variables.

## License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/Thescattypotty/GStock/blob/main/LICENSE) file for details.

## Developer

*   Bennis Yahya (Sênshî) - [https://bennis-yahya.vercel.app](https://bennis-yahya.vercel.app) - bennis-yahya@outlook.com