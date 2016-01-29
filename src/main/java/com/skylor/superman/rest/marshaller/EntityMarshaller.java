package com.skylor.superman.rest.marshaller;

import java.util.List;

/**
 * Created by darcy on 2016/1/29.
 */
public interface EntityMarshaller {

    public String marshal(Object entity);

    public <T> T unmarshal(String source, Class<T> type);

    public <T> List<T> unmarshalCollection(String source, Class<T> type);

    public <T> T updateBySource(String source, T entity);

}
