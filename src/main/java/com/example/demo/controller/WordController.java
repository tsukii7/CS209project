package com.example.demo.controller;

import com.example.demo.model.Word;
import com.example.demo.service.WordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/words")
public class WordController {
  private final WordService wordService;

  public WordController(WordService wordService) {
    this.wordService = wordService;
  }

  @GetMapping("/frequency")
  public List<Word> getWords(@RequestParam String repoName) {
    return wordService.getWords(repoName);
  }
}