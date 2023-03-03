package com.wg.boundary.rest.exceptionMappers;

import com.fasterxml.jackson.core.JsonParseException;
import io.vertx.core.json.JsonObject;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import javax.json.bind.JsonbException;
import javax.ws.rs.NotFoundException;

/**
 * @author majaesch
 *
 * */
public class ExceptionMappers {
    @ServerExceptionMapper
    public RestResponse<String> jsonExceptionHandler(JsonbException e){
        JsonObject json = new JsonObject();
        json.put("message", "JsonParseException " + e.getMessage());

        return RestResponse.status(RestResponse.Status.CONFLICT, json.toString());
    }
    @ServerExceptionMapper
    public RestResponse<String> constraintHandler(CustomConstraintViolationException e){
        JsonObject json = new JsonObject();
        json.put("message", "constraint violated " + e.getMessage());
        return RestResponse.status(RestResponse.Status.CONFLICT, json.toString());
    }
    @ServerExceptionMapper
    public RestResponse<String> persistenceHandler(CustomPersistenceException e){
        JsonObject json = new JsonObject();
        json.put("message", "persistanceException: " + e.getMessage());
        return RestResponse.status(RestResponse.Status.CONFLICT, json.toString());
    }
    @ServerExceptionMapper
    public RestResponse<String> mapException(NotFoundException e){
        JsonObject json = new JsonObject();
        json.put("message", "Not found" + e.getMessage());
        return RestResponse.status(RestResponse.Status.NOT_FOUND, json.toString());
    }

}
