package com.learningtracker.courseservice.controller;

import com.learningtracker.courseservice.dto.CourseRequest;
import com.learningtracker.courseservice.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {

    private final CourseService courseService;

    public ViewController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "courses",
                courseService.getAllCourses()
        );

        return "index";
    }

    @GetMapping("/courses/new")
    public String createCourseForm(Model model) {

        model.addAttribute(
                "course",
                new CourseRequest()
        );

        return "add-course";
    }

    @PostMapping("/courses/new")
    public String createCourse(
            @ModelAttribute CourseRequest request) {

        courseService.createCourse(request);

        return "redirect:/";
    }
}
