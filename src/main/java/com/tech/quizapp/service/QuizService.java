package com.tech.quizapp.service;

import com.tech.quizapp.dao.QuestionRepo;
import com.tech.quizapp.dao.QuizRepo;
import com.tech.quizapp.model.Question;
import com.tech.quizapp.model.QuestionWrapper;
import com.tech.quizapp.model.Quiz;
import com.tech.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepo quizRepo;

    @Autowired
    private QuestionRepo questionRepo;

    public ResponseEntity<String> createQuiz(String title, String category, int numQ) {
        System.out.println("Creating Quiz with title: " + title + " category: " + category + " num: " + numQ);

        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizRepo.save(quiz);

        return new ResponseEntity<>("Created quiz", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsofId(int id) {
        Optional<Quiz> quizId = quizRepo.findById(id);
        if (quizId.isPresent()) {
            List<Question> questionsFromDb = quizId.get().getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<>();

            for(Question q: questionsFromDb) {
                QuestionWrapper qw = new QuestionWrapper(q.getId(),
                        q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4(), q.getQuestion_title());
                questionsForUser.add(qw);
            }

            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
        int result = 0;
        List<Question> questions = quizRepo.findById(id).get().getQuestions();
        int i=0;
        for(Response r: responses) {
            if(r.getResponse().equals(questions.get(i).getRight_answer())) {
                result++;
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
