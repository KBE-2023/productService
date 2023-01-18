package com.example.helloworld.repo;

import com.example.helloworld.model.ABC;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends CrudRepository<ABC, Integer> {


}