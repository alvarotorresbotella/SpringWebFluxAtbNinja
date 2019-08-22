package com.atb.ninja.models.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import com.atb.ninja.models.dao.ExpressionDao;
import com.atb.ninja.models.documents.Expression;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ExpressionController {
	
	@Autowired
	private ExpressionDao dao;
	
	private static final Logger log=LoggerFactory.getLogger(ExpressionController.class); 
	
	@GetMapping({"/list","/"})
	public String listar(Model model)
	{
		Flux<Expression> ctrlExpressionFlux=dao.findAll(new Sort(Sort.Direction.ASC,"english"));
		ctrlExpressionFlux.subscribe(
				flux -> log.info(flux.toString()),
					error->log.error(error.getMessage()),
						new Runnable() {
							public void run() {
								log.info("Expression collection processed...");
			}
		});
		model.addAttribute("expressions", ctrlExpressionFlux);
		model.addAttribute("tittle","English expressions dictionary");
		return "list";
	}
	
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {

		Expression expression=new Expression();
		model.put("expression", expression);
		model.put("tittle", "Introduce the expression");
		model.put("button","send");
		model.put("create","true");
		return "form";
	}
	
	@RequestMapping(value="/form/{english}")
	public String editar(@PathVariable(value="english") String id, Map<String, Object> model) {
		
		if(id.equals(""))
		{
			return "list";
		}
		
	Flux<Expression> allExpressions = dao.findAll();
	Expression modelExpression=new Expression();	
	Mono<Expression> expression = allExpressions
				.filter(p -> p.getEnglish().equals(id))
				.next()
				.doOnNext( prod -> {modelExpression.setEnglish(prod.getEnglish());  modelExpression.setSpanish(prod.getSpanish());} );
		         
					expression.subscribe(flux -> log.info(flux.toString()),error -> log.error(error.getMessage()),new Runnable() {
						public void run() {
							log.info("flux edit controller procesed suscesfully...");
						}
					});
		model.put("expression", modelExpression);
		model.put("button","send");
		model.put("edit","true");
		model.put("tittle", "Edit Expression");
		return "form";
	
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String save(@Valid Expression expression, BindingResult result, Model model, SessionStatus status) {

		if(expression.getEnglish().contentEquals(""))
		{
			model.addAttribute("tittle","All the fields must be filled");
			model.addAttribute("button","send");
			return "form";
		}
	
		if (result.hasErrors()) {
			model.addAttribute("tittle","English expressions");
			return "form";
		}
		Mono<Expression> fluxSaveController=dao.save(expression);
		
		fluxSaveController.subscribe(e -> log.info(e.toString()),
				error -> log.error(error.getMessage()),new Runnable() {
					public void run() {
						log.info("Controller save executed...");
					}
				});
		
		status.setComplete();
		return "redirect:list";
	}
	
	@RequestMapping(value="/delete/{english}")
	public String eliminar(@PathVariable(value="english") String id) {
		
		dao.deleteById(id).subscribe( e-> log.info(e.toString()) , error -> log.error(error.getMessage()) , new Runnable() {
			public void run() {
				log.info("Delete flux controller processed...");
			}
		});
		
		return "redirect:/list";
	}
	
	

}
