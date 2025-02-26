package com.ecommerce.notificationservice.exception;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SmsDeliveryException.class)
    public ResponseEntity<ErrorResponse> handleSmsDeliveryException(
            SmsDeliveryException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "SMS_DELIVERY_FAILED",
                "Failed to deliver SMS to " + ex.getPhoneNumber(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

}