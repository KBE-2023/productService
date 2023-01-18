package com.example.helloworld.repo;

import com.example.helloworld.model.ABC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FetchDataService extends JpaRepository<ABC, Integer> {
    @Override
    List<ABC> findAll();
}
