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


## Docker Images

- harikaveduru/learning-course-service:1.0
- harikaveduru/learning-progress-service:1.0

## Kubernetes Deployment

Start Minikube:

minikube start --driver=docker

Create the local Kubernetes secret from the provided example:

cp kubernetes/mysql-secret.example.yaml kubernetes/mysql-secret.yaml

Edit mysql-secret.yaml and replace the placeholder credentials.

Deploy:

kubectl apply -f kubernetes/mysql-secret.yaml
kubectl apply -f kubernetes/mysql-pvc.yaml
kubectl apply -f kubernetes/mysql-deployment.yaml
kubectl apply -f kubernetes/mysql-service.yaml
kubectl apply -f kubernetes/course-deployment.yaml
kubectl apply -f kubernetes/course-service.yaml
kubectl apply -f kubernetes/progress-deployment.yaml
kubectl apply -f kubernetes/progress-service.yaml

Check the deployment:

kubectl get pods
kubectl get services
kubectl get pvc

## Access

Get the service URLs:

minikube service course-service --url
minikube service progress-service --url

Example Progress API:

/api/progress/details/101

## Scaling

Course Service and Progress Service can be scaled independently.

Example:

kubectl scale deployment course-service --replicas=2

Restore it:

kubectl scale deployment course-service --replicas=1

## Persistent Storage

MySQL uses a Kubernetes PersistentVolumeClaim (`mysql-pvc`).
Database data therefore survives replacement of the MySQL pod.

## Security

Database credentials are supplied through a Kubernetes Secret.
The real `mysql-secret.yaml` file is excluded from Git.
