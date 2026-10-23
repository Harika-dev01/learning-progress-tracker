package com.learningtracker.progress_service.client;

import com.learningtracker.progress_service.dto.CourseResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CourseClient {

    private final WebClient webClient;

    public CourseClient() {

        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
    }

    public CourseResponse getCourse(Long courseId) {

        return webClient.get()
                .uri("/api/courses/" + courseId)
                .retrieve()
                .bodyToMono(CourseResponse.class)
                .block();
    }
}
