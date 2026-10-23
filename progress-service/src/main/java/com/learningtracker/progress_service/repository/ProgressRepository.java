package com.learningtracker.progress_service.repository;

import com.learningtracker.progress_service.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository
        extends JpaRepository<Progress, Long> {

    List<Progress> findByStudentId(Long studentId);

    Optional<Progress> findFirstByStudentId(Long studentId);
}
