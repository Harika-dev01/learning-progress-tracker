package com.learningtracker.progress_service.controller;

import com.learningtracker.progress_service.dto.EnrollRequest;
import com.learningtracker.progress_service.model.Progress;
import com.learningtracker.progress_service.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.learningtracker.progress_service.dto.ProgressResponse;
import com.learningtracker.progress_service.dto.UpdateProgressRequest;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<Progress> enrollStudent(
            @RequestBody EnrollRequest request) {

        return ResponseEntity.ok(
                progressService.enrollStudent(request)
        );
    }

    /*@GetMapping("/student/{studentId}")
    public ResponseEntity<List<Progress>> getStudentProgress(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                progressService.getStudentProgress(studentId)
        );
    }*/
    
    @GetMapping("/details/{studentId}")
    public ResponseEntity<ProgressResponse>
	getProgressDetails(
		@PathVariable Long studentId) {

	    return ResponseEntity.ok(
		    progressService
		            .getProgressDetails(
		                    studentId));
    }
    
    @PostMapping("/update")
	public ResponseEntity<Progress> updateProgress(
		@RequestBody UpdateProgressRequest request) {

	    return ResponseEntity.ok(
		    progressService
		            .updateProgress(request));
	}
}
