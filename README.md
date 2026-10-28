# Learning Progress Tracker

A cloud-native, microservice-based application for managing courses, student enrolments, and learning progress.

The project demonstrates REST APIs, programmatic communication between microservices, Docker containerization, Kubernetes deployment, persistent database storage, external access, and independent horizontal scaling.

## Application overview

The Learning Progress Tracker consists of two custom Spring Boot microservices and a MySQL database.
- Course Service - Spring Boot REST API, port 8081
- Progress Service - Spring Boot REST API, port 8082
- MySQL - persistent database
- Kubernetes - deployment and service management

### Course Service

The Course Service manages course information, including:

- Course title
- Description
- Category
- Total number of lessons

It provides a REST API and runs on port `8081`.

### Progress Service

The Progress Service manages:

- Student enrolments
- Completed lessons
- Progress status
- Progress percentage

It provides a REST API and runs on port `8082`.

When detailed progress information is requested, Progress Service programmatically calls the Course Service REST API to obtain the course title and total number of lessons.

### MySQL

MySQL runs as a separate Kubernetes workload and contains two logical databases:

- `course_db`
- `progress_db`

Its data is stored on a Kubernetes PersistentVolumeClaim so that the data survives MySQL pod replacement and infrastructure restarts.

## Architecture

```text
                     Outside Kubernetes
                            |
                     Browser or curl
                            |
              +-------------+-------------+
              |                           |
      Course Service              Progress Service
        Port 8081                    Port 8082
              |                           |
              |<------ REST request ------|
              |                           |
              +------------+--------------+
                           |
                    MySQL Service
                           |
              +------------+-------------+
              |                          |
          course_db                 progress_db
                           |
                 PersistentVolumeClaim


Database credentials are supplied through a Kubernetes Secret.
The real `mysql-secret.yaml` file is excluded from Git.
