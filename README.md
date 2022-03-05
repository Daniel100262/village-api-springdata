# DevInHouse Village API (Refactored)

This is an API for registering and consulting the residents of a village, as well as generating a financial report, using Spring Boot, Spring Core, Spring Security and Spring Data, now refactored to work with Spring Data, Hibernate, RabbitMQ, JUnit and Mockito

## 🚀 Running the application

To run this project, it is necessary to follow the step by step below:

1 - Run the **dbcrete.sql** SQL script to create the database

2 - Run the Spring Boot Application to create database tables and relationships

3 - Run the **populate-db.sql** SQL script to populate database with initial data

4 - Make a request to the endpoint **/login** with the email **admin@company.com** and the password **S2jx6uTbnCi*** and get the Bearer token from the request header

5 - Use the token to access other application endpoints, always observing the need for the correct role for the endpoint.


## 🛠 Tecnologies

The following tools were used in the construction of the project:

- [JAVA](https://www.java.com)
- [Spring Core](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html)
- [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Web MVC](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html)
- [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Data](https://spring.io/projects/spring-data)
- [Hibernate](https://hibernate.org/)
- [RabbitMQ](https://www.rabbitmq.com/)
- [JUnit](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)

## ☰ Conceptual Modeling
![image](https://user-images.githubusercontent.com/6551994/156147334-688c5c24-27d9-44ec-ac71-45f5f53d726c.png)

## ☰ Logical Modeling
![image](https://user-images.githubusercontent.com/6551994/156144425-df3660d7-26be-4cc1-b0c8-4d5547bf77ba.png)

## ☰ RabbitMQ Queue Modeling 

![image](https://user-images.githubusercontent.com/6551994/156877533-7e7b97a6-7589-40f2-b58d-d201f582fc91.png)


## ✅ Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
