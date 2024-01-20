# TEST APPLICATION -> https://food-flow-75e570332cc6.herokuapp.com/foodflow

## Demo Accounts

#### To explore FoodFlow's features, you can use the following demo accounts:

#### Owner Account:

username: owner <br>
password: test

#### Customer Account:

username: customer <br>
Password: test

# FoodFlow

FoodFlow is a straightforward food ordering portal that enables restaurant owners to create accounts, establish their
restaurants, define menus, specify delivery locations, and manage orders.

Customers can also create accounts, search for restaurants,
view menus, place orders, and, under certain conditions,
cancel orders.

## ERD Diagram

![ERD Diagram](src/main/resources/ui-images/diagramERD-foodflow.png)

Account Creation: Restaurant owners and customers can create accounts on the platform.

![registration](src/main/resources/ui-images/registration-form.png)

## Features for restaurant owners

Menu Management: Owners can define their menus by specifying food categories (appetizers, soups, main courses,
desserts) and adding items with descriptions and prices.

![menu](src/main/resources/ui-images/owner-ui-images/owner-restaurant.png)
![menu](src/main/resources/ui-images/owner-ui-images/owner-menu.png)
![menu](src/main/resources/ui-images/owner-ui-images/owner-category.png)
![menu](src/main/resources/ui-images/owner-ui-images/owner-restaurantdetails.png)

Image Upload: Owners can upload images for each menu item to assist customers in making choices.

![menu](src/main/resources/ui-images/owner-ui-images/owner-item.png)

Delivery Locations: Owners can provide a list of streets to which they deliver food.

![menu](src/main/resources/ui-images/owner-ui-images/owner-delivery-address.png)

Order List: Owners can view a list of orders received, categorized as pending and completed.
Order Completion: Owners can mark orders as completed (this is simplified in the application,
as typically delivery personnel would do this).

![menu](src/main/resources/ui-images/owner-ui-images/owner-check-orders.png)

## Features for customers

Search for Restaurants: Customers can search for restaurants that offer food delivery based on the street they provide.

![menu](src/main/resources/ui-images/customer-ui-images/customer-search-restaurants.png)

Pagination and Sorting: The list of restaurants can be paginated and sorted.

![menu](src/main/resources/ui-images/customer-ui-images/customer-matching-restaurants.png)

View Menus: Customers can select a restaurant and view its menu.

Place Orders: Customers can place orders by selecting desired items and quantities.

![menu](src/main/resources/ui-images/customer-ui-images/customer-order-form.png)

Order Confirmation: After placing an order, customers receive a confirmation with a unique order number.

![menu](src/main/resources/ui-images/customer-ui-images/customer-order-information.png)

Order Cancellation: Customers can cancel orders if it has been less than 5 minutes since they were placed.

![menu](src/main/resources/ui-images/customer-ui-images/customercheckorder.png)

NEW FEATURE (29.10.2023) : CHECK WEATHER IN YOUR CITY!

![menu](src/main/resources/ui-images/customer-ui-images/customer-weatherapi.png)

## Getting started

1. Clone project

  ``` bash      
   git clone https://github.com/mateuszmechula/foodflow.git
  ```

2. Open cloned directory

  ``` bash      
   cd foodflow
  ```

3. Build project

  ``` bash
  ./gradlew clean build
  ```

4. Go to docker directory

  ``` bash      
   cd docker
  ```

5. Run using docker-compose

  ``` bash
  docker-compose up -d
  ```

## Technologies used

### Backend

- Spring Boot
- Spring Web
- Spring Security
- Spring Data PostgreSQL
- Lombok
- Gradle
- Flyway
- Mapstruct
- OpenAPI Generator (WebClient)

### Backend Testing

- JUnit
- Mockito
- RestAssured
- Testcontainers
- Wiremock

### Database

- PostgreSQL

### Frontend

- Thymeleaf

### Other

- Docker
- Swagger

## Authors

- [@Author](https://www.github.com/MateuszMechula)
