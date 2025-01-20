# EvoPlus

## Getting Started
This application uses Maven for build automation as well as Docker for create containers 
and Docker-Compose for container orchestration.

#### Docker
To start the application with Docker, follow these steps:
1) Install dependencies:
`cd app && ./mvnw clean install`

2) Deploy the containers and start the application:
`cd .. && docker-compose up`

3) Navigate to `http://localhost:8080/`

#### Manual startup
To manually start the application (i.e. without Docker) follow these steps:

1) Install dependencies `cd app && ./mvnw clean install`

2) Start the application `./mvnw spring-boot:run`

3) Navigate to `http://localhost:8080/`

## Good to know

### Updating Docker images
Docker keeps copies of previously downloaded images locally and does not update these unless specified.
This can create problems during development.

#### Main application image
If the main application `/app`  is being developed locally, its associated Docker image will need
to be rebuilt everytime the application is run. Otherwise, the newly made changes will not
be reflected when executing the application.

In order to do so, execute this command before deploying the containers:
`docker-compose build`


#### Other images
Publicly available images can be updated with the following command:
`docker-compose pull`

