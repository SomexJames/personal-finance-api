# personal-finance-api

API for personal finance management

## About the API

A Java-based API that allows you to manually input future income and expenses to visualize what your balance might look like on a given date. (i.e. "If I buy this item now, I get my pay check next week, will I have enough in my account to pay for these bills coming up after?")

## Features

This API provides HTTP endpoints and tools for the following:

* Create a user: `POST /api/v1/users`
* Add an account to a user: `POST /api/v1/users/{userId}/accounts`
* Add a transaction to an account: `POST /api/v1/accounts/{accountId}/transactions`
* Retrieve account balance for a specific date: `GET /api/v1/accounts/{accountId}/balance?date=YYYY-MM-DD`
* Get all transactions for an account: `GET /api/v1/accounts/{accountId}/transactions`
* Get the last known balance before a specific date: `GET /api/v1/accounts/{accountId}/balance/last-known?date=YYYY-MM-DD`
* Delete a transaction by ID: `DELETE /api/v1/accounts/{accountId}/transactions/{transactionId}`

## Details

### `POST /api/v1/users`

This endpoint is used to create a new user.

**Body:**

```json
{
  "name": "John Doe"
}
```

**Response:**

```json
{
  "id": "12345",
  "name": "John Doe",
  "accounts": []
}
```

**Where:**

- `id` - unique user id (automatically generated)
- `name` - the name of the user
- `accounts` - list of accounts (default empty)

### `POST /api/v1/users/{userId}/accounts`

This endpoint adds a new account to an existing user.

**Body:**

```json
{
  "accountType": "CHECKING"
}
```

**Response:**

```json
{
  "id": "67890",
  "accountType": "CHECKING",
  "dailyTransactions": {}
}
```

**Where:**

- `id` - unique account id (automatically generated)
- `accountType` - type of account: CHECKING, SAVINGS, CREDIT_CARD, CASH, etc.
- `dailyTransactions` - map containing DailyTransaction objects, keyed by date. (each DailyTransaction holds the transactions and balance information for that day)

### `POST /api/v1/accounts/{accountId}/transactions`

This endpoint adds a transaction to an account.

**Body:**

```json
{
  "name": "Salary",
  "amount": 1000.00,
  "startDate": "2024-01-01",
  "recurrencePeriod": 0,
  "stopDate": null,
  "maxOccurrences": 0,
  "type": "INCOME"
}
```

**Response:**

```json
{
  "id": "abcde",
  "name": "Salary",
  "amount": 1000.00,
  "startDate": "2024-08-16",
  "recurrencePeriod": 0,
  "stopDate": null,
  "maxOccurrences": 0,
  "type": "INCOME"
}
```

**Where:**

- `id` - unique account id (automatically generated)
- `name` - The name or description of the transaction.
- `amount` - The transaction amount.
- `startDate` - The date the transaction starts.
- `recurrencePeriod` - The number of days between recurrences; 0 for a single occurrence.
- `stopDate` - The date to stop recurring transactions (optional).
- `maxOccurrences` - Maximum number of recurrences (optional).
- `type` - The type of transaction: INCOME or EXPENSE.

### `GET /api/v1/accounts/{accountId}/balance?date=YYYY-MM-DD`

This endpoint retrieves the account balance for a specific date.

**Response:**

```json
{
  "balance": 500.00
}
```

### `GET /api/v1/accounts/{accountId}/transactions`

This endpoint returns all transactions for a given account.

**Response:**

```json
[
  {
    "id": "abcde",
    "name": "Salary",
    "amount": 1000.00,
    "startDate": "2024-08-16",
    "recurrencePeriod": 0,
    "stopDate": null,
    "maxOccurrences": 0,
    "type": "INCOME"
  },
  {
    "id": "fghij",
    "name": "Rent",
    "amount": 500.00,
    "startDate": "2024-08-16",
    "recurrencePeriod": 0,
    "stopDate": null,
    "maxOccurrences": 0,
    "type": "EXPENSE"
  }
]
```

### `GET /api/v1/accounts/{accountId}/balance/last-known?date=YYYY-MM-DD`

This endpoint returns the last known balance before the specified date.

**Response:**

```json
{
  "balance": 1000.00
}
```

### `DELETE /api/v1/accounts/{accountId}/transactions/{transactionId}`

This endpoint deletes a specific transaction by ID.

**Response:**

- Status: `204` - no content

## Technologies Used

This project was developed with:

- **Java 22 (OpenJDK 22.0.2)**
- **SpringBoot 3.3.0**
- **JUnit 5** for unit testing
- **Mockito** for mocking dependencies in tests
- **Gradle** for project management and build automation

## Compile and Package

To compile and package the project into a JAR file, run:

```bash
./gradlew build
```

The JAR file will be generated in the `build/libs` directory.

## Execution

To run the API, execute the generated JAR file:

```bash
java -jar build/libs/finance-management-api.jar
```

By default, the API will be available at `http://localhost:8080/api/v1`.

## Test

To run unit tests, execute:

```bash
./gradlew test
```

This will run all unit tests and generate a report in the `build/reports/tests/test/index.html` file.