# Proof-of-Concept License Validation Application

This application is intended to help in testing and development of the UI prototype of the "Register FireCam Plugin"
feature of the [FireControl](https://github.com/langmuirsystems/firecontrol) application. 

## Illustration of the `/licenses/buy` endpoint

When one sends GET request to the endpoint `/licenses/buy`, the server will return a page
that contains prefilled ( with the email address passed in request ) text field and a 'Buy' button.

Below is an example of sending `/licenses/buy?email=user@test.com` request.

The user is expected only to press the 'Buy' button at this stage.

<div style="text-align: center;">
<img src="https://gist.github.com/user-attachments/assets/9cffc673-e1e6-41bf-a47f-5d113685ace6" width="500;"> 
</div>

## Redirection to the `/licenses/complete-buy` endpoint

When the 'Buy' button is pressed,
the form sends request to the `/licenses/complete-buy` endpoint
passing the email address.\
Based on the address,
the server will generate the license number or retrieve it from a database
if the number was already generated for this email address.\
Then redirection occurs to the `/licenses/thank-you` page that will contain all the gathered information.

The user is expected to copy the displayed license number and then paste it into the 'Register FireCam Plugin'
pane inside the FireControl application.

<div style="text-align: center;">
<img src="https://gist.github.com/user-attachments/assets/06d512e6-a6e3-478d-b03d-4abade961aaf" width="500">
</div>

---

### Add .env file to allow Docker extracting environment variables

These environment variables are required to start all defined services:
database, mail server, and the Spring Boot application itself.

Add the following `.env` file to the root of the project folder. \
It will be used by Docker to extract environment variables when you run `docker compose up --build -d`.


```
###########################################################################################
# Database-specific environment variables ( used by Docker Compose - PostgreSQL service ) #
###########################################################################################
POSTGRES_DB=email_license_db
POSTGRES_USER=user
POSTGRES_PASSWORD=password

###########################################################################################
#    Application-specific environment variables ( used by Spring Boot application )       #
###########################################################################################
# The 'database' refers here to the service name in our 'docker-compose.yml' file
# owing to Docker's internal networking allows services to reach each other by name
SPRING_DATASOURCE_URL=jdbc:postgresql://database:5432/email_license_db
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=password
# Below are the credentials for the email sending
SPRING_MAIL_HOST: mailhog
SPRING_MAIL_PORT: 1025
SPRING_MAIL_USERNAME: ""
SPRING_MAIL_PASSWORD: ""
```

### Observe intercepted emails in Mailhog Web UI

Uncomment the following line in `docker-compose.yml` file if you want the `mailhog` web UI be accessible
and allow checking the sent emails.

```yaml
  mailhog:
    image: mailhog/mailhog
    container_name: mailhog
    env_file:
      - ".env"
    ports:
      - "1025:1025" # SMTP server
#      - "8025:8025" # Web UI      <------- uncomment this line
    environment:
      SPRING_MAIL_HOST: ${SPRING_MAIL_HOST}
      SPRING_MAIL_PORT: ${SPRING_MAIL_PORT}
      SPRING_MAIL_USERNAME: ${SPRING_MAIL_USERNAME}
      SPRING_MAIL_PASSWORD: ${SPRING_MAIL_PASSWORD}

```

## Add environment variables to IntelliJ project

All the environment variables in `.env` file are expected to be read by Docker
when one builds the complete image locally and runs it locally or deploys it to the remote server.

But during development it's not alway convenient.
Instead,
one might want
to start only the database service defined in `docker-compose.yml` and run application from within IntelliJ.
In order for IntelliJ to be able to access the database running in the Docker container, it has also required using corresponding environment variables. 
For that, one must add them manually as follows.

### Select this application in the `Run/Debug Configuration`

1. Open Run/Debug Configuration ( `Run` → `Edit Configurations...`) 
2. Select the application's name from the list on the left side pane

<div style="text-align: center;">
<img src="https://gist.github.com/user-attachments/assets/7cebce6d-fd45-4069-bbb9-dddc67578a5f" width="800">
</div>

### Open `Environment Variables` sub-pane

Click on the `Edit environment variables` icon

<div style="text-align: center;">
<img src="https://gist.github.com/user-attachments/assets/1b2e5c70-11d5-4d5f-a642-d83203c5419e" width="800">
</div>

### Fill in the environment variables values

<div style="text-align: center;">
<img src="https://gist.github.com/user-attachments/assets/750dc47a-b98e-4420-9419-4ad605856171" width="500">
</div>

Now, when you run the application ( for example, with `./gradlew run` ), it should be able to connect to the database.