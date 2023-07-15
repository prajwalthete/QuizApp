package com.prajwal.QuizApp.service;

import com.prajwal.QuizApp.entity.Question;
import com.prajwal.QuizApp.entity.QuestionWrapper;
import com.prajwal.QuizApp.entity.Quiz;
import com.prajwal.QuizApp.entity.Response;
import com.prajwal.QuizApp.repository.QuestionDao;
import com.prajwal.QuizApp.repository.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, Long numQ, String title) {

        List<Question> questions = questionDao.getQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Long id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDb = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();

        for (Question q : questionsFromDb) {
            QuestionWrapper wrapper = new QuestionWrapper(
                    q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());

            questionsForUser.add(wrapper);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(Long.valueOf(id)).get();
        List<Question> questions = quiz.getQuestions();
        int count = 0;
        int i = 0;
        for (Response response : responses) {
            if (response.getResponse().equals(questions.get(i).getRightAnswer()))
                count++;

            i++;

        }

        return new ResponseEntity<>(count, HttpStatus.OK);
    }


   /* public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Long id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDb = quiz.get().getQuestions();

        List<QuestionWrapper> questionsForUser = questionsFromDb.stream()
                .map(q -> new QuestionWrapper(
                        q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }*/
}
