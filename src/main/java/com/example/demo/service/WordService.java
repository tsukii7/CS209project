package com.example.demo.service;

import com.example.demo.model.Word;
import com.example.demo.repository.WordRepository;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordService {
    private final WordRepository wordRepository;

    @Autowired
    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public List<Word> getWords(String repoName) {
        return wordRepository.findByRepoNameOrderByCountDesc(repoName);
    }

    public void addWords() {
        try {
            // 新建FrequencyAnalyzer 对象
            FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
            // 设置分词返回数量(频率最高的600个词)
            frequencyAnalyzer.setWordFrequenciesToReturn(100);
            // 最小分词长度
            frequencyAnalyzer.setMinWordLength(5);
            // 引入中文解析器
            frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());

            // 可以直接从文件中读取
            List<WordFrequency> wordFrequencies;
            wordFrequencies = frequencyAnalyzer.load(
                    new File("D:\\Program\\Idea\\CS209project\\src\\main\\resources\\test_openai.txt"));
            List<Word> words = new ArrayList<>();
            for (WordFrequency wordFrequency : wordFrequencies) {
                words.add(new Word(wordFrequency.getWord(),wordFrequency.getFrequency(),"openai/gym"));
            }

            frequencyAnalyzer.setMinWordLength(3);
            wordFrequencies = frequencyAnalyzer.load(
                    new File("D:\\Program\\Idea\\CS209project\\src\\main\\resources\\test_babysor.txt"));
            for (WordFrequency wordFrequency : wordFrequencies) {
                words.add(new Word(wordFrequency.getWord(),wordFrequency.getFrequency(),"babysor/MockingBird"));
            }
            wordRepository.saveAll(words);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}