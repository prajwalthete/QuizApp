package com.prajwal.QuizApp.service;

import com.prajwal.QuizApp.entity.Question;
import com.prajwal.QuizApp.repository.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.valueOf(200));

        } catch (Exception e) {

            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<Question> getQuestionById(Long id) {
        try {
            Question question = questionDao.findById(id).orElse(null);
            if (question == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(question, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

   /* public Question getQuestionById(Long id) {
        return questionDao.findById(id).orElse(null);
    }*/


    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {

        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);


    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestion(Long id) {
        try {
            questionDao.deleteById(id);
            return new ResponseEntity<>("Question has been deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete the question", HttpStatus.BAD_REQUEST);
        }
    }

   /* public void deleteQuestion(Long id) {
        questionDao.deleteById(id);
    }*/


    public ResponseEntity<String> updateQuestion(Long id, Question question) {
        try {
            Question existingQuestion = questionDao.findById(id).orElse(null);
            if (existingQuestion == null) {
                return new ResponseEntity<>("Question does not exist", HttpStatus.BAD_REQUEST);
            }
            existingQuestion.setCategory(question.getCategory());
            existingQuestion.setDifficultyLevel(question.getDifficultyLevel());
            existingQuestion.setOption1(question.getOption1());
            existingQuestion.setOption2(question.getOption2());
            existingQuestion.setOption3(question.getOption3());
            existingQuestion.setOption4(question.getOption4());
            existingQuestion.setQuestionTitle(question.getQuestionTitle());
            existingQuestion.setRightAnswer(question.getRightAnswer());
            Question updatedQuestion = questionDao.save(existingQuestion);
            return new ResponseEntity<>(updatedQuestion.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update the question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*public ResponseEntity<String> updateQuestion(Long id, Question question) {
        Question existingQuestion = questionDao.findById(id).orElse(null);
        if (existingQuestion == null) {
            return new ResponseEntity<>("Question does not exist", HttpStatus.BAD_REQUEST);
        }
        existingQuestion.setCategory(question.getCategory());
        existingQuestion.setDifficultyLevel(question.getDifficultyLevel());
        existingQuestion.setOption1(question.getOption1());
        existingQuestion.setOption2(question.getOption2());
        existingQuestion.setOption3(question.getOption3());
        existingQuestion.setOption4(question.getOption4());
        existingQuestion.setQuestionTitle(question.getQuestionTitle());
        existingQuestion.setRightAnswer(question.getRightAnswer());
        String updatedQuestion = String.valueOf(questionDao.save(existingQuestion));
        return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
    }*/
}
