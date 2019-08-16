package com.atb.ninja.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.atb.ninja.models.documents.Expression;

public interface ExpressionDao extends ReactiveMongoRepository<Expression, String>
{

}
