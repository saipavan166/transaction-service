AI Usage Disclosure

Project: Toucan Payments – Transaction Processing Service
Candidate: [Your Name]
Date: [Submission Date]

AI tools used
I used ChatGPT as an AI coding assistant while completing the Java/Spring Boot transaction-processing challenge.

How I used AI
I used AI primarily to:
- Understand the requirements in the challenge document.
- Discuss possible Spring Boot project structure and layering.
- Review Java validation, exception handling, REST API design, and JUnit testing approaches.
- Generate initial implementation ideas and test cases.
- Review code for edge cases and readability.

Significant AI-generated suggestions
AI suggested a conventional Controller → Service → Repository structure, DTO-based request validation, centralized exception handling, duplicate Transaction ID detection, and automated tests for the required scenarios.

What I changed, corrected, or rejected
I reviewed the generated suggestions against the actual starter project and challenge requirements rather than accepting them blindly. I adapted the implementation to the existing project structure and selected the business rules and validation behavior used by the application. I also kept the solution deliberately simple because the challenge emphasizes clear Java fundamentals, validation, testing, and maintainability.

What AI got wrong or what required verification
AI-generated code and suggestions were treated as drafts. I checked imports, Spring annotations, validation behavior, endpoint mappings, status-transition logic, and test assertions against the actual project. Any suggestions that did not fit the starter project or the stated requirements were changed or rejected.

How I checked the final result
I reviewed the source code and test cases against the four required operations and the minimum testing requirements. The project is intended to be verified from a clean checkout using:

    mvnw.cmd clean test

I would also manually exercise the REST endpoints using an HTTP client such as Postman or curl and verify successful requests, validation failures, duplicate IDs, missing transactions, status updates, and customer transaction retrieval.

Declaration
I understand that AI tools are permitted for this challenge, but I remain responsible for the submitted code and must be able to explain and modify it during the technical interview.
