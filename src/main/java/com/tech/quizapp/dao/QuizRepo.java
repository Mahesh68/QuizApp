package com.tech.quizapp.dao;

import com.tech.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface QuizRepo extends JpaRepository<Quiz, Integer> {
}
