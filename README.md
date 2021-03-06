# Dev News

## Introduction
This is a basic backend API for a developer news site where users can create articles, comment them and assign topics to articles.  

You can make requests and get plain json text responses via curl/Postman. 

## Requirements

* Gradle 6.8.3
* Docker
* Postman or curl for making requests 

The project has these dependencies:
* Spring Web
* Spring JPA
* Spring Validation
* PostgreSQL Driver

## Setup
### Docker
Install [Docker Desktop](https://www.docker.com/products/docker-desktop) and start it up.  

In the project root folder, start the Docker container with `docker-compose up`  

Check the database is running with `docker ps`  

### Gradle
In a new terminal, again in the project root, enter the run command:  

On MacOS or Linux: `./gradlew bootRun`  

On Windows: `gradle bootRun`

## Using the API

### Make requests with Postman or Curl

Install [Postman](https://www.postman.com/downloads/) and start it up.
Create a new collection and start adding requests to the endpoints detailed below.

### Make requests with Curl
Or use [curl](https://curl.se/), a command line tool for getting or sending files using URL syntax.



### Article
Article is the core entity. It represents a news article with an **id**, **title**, **body** (article text content), and **authorName**.

#### Endpoints
Path prefix: `localhost:8080`

| HTTP Method | HTTP Path | Action |
| ------------|-----------|--------|
| `GET`    | `/articles`      | return all articles. |
| `GET`    | `/articles/{id}` | return a specific article based on the provided id.|
| `POST`   | `/articles`      | create a new article.|
| `PUT`    | `/articles/{id}` | update the given article.|
| `DELETE` | `/articles/{id}` | delete the given article.|

### Comments
Users can comment the different articles with a unique **id**, **body**, **authorName** (for the comment), and **article**
on which the comment was posted. Each article can have zero or more comments. 

Example JSON response when requesting a comment:

```json
{
    "id": 1,
    "body": "This article is very well written",
    "authorName": "John Smith",
    "article": {
        "id": 1,
        "title": "10 reasons to learn Spring",
        "body": "In this article I'll be listing 10 reasons why you should learn spring and use it in your next project...",
        "authorName": "John Smith"
    }
}

```
#### Endpoints
Path prefix: `localhost:8080`

| HTTP Method | HTTP Path | Action |
| ------------|-----------|--------|
| `GET`    | `/articles/{articleId}/comments`    | return all comments on article given by `articleId`. |
| `GET`    | `/comments?authorName={authorName}` | return all comments made by author given by `authorName`. |
| `POST`   | `/articles/{articleId}/comments`    | create a new comment on article given by `articleId`. |
| `PUT`    | `/comments/{id}`                    | update the given comment. |
| `DELETE` | `/comments/{id}`                    | delete the given comment. |


### Topics
Articles can be catagorized by topics. Each topic can be applied to zero or many articles and each article can have zero or many topics.

Example JSON response when requesting an article:

```json
{
    "id": 1,
    "title": "10 reasons to learn Spring",
    "body": "In this article I'll be listing 10 reasons why you should learn spring and use it in your next project...",
    "authorName": "John Smith",
    "topics": [
        {
            "id": 1,
            "name": "backend"
        },
        {
            "id": 2,
            "name": "java"
        },
        {
            "id": 3,
            "name": "spring"
        }
    ]
}
```

#### Endpoints
Path prefix: `localhost:8080`

| HTTP Method | HTTP Path | Action |
| ------------|-----------|--------|
| `GET`    | `/topics` | return all topics. |
| `GET`    | `/articles/{articleId}/topics` | return all topics associated with article given by `articleId`. |
| `POST`   | `/articles/{articleId}/topics` | associate the topic with the article given by `articleId`. If topic does not already exist, it is created. |
| `POST`   | `/topics` | create a new topic. |
| `PUT`    | `/topics/{id}` | update the given topic. |
| `DELETE` | `/topics/{id}` | delete the given topic. |
| `DELETE` | `/articles/{articleId}/topics/{topicId}` | delete the association of a topic for the given article. The topic & article themselves remain. |
| `GET`    | `/topics/{topicId}/articles` | return all articles associated with the topic given by `topicId`. |

## Authors and acknowledgements
Thanks to Kwabena Asante-Poku (Back End teacher at SDA), the teaching assistants and fellow SDA classmates.
