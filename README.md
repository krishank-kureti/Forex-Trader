# Forex Trading & Exchange Management System

A REST API backend for a Forex Trading system built with Java 17 + Spring Boot 3.2.0 + MySQL.

## Features

- **User Authentication** - JWT-based login and registration
- **Wallet Management** - Deposit, withdraw, and check balance
- **Trade Operations** - Place buy/sell orders, view history, cancel orders
- **Exchange Rates** - View live currency pair rates
- **Order Matching** - Automated matching engine for trades
- **Settlement** - Trade settlement with fee calculation

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- Spring Security (JWT)
- Spring Data JPA
- MySQL Database
- Maven

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Configuration

Database settings in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forex_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### Build & Run

```bash
# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

The server will start at `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token

### Wallet (Requires JWT)
- `GET /api/wallet/balance` - Get balance
- `POST /api/wallet/deposit` - Deposit funds
- `POST /api/wallet/withdraw` - Withdraw funds
- `GET /api/wallet/transactions` - View transaction history

### Trading (Requires JWT)
- `POST /api/trades/place` - Place new order
- `GET /api/trades/history` - View order history
- `DELETE /api/trades/{id}` - Cancel order

### Exchange Rates
- `GET /api/rates` - Get all rates

## Project Structure

```
src/main/java/com/forex/
├── controller/      # REST API controllers
├── service/         # Business logic
├── repository/      # JPA repositories
├── model/           # Entity classes
├── dto/             # Data transfer objects
├── config/          # Security & app config
└── patterns/        # Design pattern implementations
    ├── factory/
    ├── observer/
    ├── strategy/
    └── facade/
```

## Design Patterns

- **Factory Pattern** - Order creation (Buy/Sell)
- **Observer Pattern** - Trade event notifications
- **Strategy Pattern** - Fee calculation
- **Facade Pattern** - Wallet operations

## License

This project is for educational purposes.
