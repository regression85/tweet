package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.TweetEntity;
import com.example.demo.repository.TweetRepository;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@RestController
@RequestMapping("/tweet")
class Controller {

    @Autowired
    private TweetRepository repository;


    @GetMapping(value = "/{id}")
    public String findById(@PathVariable("id") Long id) {
    	TweetEntity entity = new TweetEntity();
    	List<String> list = new ArrayList<String>();
    	try {
    		list = searchtweets();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(!list.isEmpty()) {
    		entity.setId(id);
    		entity.setTweet(list.get(0));
    		repository.save(entity);
    		return "TweetEntity guardado con id: "+id +"tweet : "+entity.getTweet();
    	}else {
    		return "TweetEntity no guardado";
    		
    	}
    	
    }
    
    public static List<String> searchtweets() throws TwitterException {
    	 
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query("escalada");
        QueryResult result = twitter.search(query);
        
        return result.getTweets().stream()
          .map(item -> item.getText())
          .collect(Collectors.toList());
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Long create(@RequestBody Foo resource) {
//        Preconditions.checkNotNull(resource);
//        return service.create(resource);
//    }
//
//    @PutMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void update(@PathVariable( "id" ) Long id, @RequestBody Foo resource) {
//        Preconditions.checkNotNull(resource);
//        RestPreconditions.checkNotNull(service.getById(resource.getId()));
//        service.update(resource);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void delete(@PathVariable("id") Long id) {
//        service.deleteById(id);
//    }

}