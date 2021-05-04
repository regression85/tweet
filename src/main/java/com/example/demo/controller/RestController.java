package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.TweetEntity;
import com.example.demo.repository.TweetRepository;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.api.TrendsResources;

@RestController
@RequestMapping("/tweet")
class Controller {

    @Autowired
    private TweetRepository repository;


    @GetMapping(value = "busqueda/{busqueda}")
    public List<TweetEntity> findById(@PathVariable("busqueda") String busqueda,
    		@RequestParam(required = false, defaultValue="1500") Long seguidores,
    		@RequestParam(required = false, defaultValue="ES") String idioma 
    		) throws Exception {
    	
    	validarIdioma(idioma);
    	
    	TweetEntity entity = new TweetEntity();
    	List<TweetEntity> list = new ArrayList<TweetEntity>();
    	try {
    		list = searchtweets(busqueda, seguidores, idioma );
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(!list.isEmpty()) {
    		
    		repository.saveAll(list);
    		return list;
    		
    	}else {
    		return list;
    		
    	}
    	
    }
    
    @GetMapping(value = "validar/{id}")
    public String validarTweet(@PathVariable Long id) {
    	
    	 Optional<TweetEntity> entityOptional =repository.findById(id);
    	 TweetEntity entity = entityOptional.get();
    	 entity.setValidacion(true);
    	 repository.save(entity);
    	 
    	 return "Tweet con id "+ id +"validado";
    }
    
    @GetMapping(value = "consultarValidados/{usuario}")
    public List<TweetEntity> validarTweet(@PathVariable String usuario) {
    	 List<TweetEntity> lista =   repository.findByUsuario(usuario);
    	 List<TweetEntity> listaValidados = new ArrayList<TweetEntity>();
    	 for (TweetEntity entity: lista) {
    		 
    		 if (entity.isValidacion()){
    			 listaValidados.add(entity);
    		 }
    		 
    	 }
    	 return listaValidados;
    }
    
    @GetMapping(value = "consultarRank")
    public TrendsResources  consultarRank() {
    	Twitter twitter = TwitterFactory.getSingleton();
    	TrendsResources trend= twitter.trends();
   //TODO
    	return null;
    }
    
    
    
    

    public static List<TweetEntity> searchtweets(String busqueda, Long seguidores, String idioma) throws TwitterException {
   	 
        Twitter twitter = TwitterFactory.getSingleton();
        String lang = "lang;"+idioma;
  
        Query query = new Query().lang(idioma).query(busqueda).count(100);
        QueryResult result = twitter.search(query);
       
        List<Status> tweets= result.getTweets();
        List<TweetEntity> listaEntity = new ArrayList<TweetEntity>();
        for (Status tweet : tweets) {
        	if (tweet.getUser().getFollowersCount()>seguidores) {
        		TweetEntity entity = new TweetEntity(tweet.getId(),tweet.getUser().getName(), tweet.getText(), false);
        		listaEntity.add(entity);
        	}
           }
        return  listaEntity;

    }

    private void validarIdioma(String idioma) throws Exception {
    	
    	if (!"ES".equals(idioma) && !"IT".equals(idioma) && !"FR".equals(idioma)) {
    		throw new Exception("Solo permitidos ES. IT, FR");
    	}
    	
    }
   


}