package com.atb.ninja.models.documents;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "expressions")
public class Expression {
	
	
	@Id
	private String english;
	
	private String spanish;
	
	
	public Expression() {
	}
	
	public Expression(String english, String spanish) {
		this.english = english;
		this.spanish = spanish;
	}
	public String getEnglish() {
		return english;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	public String getSpanish() {
		return spanish;
	}
	public void setSpanish(String spanish) {
		this.spanish = spanish;
	}
	

	@Override
	public String toString() {
		return "Expression [english=" + english + ", spanish=" + spanish + "]";
	}
	
}
