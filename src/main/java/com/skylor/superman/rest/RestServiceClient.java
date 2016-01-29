package com.skylor.superman.rest;

import java.util.List;

import com.skylor.superman.rest.model.EntityQuery;
import com.skylor.superman.rest.request.RestServiceRequest;

/**
 * Created by darcy on 2016/1/29.
 */
public interface RestServiceClient {

    public <T> T retrieve(String scope, Class<T> entityType, String id);

    public <T> List<T> find(String scope, Class<T> entityType, EntityQuery query);

    public <T> T save(String scope, Object entity, Class<T> resultType);

    public <T> T update(String scope, String id, Object entity, Class<T> resultType);

    public <T> T delete(String scope, Class<T> entityType, String id);

    public long count(String scope, Class<?> entityType, EntityQuery query);

    public <T> T operate(String scope, Class<?> entityType, String id, String operation, Object[] args, Class<T> resultType);

    public <T> RestServiceRequest<T> serviceRequest(String scope, Class<T> entityType);
}
