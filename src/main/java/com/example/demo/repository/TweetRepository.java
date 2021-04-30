package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.TweetEntity;

public interface TweetRepository extends CrudRepository < TweetEntity, Long > {
	
}
