package com.tech.quizapp.service;

import com.tech.quizapp.dao.QuestionRepo;
import com.tech.quizapp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        return new ResponseEntity<>(questionRepo.findByCategory(category), HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionRepo.save(question);
            return new ResponseEntity<>("Successfully added question", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> updateQuestion(Question question) {
        Optional<Question> questionOptional = questionRepo.findById(question.getId());
        if (questionOptional.isPresent()) {
            Question questionToUpdate = questionOptional.get();
            questionToUpdate.setQuestion_title(question.getQuestion_title());
            questionRepo.save(question);
            return new ResponseEntity<>("Successfully updated question", HttpStatus.OK);
        }

        return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
    }
}
