package com.dev.issuebook.dto;


public record ErrorResponse(
        String timeStamp,
        int status,
        String error,
        String message
) {

}

