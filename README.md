 

# betPawa-wallet 

Based on GoogleRPC and Protocol Buffer - a multithreaded Asynchronous Application that Allows Deposit, Withdrawal and Get Balance for the users.Project consists of a Wallet server and Wallet client.The wallet server will keep track of a users monetary balance in the system. The client will emulate users depositing and withdrawing funds.

---

### Sub Modules 

## betPawa-wallet-client

* Spring BOOT based Client Which makes Concurrent Transaction Request via GRPC Stub over HTTP2.
* The wallet client will emulate a number of users concurrently using the wallet. 
* The wallet client must connect to the wallet server over gRPC. 
* The client eliminating users doing rounds (a sequence of events). 
* Whenever a round is needed it is picked at random from the following list of available rounds

#### Round A

* Deposit 100 USD
* Withdraw 200 USD
* Deposit 100 EUR
* Get Balance
* Withdraw 100 USD
* Get Balance
* Withdraw 100 USD

#### Round B

* Withdraw 100 GBP
* Deposit 300 GPB
* Withdraw 100 GBP
* Withdraw 100 GBP
* Withdraw 100 GBP
* Withdraw 100 GBP
* Withdraw 100 GBP

#### Round C

* Get Balance
* Deposit 100 USD
* Deposit 100 USD
* Withdraw 100 USD
* Deposit 100 USD
* Get Balance
* Withdraw 200 USD
* Get Balance

#### The wallet client will have the following parameters:

* users (number of concurrent users emulated)
* concurrent_threads_per_user (number of concurrent requests a user will make)
* rounds_per_thread (number of rounds each thread is executing)

##### Number of Transactions per Request by betPawa-wallet-client:

* `K Users` Should Run Concurrently.
* Each Kth User Can make `N Requests`.
* Each Nth Request can Spawn `M Concurrent Operations(Rounds)`.
* Each Round will have `DEPOSIT`, `WITHDRAW`,  `BALANCE`.
* Total Number of threads will K * M * N (* Average 7 Transactions per round).
    
## betPawa-wallet-server

*    Records balance in user Wallet.
*   Expose API for Depositing Money, Withdrawing and Getting Balance in different currencies.
    
## betPawa-wallet-proto
    
*   Has proto file(.proto), Generated Stubs and Domains shared by BetPawaWalletClient and BetPawaWalletServer.

---

### Assumptions and Pointers:

* Server Handles Transaction on FCFS. Ideally in a solution like this the sequence of transactions must be maintained viz OOS.
* About "Make sure the client exits when all rounds has been executed."

  * betPawa-wallet-client exposes SWAGGER API for Testing.
  
* Technologies

	* Java 8.
	* gRPC
	* MySQL 
	* Gradle
	* JUnit
	* SLF4J
	* Docker
	* Hibernate
	* Spring and Spring Boot.
	* Swagger
	

* The docker containers should run via Compose/Kubernetes. OOS.
* The Client/Server Doesn't retry failed transactions. OOS
* The user ID is Taken from Number of Users param (userID:1 for numberOfuser=1,userID:1,userID:2 for numberOfuser=2).
* Database schema has been kept Simple with One table.
* The Service Response/Request has been kept same for `RAPID (Rapid Application Development in Pro-typing)` Otherwise it should be different for each transaction type example /docs/wallet.proto.
---

### How to run `BetPawaWalletServer` and `BetPawaWalletClient`

#### Database

##### LOCAL 

Hibernate/ auto create betpawa db / imports.sql for initial datas

#### JAVA(betPawa-wallet-server)

##### LOCAL 

Run server as Spring Boot Application.

#### JAVA(betPawa-wallet-client)

##### LOCAL 

Run client as Spring Boot Application.
Use Swagger for test

---

### Important choices in Solution

* The Whole Structure of the BetPawa-wallet application is loosely coupled SOA.
* Each Client,Server,DB Instances are developed keeping Scalability,Elasticity and Fault tolerance in mind.
* Docker Instances make it possible to enable containerization and Helps in Deployments.
* The Performance Tuning variables are not yet externalized.
* Server Side - Connection Pooling Configurations(Depends on Given Deployment Platform).
* Client Side - Task Executor is Configurable with Concurrent Worker Threads.

### Transactions Per Seconds[TPS].

#### Per Transaction Variant:

Application Variant : All below are 10 Concurrent Calls but they take different execution time because of their nature.

* Single user Making 10 Deposit: Corresponds to 20 DB Calls 10 for Get and 10 for Update.
* Single user Making 5 Withdraws 5 Deposit:Same.
* Single user Making 4 Withdraws 4 Deposit 2 Balance: 18 DB Calls 10 Get and 8 Update.

All of the above transactions have high chances of` OptimisticLockException` due to versioning on WIP same row stale object.Can be mitigated with retry mechanism however in this kind of scenario the sequence need to be guaranteed possible with `bidirectional streaming`.

Then there are other scenarios with multiple users with multiple transactions - testing is OOS.

#### Database Variant:
	
* MYSQL DB: Although mysql can handle 150+1 Connections , for that the calling system should be of very high configurations, The current application configures the Connection Pool Max Size to be 10 based `Number of Cores * 2 + Max(tX Spindle time)`
* Dockerized MYSQL DB: This makes a delta of 5-10 %.
	
### Integration Test

    1.  Make a withdrawal of USD 200 for user with id 1. Must return "insufficient_funds".
    2.  Make a deposit of USD 100 to user with id 1.
    3.  Check that all balances are correct
    4.  Make a withdrawal of USD 200 for user with id 1. Must return "insufficient_funds".
    5.  Make a deposit of EUR 100 to user with id 1.
    6.  Check that all balances are correct
    7.  Make a withdrawal of USD 200 for user with id 1. Must return "insufficient_funds".
    8.  Make a deposit of USD 100 to user with id 1.
    9.  Check that all balances are correct
    10. Make a withdrawal of USD 200 for user with id 1. Must return "ok".
    11. Check that all balances are correct
    12. Make a withdrawal of USD 200 for user with id 1. Must return "insufficient_funds".
