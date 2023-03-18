# Self-implementation assessment case

### Preconditions

We have a case study from a payment processing business domain.
It covers the use case of “customer doing payment transactions at merchant”. You have the following entities with these attributes:
  ### Customer:
- name
- email
- dateOfRegistration
### PaymentTransaction:
- transactionDate
- grossAmount
- VATRate  (0%, 7%, 19%)
- receiptId
### Merchant:
- name
- active

 ## Technical scope

 You can work with any SQL relational database according to your best experience (e.g. PostgreSQL, mySQL, ...).
 REST services can be implemented by using Spring Boot as main framework.
Programming language of the service component has to be Java.

## Tasks DB

#### design a data model that can support following:

customer can do 0 - N transactions at any of the merchants
The transaction is always done by one customer at one marchant Multiple customers are doing transactions at one merchant


##### Write SQL commands to create a data model:

#### Assume that there are some payment transactions done in 2022:
- Write an SQL query to select the merchant with the highest turnover in 2022
Note: turnover is the sum of gross amount of all transactions
- Write a query in order to check whether the merchant with the highest turnover is still active?
- Write a query to select the 5 top customers that have been active in 2022 but ceased doing transactions in 2023
- top customer shall the one with the highest number of transactions (regardless of amount)

### REST Service

- create a Spring boot service
- create an endpoint returning a merchant by his ID
- create an endpoint to get all active merchants (having active attribute = true) create an endpoint to get all customers without any PaymentTransaction

### Demonstration of the service

- run the service
- demonstrate how you can get the results
