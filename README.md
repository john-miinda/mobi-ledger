# MobiLedger

A production-style full-stack personal finance management platform for tracking transactions, managing budgets, monitoring savings goals, and analyzing financial activity.

MobiLedger is designed to give individuals a clear view of their financial health by combining transaction tracking, budget planning, recurring expense insights, and savings target management in one place.

## Overview

- Track income and expenses with a clear transaction history
- Create and manage monthly budgets by category
- Monitor savings goals and progress toward targets
- Analyze spending patterns over time
- Maintain a clean and scalable full-stack architecture for real-world use

## Tech Stack

### Backend
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL

### Frontend
- React
- TypeScript

### Tooling & Delivery
- Docker
- GitHub Actions

## Architecture

MobiLedger follows a modern layered architecture with:

- A Java/Spring Boot backend for API logic, authentication, and data persistence
- A React + TypeScript frontend for a responsive user experience
- MySQL as the relational database for financial records
- Docker for local environment consistency and deployment readiness

## Features

### Transaction Management
- Record income and expense transactions
- Categorize spending by type or custom labels
- View recent transaction activity and historical movement

### Budgeting
- Create budget limits by category
- Compare spending against allocated budgets
- Identify overspending early

### Savings Goals
- Set savings targets with milestones or target amounts
- Track contribution progress over time
- Understand how current habits affect long-term goals

### Financial Insights
- Review spending trends and behaviour over time
- Support informed decision-making around recurring costs and cash flow

## Project Structure

```text
mobi-ledger/
├── backend/
│   ├── src/
│   ├── pom.xml
│   └── ...
├── frontend/
│   ├── src/
│   ├── package.json
│   └── ...
├── docker/
│   └── ...
├── .gitignore
├── README.md
└── ...
```

## Getting Started

### Prerequisites

- Java 17+
- Node.js 18+
- npm or yarn
- MySQL
- Docker (optional, for containerized setup)

### Backend Setup

1. Navigate to the backend project directory.
2. Configure your database connection in the application configuration.
3. Run the application with your preferred Spring Boot command.

Example:

```bash
./mvnw spring-boot:run
```

### Frontend Setup

1. Navigate to the frontend project directory.
2. Install dependencies.

```bash
npm install
```

3. Start the development server.

```bash
npm run dev
```

### Docker Setup

If the project includes Docker configuration, you can run the application using Docker Compose or the provided container scripts.

## Environment Variables

Set the following values as needed for local development or deployment:

- Database URL
- Database username
- Database password
- JWT or authentication secret
- Frontend API base URL

## Roadmap

- Enhanced analytics and reporting
- Recurring transaction automation
- Multi-account support
- Better financial forecasting tools
- Improved dashboard UX and mobile responsiveness

## Contributing

Contributions are welcome. Please open an issue or submit a pull request with a clear description of the change and any relevant context.

## License

This project is currently under active development. Add a license file if you want to define usage and distribution terms explicitly.

## Status

MobiLedger is a production-style project focused on personal finance management, with a strong full-stack foundation and a clear path toward broader financial tooling and insights.
