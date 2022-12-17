package com.example.demo.service;

import com.example.demo.repository.ReleaseCommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReleaseCommitService {
    private final ReleaseCommitRepository releaseCommitRepository;

    @Autowired
    public ReleaseCommitService(ReleaseCommitRepository releaseCommitRepository) {
        this.releaseCommitRepository = releaseCommitRepository;
    }


}