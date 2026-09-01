# Toucan Payments — Transaction Service

## 1. Understanding of the problem

This service manages customer transactions and implements the four required operations: create a transaction, retrieve a transaction by Transaction ID, update transaction status, and retrieve all transactions for a Customer ID.

The implementation uses Spring Boot, Spring Web, Spring Data JPA, H2 and JUnit as supplied by the starter project.

## 2. Assumptions and validation rules

The assignment says each candidate has a variant supplied in their invitation email. That variant was not included in the starter files available to me, so the following rules are the assumptions used in this implementation. **Before submission, replace these with the exact rules in your invitation if they differ.**

- Transaction ID: required and must match `t` followed by 11 digits.
- Customer ID: required and must match `c` followed by 11 digits.
- Amount: required and at least `0.01`.
- Currency: required, exactly three uppercase letters.
- Transaction type: `PAYMENT`, `REFUND`, or `TRANSFER`.
- New transactions must start in `PENDING`.
- Transaction IDs are unique.
- Statuses: `PENDING`, `COMPLETED`, `FAILED`, `CANCELLED`.
- Status transitions are deliberately restricted: `PENDING` can move to `COMPLETED`, `FAILED`, or `CANCELLED`. Terminal states cannot move to another state. Repeating the same status is allowed.

The transition rule prevents a completed/failed/cancelled transaction from being silently changed later, which makes transaction history safer and easier to reason about.

## 3. API endpoints

### Create transaction

`POST /api/transactions`

Example request:

```json
{
  "transactionId": "t12345678901",
  "customerId": "c12345678901",
  "amount": 125.50,
  "currency": "USD",
  "transactionType": "PAYMENT",
  "status": "PENDING"
}
```

Returns `201 Created`. Invalid input returns `400 Bad Request`; duplicate IDs return `409 Conflict`.

### Get transaction

`GET /api/transactions/{transactionId}`

Returns `200 OK` or `404 Not Found`.

### Update transaction status

`PATCH /api/transactions/{transactionId}/status`

Example:

```json
{"status":"COMPLETED"}
```

Returns `200 OK`. Invalid transitions return `409 Conflict`.

### Get customer transactions

`GET /api/transactions?customerId=c12345678901`

Returns `200 OK` with a JSON array. If the customer has no transactions, the response is an empty array.

## 4. Structure

- `controller` — HTTP/API layer.
- `service` — business rules and status-transition logic.
- `repository` — persistence through Spring Data JPA.
- `entity` — database/domain model and enums.
- `dto` — request/response objects.
- `exception` — consistent API error handling.

## 5. Testing

The automated test suite covers:

1. Successful transaction creation.
2. Validation failure.
3. Duplicate Transaction ID.
4. Missing transaction.
5. Valid status update.
6. Invalid status transition from a terminal state.
7. Retrieval of all transactions for a customer.

Run from the project root:

```bash
./mvnw clean test
```

Windows:

```bat
mvnw.cmd clean test
```

## 6. Known limitations / future improvements

- The currency list is intentionally generic because the assigned currency variant was not available in the starter files. It should be replaced with the exact permitted currencies from the candidate invitation.
- H2 is an embedded database suitable for this exercise; production would normally use a managed relational database.
- No authentication/authorization is included because it is outside the stated exercise.
- Pagination could be added to the customer-transaction endpoint for a production-scale dataset.
- Audit fields such as created time, updated time and status-change history could be added if transaction history requirements were expanded.

## 7. AI Usage Disclosure

I used an AI coding assistant to help interpret the requirements, propose a Spring Boot layering approach, generate initial implementation ideas, and suggest automated tests.

I reviewed the generated code against the challenge requirements and kept the design intentionally small: controller, service, repository, DTOs, entity/enums and centralized exception handling. I also checked the business rules manually, particularly duplicate IDs, validation, missing transactions and status transitions. The assigned candidate variant was not present in the provided files, so the README explicitly identifies the assumptions that must be checked against the invitation email before submission.

The final implementation should be verified locally with `mvnw.cmd clean test` on Windows or `./mvnw clean test` on Linux/macOS before submission.
