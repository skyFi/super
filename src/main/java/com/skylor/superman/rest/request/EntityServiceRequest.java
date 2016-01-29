package com.skylor.superman.rest.request;

import com.skylor.superman.rest.RestServiceClient;

/**
 * Created by darcy on 2016/1/29.
 */
public class EntityServiceRequest<T> extends RestServiceRequest<T> {

    protected String id;

    public EntityServiceRequest(RestServiceClient restServiceClient,
                                String scope,
                                Class<T> entityType,
                                String id) {
        super(restServiceClient, scope, entityType);
        this.id = id;
    }

    public OperationServiceRequest<T> operation(String operation) {
        return new OperationServiceRequest<T>(restServiceClient, scope, entityType, id, operation);
    }

    public T retrieve() {
        return restServiceClient.retrieve(scope, entityType, id);
    }

    public T update(T entity) {
        return restServiceClient.update(scope, id, entity, entityType);
    }

    public <C> C update(T entity, Class<C> returnType) {
        return restServiceClient.update(scope, id, entity, returnType);
    }

    public T delete() {
        return restServiceClient.delete(scope, entityType, id);
    }

}
