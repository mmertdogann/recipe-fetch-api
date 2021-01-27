# Recipe Fetch API

Recipe Fetch API for listing recipes

## About Project

This project is the backend part of a website about showing recipes to the user. The whole project covers different stories for showing different use cases:

* Story 1 - Fetch all recipes or filtered by name or category from the backend system
* Story 2 - Fetch all categories from the backend system
* Story 3 - Fetch recipes from the specified category

 ![Recipe Fetch API](https://i.ibb.co/h8FXKRb/Screen-Shot-2021-01-13-at-17-27-24.png)

 > What does the API look like, running at https://recipe-fetch-api.herokuapp.com/recipes

## Technologies

The project has been created using these technologies:

* **Java 8** as programming language 
* **Spring Boot v2.4.1** as web framework
* **Maven v3.6.3** as build automation tool
* **JUnit & Mockito** as unit testing libraries

## Setup

### Required Softwares:

* Java 8
* Maven 3.6.3

### Installation Steps:

* Import as a maven project
* Open a terminal, navigate to the project directory and run `mvn clean package` This command will build the project, run all of the unit tests and also produce a jar. If you want to just run the unit test, use `mvn test` command
* In the end, run `java -jar target/recipe-fetch-api-0.0.1-SNAPSHOT.jar` command in the project directory in order to start the Spring Boot Web application

> In order to test post request; in the `src/main/resources` folder, there is a `test.json` file to create a recipe. The POST request can be generated by using Postman
