package com.atb.ninja;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.atb.ninja.models.dao.ExpressionDao;
import com.atb.ninja.models.documents.Expression;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringWebFluxAtbNinjaApplication implements CommandLineRunner{
	
	@Autowired
	private ExpressionDao dao;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger log=LoggerFactory.getLogger(SpringWebFluxAtbNinjaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringWebFluxAtbNinjaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// Descomentar para resetear la colección
		//  mongoTemplate.dropCollection("expressions").subscribe();
		  
		/*Descomentar para tener una carga incial de datos 
		  Flux<Expression> fluxExpression = Flux .just( new Expression("Ask for",
		  "perdir, preguntar por"), new Expression("I can tell", "Se nota"), new
		  Expression("I can (not) afford", "(No) puedo permitirme"), new
		  Expression("Be in the running", "Estar en la pomada")
		  
		  ).flatMap(flux -> dao.save(flux));
		  
		  fluxExpression .subscribe(flux -> log.info(flux.toString()),error ->
		  error.getMessage(),new Runnable() { public void run() {
		  log.info("Flujo procesado...."); } });
	*/

	};

}
