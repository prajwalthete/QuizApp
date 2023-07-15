package com.prajwal.QuizApp.service;

import com.prajwal.QuizApp.entity.Question;
import com.prajwal.QuizApp.repository.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        return questionDao.findAll();

    }

    public Question getQuestionById(Long id) {
        return questionDao.findById(id).orElse(null);
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    public Question addQuestion(Question question) {
        return questionDao.save(question);
    }

    public void deleteQuestion(Long id) {
        questionDao.deleteById(id);
    }

    public Question updateQuestion(Long id, Question question) {
        Question existingQuestion = questionDao.findById(id).orElse(null);
        if (existingQuestion == null) {
            return null; // or throw an exception

        }
        existingQuestion.setCategory(question.getCategory());
        existingQuestion.setDifficultyLevel(question.getDifficultyLevel());
        existingQuestion.setOption1(question.getOption1());
        existingQuestion.setOption2(question.getOption2());
        existingQuestion.setOption3(question.getOption3());
        existingQuestion.setOption4(question.getOption4());
        existingQuestion.setQuestionTitle(question.getQuestionTitle());
        existingQuestion.setRightAnswer(question.getRightAnswer());
        return questionDao.save(existingQuestion);
    }
}
