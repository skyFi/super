package com.skylor.superman.rest;

import java.net.URLEncoder;
import java.util.List;

import javax.activation.MimeType;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.skylor.superman.exception.RestServiceException;
import com.skylor.superman.exception.RestServiceRequestException;
import com.skylor.superman.rest.marshaller.EntityJsonMarshaller;
import com.skylor.superman.rest.model.EntityQuery;
import com.skylor.superman.rest.model.EntityUri;
import com.skylor.superman.rest.request.RestServiceRequest;
import com.skylor.superman.util.StringValueUtil;

/**
 * Created by darcy on 2016/1/29.
 */
public class CommonRestServiceClient implements RestServiceClient {

    protected String serverUrl;

    protected HttpClient httpClient;

    private EntityJsonMarshaller entityJsonMarshaller = new EntityJsonMarshaller();

    public CommonRestServiceClient(String serverUrl) {
        this(serverUrl, null);
    }

    public CommonRestServiceClient(String serverUrl, HttpClient httpClient) {
        this.serverUrl = serverUrl;
        this.httpClient = httpClient;

        if (this.httpClient == null) {
            RequestConfig config = RequestConfig.custom()
                    .setSocketTimeout(30 * 1000)
                    .setConnectTimeout(30 * 1000)
                    .build();

            this.httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        }
    }

    @Override
    public <T> T retrieve(String scope, Class<T> entityType, String id) {
        try {
            String uri = serverUrl + new EntityUri(scope, entityType.getSimpleName(), id).toUri();
            HttpGet httpGet = new HttpGet(uri);
            String response = executeRequest(httpGet);

            if (StringUtils.isEmpty(response)) {
                return null;
            }

            return entityJsonMarshaller.unmarshal(response, entityType);
        } catch (RestServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new RestServiceRequestException(e.getMessage(), e);
        }
    }

    @Override
    public <T> List<T> find(String scope, Class<T> entityType, EntityQuery query) {
        try {
            String uri = serverUrl + new EntityUri(scope, entityType.getSimpleName(), null).toUri();
            String queryString = query.buildQueryString();

            HttpGet httpGet = new HttpGet(uri + "?" + URLEncoder.encode(queryString));
            String response = executeRequest(httpGet);

            if (StringUtils.isEmpty(response)) {
                return null;
            }

            return entityJsonMarshaller.unmarshalCollection(response, entityType);
        } catch (RestServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new RestServiceRequestException(e.getMessage(), e);
        }
    }

    @Override
    public long count(String scope, Class<?> entityType, EntityQuery query) {
        try {

            String uri = serverUrl + new EntityUri(scope, entityType.getSimpleName(), null).toUri();
            String queryString = query.forCount().buildQueryString();

            HttpGet httpGet = new HttpGet(uri + "?" + queryString);
            String response = executeRequest(httpGet);

            return Long.parseLong(response);
        } catch (RestServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new RestServiceRequestException(e.getMessage(), e);
        }
    }

    @Override
    public <T> T save(String scope, Object entity, Class<T> resultType) {
        try {
            String uri = serverUrl + new EntityUri(scope, entity.getClass().getSimpleName(), null).toUri();

            HttpPost httpPost = new HttpPost(uri);
            StringEntity stringEntity = new StringEntity(entityJsonMarshaller.marshal(entity), ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);

            String response = executeRequest(httpPost);

            if (StringUtils.isEmpty(response)) {
                return null;
            }

            return entityJsonMarshaller.unmarshal(response, resultType);
        } catch (RestServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new RestServiceRequestException(e.getMessage(), e);
        }
    }

    @Override
    public <T> T update(String scope, String id, Object entity, Class<T> resultType) {
        try {
            String uri = serverUrl + new EntityUri(scope, entity.getClass().getSimpleName(), id).toUri();

            HttpPut httpPut = new HttpPut(uri);
            StringEntity stringEntity = new StringEntity(entityJsonMarshaller.marshal(entity), ContentType.APPLICATION_JSON);
            httpPut.setEntity(stringEntity);

            String response = executeRequest(httpPut);

            if (StringUtils.isEmpty(response)) {
                return null;
            }

            return entityJsonMarshaller.unmarshal(response, resultType);
        } catch (RestServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new RestServiceRequestException(e.getMessage(), e);
        }

    }

    @Override
    public <T> T delete(String scope, Class<T> entityType, String id) {
        try {
            String uri = serverUrl + new EntityUri(scope, entityType.getSimpleName(), id).toUri();

            HttpDelete httpDelete = new HttpDelete(uri);
            String response = executeRequest(httpDelete);

            if (StringUtils.isEmpty(response)) {
                return null;
            }

            return entityJsonMarshaller.unmarshal(response, entityType);
        } catch (RestServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new RestServiceRequestException(e.getMessage(), e);
        }
    }

    public <T> T operate(String scope, Class<?> entityType, String id, String operation, Object[] args, Class<T> resultType) {
        try {
            String uri = serverUrl + new EntityUri(scope, entityType.getSimpleName(), id, operation).toUri();

            String body = "";
            if (args != null) {
                String[] stringValues = new String[args.length];
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    stringValues[i] = StringValueUtil.getStringValue(arg, arg.getClass());
                }
                body = entityJsonMarshaller.marshal(stringValues);
            }

            HttpPost httpPost = new HttpPost(uri);
            StringEntity stringEntity = new StringEntity(body, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            String response = executeRequest(httpPost);

            if (StringUtils.isEmpty(response)) {
                return null;
            }

            return entityJsonMarshaller.unmarshal(response, resultType);
        } catch (RestServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new RestServiceRequestException(e.getMessage(), e);
        }
    }

    @Override
    public <T> RestServiceRequest<T> serviceRequest(String scope, Class<T> entityType) {
        return new RestServiceRequest<T>(this, scope, entityType);
    }

    protected String executeRequest(HttpUriRequest httpRequest) throws Exception {
        HttpResponse response = httpClient.execute(httpRequest);

        int code = response.getStatusLine().getStatusCode();
        Header contentType = response.getEntity().getContentType();
        String charset = contentType == null ? "UTF-8" : new MimeType(contentType.getValue()).getParameter("charset");
        String body = EntityUtils.toString(response.getEntity(), charset == null ? "UTF-8" : charset);

        if (code != 200) {
            throw new RestServiceException(body);
        } else {
            return body;
        }
    }

}