# video-stream
Video Stream api to simulate upload of video and metadata.

This api cover the very basic functionalities of uploading and downloading video content and the metadata.

This application is using Java 17+, SpringBoot version 3.2, Postgres 16.+

## Getting Started
To run the project you will need docker running. 
On the startup of the Springboot the api will run the `docker compose up` for you  

* The Api is using the pots 8080, 5050 and 5432.

Docker is being used to:
- To create a Postgres Database.
- Create an instance of PGAdmin on a container, integrating automatically with the database.
- To perform the integration tests using TestContainers.

### Api documentation & Information
- The Swagger docs can be accessed by http://localhost:8080/swagger-ui/index.html
- To use the PGAdmin you can access by http://localhost:5050