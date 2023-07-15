package com.prajwal.QuizApp.repository;

import com.prajwal.QuizApp.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz,Long> {
}
