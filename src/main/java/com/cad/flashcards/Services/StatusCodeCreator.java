package com.cad.flashcards.Services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StatusCodeCreator {

    public HttpStatus accountExists(){
        return HttpStatus.CONFLICT;
    }

    public HttpStatus UserNotFound(){
        return HttpStatus.NOT_FOUND;
    }

    public HttpStatus created(){
        return HttpStatus.CREATED;
    }


    public HttpStatus success(){
        return HttpStatus.ACCEPTED;
    }

    public HttpStatus badRequest(){
        return HttpStatus.BAD_REQUEST;
    }

    public HttpStatus unauthorized(){
        return HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus internalServerError() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus notAcceptable() {
        return HttpStatus.NOT_ACCEPTABLE;
    }
}
