package com.learningtracker.progress_service.client;

import com.learningtracker.progress_service.dto.CourseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CourseClient {

    private final WebClient webClient;

    public CourseClient(
            @Value("${course-service.base-url}") String courseServiceBaseUrl) {

        this.webClient = WebClient.builder()
                .baseUrl(courseServiceBaseUrl)
                .build();
    }

    public CourseResponse getCourse(Long courseId) {

        return webClient.get()
                .uri("/api/courses/{courseId}", courseId)
                .retrieve()
                .bodyToMono(CourseResponse.class)
                .block();
    }
}
