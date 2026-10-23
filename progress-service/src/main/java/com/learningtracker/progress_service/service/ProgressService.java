package com.learningtracker.progress_service.service;

import com.learningtracker.progress_service.dto.EnrollRequest;
import com.learningtracker.progress_service.model.Progress;
import com.learningtracker.progress_service.model.ProgressStatus;
import com.learningtracker.progress_service.repository.ProgressRepository;
import org.springframework.stereotype.Service;
import com.learningtracker.progress_service.client.CourseClient;
import com.learningtracker.progress_service.dto.CourseResponse;
import com.learningtracker.progress_service.dto.ProgressResponse;
import com.learningtracker.progress_service.dto.UpdateProgressRequest;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final CourseClient courseClient;

    public ProgressService(
        ProgressRepository progressRepository,
        CourseClient courseClient) {

	    this.progressRepository = progressRepository;
	    this.courseClient = courseClient;
    }

    public Progress enrollStudent(EnrollRequest request) {

        Progress progress = new Progress();

        progress.setStudentId(request.getStudentId());
        progress.setStudentName(request.getStudentName());
        progress.setCourseId(request.getCourseId());

        progress.setCompletedLessons(0);
        progress.setStatus(ProgressStatus.NOT_STARTED);
        progress.setEnrollmentDate(LocalDate.now());

        return progressRepository.save(progress);
    }

    public List<Progress> getStudentProgress(Long studentId) {

        return progressRepository.findByStudentId(studentId);
    }
    
    public ProgressResponse getProgressDetails(
        Long studentId) {

    Progress progress = progressRepository
            .findByStudentId(studentId)
            .stream()
            .findFirst()
            .orElseThrow(() ->
                    new RuntimeException(
                            "Student not found"));

    CourseResponse course =
            courseClient.getCourse(
                    progress.getCourseId());

    ProgressResponse response =
            new ProgressResponse();

    response.setStudentId(
            progress.getStudentId());

    response.setStudentName(
            progress.getStudentName());

    response.setCourseName(
            course.getTitle());

    response.setCompletedLessons(
            progress.getCompletedLessons());

    response.setTotalLessons(
            course.getTotalLessons());

    int percentage =
            (progress.getCompletedLessons() * 100)
                    / course.getTotalLessons();

    response.setProgressPercentage(
            percentage);

    response.setStatus(
            progress.getStatus().name());

    return response;
}

public Progress updateProgress(
        UpdateProgressRequest request) {

    Progress progress =
            progressRepository
                    .findFirstByStudentId(
                            request.getStudentId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Student not found"));

    progress.setCompletedLessons(
            request.getCompletedLessons());

    CourseResponse course =
            courseClient.getCourse(
                    progress.getCourseId());

    if (request.getCompletedLessons() == 0) {

        progress.setStatus(
                ProgressStatus.NOT_STARTED);

    } else if (
            request.getCompletedLessons()
                    >= course.getTotalLessons()) {

        progress.setStatus(
                ProgressStatus.COMPLETED);

    } else {

        progress.setStatus(
                ProgressStatus.IN_PROGRESS);
    }

    return progressRepository.save(
            progress);
}
}
