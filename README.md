# Hotel Management System API

## Introduction
This project represents the APIs for a Hotel Management System. These APIs can be used to perform various operations related to hotel management, such as creating accounts, logging in, making reservations, seeing available rooms, paying for reservations, and viewing invoices.

The system has a Role-Based Access Control (RBAC) system, where there are two roles: **ADMIN** and **CUSTOMER**. Admins can perform all the actions that customers can, as well as additional tasks such as adding new rooms, floors, changing room classes, and modifying room features and pricing.

## Key Features
- **User Management**:
  - Registration and login for customers and admins
  - Role-based access control
- **Hotel Management**:
  - Managing rooms, floors, and room classes
  - Updating room features and pricing
- **Reservation Management**:
  - Allowing customers to make, view, and pay for reservations
  - Tracking reservation status and details
- **Payment Processing**:
  - Handling various payment methods
  - Tracking payment status

## Resources
The system includes the following main resources:

- **Feature**: Represents the available features for each room class.
- **Floor**: Represents a specific level or story in the hotel building.
- **Payment**: Represents a payment made for a reservation.
- **Reservation**: Represents a booking made by a user.
- **Role**: Represents a user role in the system (ADMIN or CUSTOMER).
- **Room**: Represents a specific room within the hotel.
- **RoomClass**: Represents a class or type of room available in the hotel.
- **RoomClassFeature**: Represents a relationship between a room class and a feature.
- **RoomStatus**: Represents the status of a room (e.g., available, occupied, etc.).
- **UserEntity**: Represents a user in the system.



## Installation and Deployment
### Running the Application
1. Ensure you have Java Development Kit (JDK version 21 or higher) installed on your machine.
2. Clone the repository or download the source code for the application.
3. Open a terminal or command prompt and navigate to the root directory of the application.
4. Build the application by running the following command:
   
mvnw clean install
5. Start the application by running the following command:
mvnw spring-boot:run
6. The application will start and listen on a specific port (usually 8080). You can access the application by opening `http://localhost:8080`.

### Running with Docker
1. Ensure you have Docker installed on your machine.
2. Build the Docker image using the provided Dockerfile:
mvnw clean install docker-compose build docker-compose up

3. Alternatively, you can use a pre-built image from the provided repository:
   
docker run <image-name-or-image-id>


## Lessons Learned
Throughout this project, we learned the following:

1. Importance of Role-Based Access Control (RBAC) in application security and how to implement it using JWT tokens.
2. Best practices for building RESTful APIs using Spring Boot, including following the HATEOAS principle and implementing pagination.
3. Considerations for database design and handling complex relationships between entities.
4. Creating and deploying Docker images for the application.
5. Collaboration and version control using Git in a team environment.

## Contribution
This project was developed as part of the Web Services class at Birzeit University, taught by Dr. Mohammad Kharmah.

