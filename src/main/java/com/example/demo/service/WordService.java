package com.example.demo.service;

import com.example.demo.model.Word;
import com.example.demo.repository.WordRepository;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WordService {
    private final WordRepository wordRepository;
    private static int WORDS_COUNT = 100;
    private static int MIN_OPENAI_LENGTH = 5;
    private static int MIN_BABYSOR_LENGTH = 5;

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
            frequencyAnalyzer.setWordFrequenciesToReturn(WORDS_COUNT * 2);
            // 最小分词长度
            frequencyAnalyzer.setMinWordLength(MIN_OPENAI_LENGTH);
            // 引入中文解析器
            frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());

            // 可以直接从文件中读取
            List<WordFrequency> wordFrequencies;
            wordFrequencies = frequencyAnalyzer.load(
                    new File("D:\\Program\\Idea\\CS209project\\src\\main\\resources\\test_openai.txt"));
            List<Word> openaiWords = new ArrayList<>();
            for (WordFrequency wordFrequency : wordFrequencies) {
                openaiWords.add(new Word(wordFrequency.getWord(), wordFrequency.getFrequency(), "openai/gym"));
            }

            frequencyAnalyzer.setMinWordLength(MIN_BABYSOR_LENGTH);
            List<Word> babysorWords = new ArrayList<>();
            wordFrequencies = frequencyAnalyzer.load(
                    new File("D:\\Program\\Idea\\CS209project\\src\\main\\resources\\test_babysor.txt"));
            for (WordFrequency wordFrequency : wordFrequencies) {
                babysorWords.add(new Word(wordFrequency.getWord(), wordFrequency.getFrequency(), "babysor/MockingBird"));
            }


            openaiWords.stream()
                    .filter(word -> !getFilterSet().contains(word.getText()))
                    .limit(100)
                    .forEach(wordRepository::save);
            babysorWords.stream()
                    .filter(word -> !getFilterSet().contains(word.getText()))
                    .limit(100)
                    .forEach(wordRepository::save);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<String> getFilterSet() {
        Set<String> set = new HashSet<String>();
        set.add("");
        set.add("don't");
        set.add("about");
        set.add("could");
        set.add("being");
        set.add("would");
        set.add("which");
        set.add("there");
        set.add("other");
        set.add("this");
        set.add("these");
        set.add("doesn't");
        set.add("before");
        set.add("looks");
        set.add("without");
        set.add("while");
        set.add("from");
        set.add("really");
        set.add("here");
        set.add("recent");
        set.add("https");
        set.add("false");
        set.add("should");
        set.add("doesn'");
        set.add("aidatatang_200zh");
        set.add("pretrained-11-7-21_75kpt");
        set.add("their");
        set.add("using");
        set.add("because");
        set.add("still");
        set.add("please");
        set.add("after");
        set.add("however");
        set.add("where");
        set.add("since");
        set.add("maybe");
        set.add("above");
        set.add("though");
        set.add("datasets_root");
        set.add("demo_toolboxpy");
        set.add("**vars");
        set.add("state_dict");
        set.add("batch_size");
        set.add("**summary");
        set.add("synthesizer_trainpy");
        set.add("***@******&gt");
        set.add("**kwargs");
        set.add("preprocess_dataset");
        set.add("*args");
        set.add("cannot");
        set.add("@babysor");
        set.add("------------------");
        set.add("------------");
        set.add("----------------");
        set.add("---------------");
        set.add("state_steps");
        set.add("**env");
        set.add("synthesizer\\saved_models");
        set.add("syn_models_dir");
        set.add("voc_models_dir");
        set.add("that'");
        set.add("going");
        set.add("rather");
        set.add("requirementstxt");
        set.add("query_devices");
        set.add("enc_models_dir");
        set.add("thanks");
        set.add("environments");
        set.add("seems");
        set.add("instead");
        set.add("actually");
        set.add("thank");
        set.add("currently");
        set.add("probably");
        set.add("already");
        set.add("through");
        set.add("those");
        set.add("makes");
        set.add("named");
        set.add("saved_models");
        set.add("between");
        set.add("directly");
        set.add("tried");
        set.add("think");
        set.add("added");
        set.add("current");
        set.add("having");
        set.add("trying");
        set.add("might");
        set.add("first");
        set.add("possible");
        set.add("encoder_projweight");
        return set;
    }
}