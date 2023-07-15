package com.prajwal.QuizApp.repository;

import com.prajwal.QuizApp.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Long> {

    List<Question> findByCategory(String category);
}
