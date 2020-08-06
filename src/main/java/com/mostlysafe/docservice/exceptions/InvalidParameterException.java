package com.mostlysafe.docservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Parameters missing or invalid.")
public class InvalidParameterException extends DocumentServiceException {
}
