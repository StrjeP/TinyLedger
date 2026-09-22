# Tiny Ledger

##  To run the application:

./mvnw spring-boot:run

Then visit:
- http://localhost:8080/hello — the sample endpoint
- http://localhost:8080/swagger-ui/index.html — Swagger UI
- http://localhost:8080/v3/api-docs — raw OpenAPI spec

Stop it with Ctrl+C in that terminal.

Alternatively, build a runnable jar and run it directly:

./mvnw clean package -DskipTests                                                                                                                                                                                                                                                                                                                                         
java -jar target/tiny_ledger-0.0.1-SNAPSHOT.jar


#  Design overview

* Assume this is a single ledger / account in a single currency, all actions are on this one item.
* The currency is a simple double not a big decimal, easier to code.
* There are no limits to the size of deposit or withdrawl, allow overdrawn.
* Running the service starts with a blank ledger, there is no persistent storage.

* Use Lombok to simplify the payloads.

## API 

### Money Movements - requests POST

Post a JSON request to the end point with type of deposit or withdrawl, the response is an action report, 
success/failure with place for additional errors.

### View current balance / View transaction history

Two end points:

GET current balance - ledger / account summary no inputs

GET transaction history - start with returning all, if time, add date range and/or pagination 


## Internal Structure

* Requests are stamped with a unique id, just use milli since epoch from a clock.
* Passed to service using a FIFO queue, to decouple REST from service allow alternative transport.
* No Orchestration/Saga just process requests, perform the action, record transaction with request. 

* Ledger has no identity just current balance.
* Transactions is an append only list of transactions, timestamp from the clock, action, amount and request (aka cause).

## Model

### Money Movement Request

TBD

### Balance Response

TBD

### Transactions Response



<hr>

# Project setup

Using Intellij new project to boot strap a Spring Boot with Spring MVC - as use that in my day job.



<hr>
Direct copy from pdf:

# Take Home Assignment - Build a tiny ledger! 🏦
This task should take no more than a few hours to complete.

You are expected to implement a set of apis to power a simple ledger.

From a functional perspective, the following features should be implemented:

* Ability to record money movements (ie: deposits and withdrawals)
* View current balance
* View transaction history

From a technical perspective:

* We expect you to deliver a functional web application (no UI, just the apis)
that can be run locally.
* You can use any programming language and framework of your choice.
* For the sake of simplicity, we strongly suggest you use in-memory data structures to store the data (for example: a map or an array), and it should not
be necessary to install any optional software to run it (besides any libraries
that you choose to use).
 
You are free to make assumptions whenever you feel it is necessary, but please
document them.

Please try to keep it simple. The objective is to understand your approach to
problems and your thought process rather than a test of your technical knowledge,
even if it means having to make trade-offs.

This means that you are **not** expected to deliver any of the below:
* authentication/authorisation
* logging / monitoring
* transactions/atomic operations
* ...

(Feel free to cut down other parts as much as you need to fit the solution into the
time you have available)
The solution should be submitted as a link to a public git repository (GitHub, GitLab,
Bitbucket, etc.) with a README file containing instructions on how to run the
application as well as a few examples of how to execute the implemented features.


<hr>

Personal notes:

export JAVA_HOME=/Users/stephen/Library/Java/JavaVirtualMachines/openjdk-26.0.2.1/Contents/Home
