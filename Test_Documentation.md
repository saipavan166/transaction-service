Test Documentation

Project: Toucan Payments – Transaction Processing Service

1. Testing approach

The project uses JUnit/Spring Boot tests to verify the transaction-processing business behavior. The tests focus on meaningful outcomes rather than only checking that methods execute without exceptions.

The main required scenarios are covered:

1. Successful transaction creation
2. Rejection of invalid transaction input
3. Rejection of a duplicate Transaction ID
4. Request for a transaction that does not exist

Additional tests are included for the status-update and customer-transaction behavior where applicable.

2. Test cases

TC-01 — Create transaction successfully
Purpose:
Verify that a valid transaction is accepted and stored.

Expected result:
- HTTP 201 Created (or the endpoint's documented success response)
- The returned transaction contains the supplied Transaction ID, Customer ID, amount, currency, type, and initial status.
- The transaction can subsequently be retrieved.

TC-02 — Reject invalid transaction
Purpose:
Verify that validation prevents malformed transaction data from being stored.

Example invalid conditions:
- Missing/blank required Customer ID
- Invalid Transaction ID format
- Invalid amount
- Missing required currency or transaction type

Expected result:
- HTTP 400 Bad Request
- A useful validation error is returned.
- No invalid transaction is persisted.

TC-03 — Reject duplicate Transaction ID
Purpose:
Verify that Transaction IDs are unique.

Steps:
1. Create a valid transaction.
2. Submit another transaction using the same Transaction ID.

Expected result:
- HTTP 409 Conflict
- The original transaction remains unchanged.
- The duplicate is not stored.

TC-04 — Get transaction that does not exist
Purpose:
Verify correct behavior when a requested Transaction ID cannot be found.

Expected result:
- HTTP 404 Not Found
- The response identifies that the transaction does not exist.

TC-05 — Update transaction status
Purpose:
Verify that the status of an existing transaction can be changed according to the application's permitted state transitions.

Expected result:
- Valid transition succeeds.
- Invalid transition is rejected with an appropriate error response.
- The stored transaction reflects a successful status change.

TC-06 — Get customer transactions
Purpose:
Verify that transactions can be retrieved for a Customer ID.

Expected result:
- The response contains transactions belonging to the requested customer.
- Transactions belonging to other customers are not included.

3. What the tests demonstrate

The test suite is intended to demonstrate:
- Request validation
- Persistence behavior
- Uniqueness enforcement
- Not-found handling
- Business-rule enforcement
- REST error handling
- Correct response behavior

4. Running the tests

From the project root on Windows:

    mvnw.cmd clean test

On Linux/macOS:

    ./mvnw clean test

The build should be run from a clean checkout before submission.

5. Test evidence

Paste the actual output produced by the command above into this section before submitting the challenge. Do not claim a passing test run unless the command has actually been executed successfully.

Example format:

    [PASTE ACTUAL mvnw.cmd clean test OUTPUT HERE]

6. Notes

The challenge requires at least four automated tests and specifically calls for coverage of successful creation, validation failure, duplicate Transaction ID, and a missing transaction. The submitted tests should be reviewed against the assigned candidate variant before final submission.
