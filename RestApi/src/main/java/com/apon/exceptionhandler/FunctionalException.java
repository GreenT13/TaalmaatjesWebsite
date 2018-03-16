package com.apon.exceptionhandler;

public class FunctionalException extends Exception {
    private String messageResource;
    private Object[] messageArguments;

    public FunctionalException(String messageResource, Object... messageArguments) {
        this.messageResource = messageResource;
        this.messageArguments = messageArguments;
    }

    public String getMessageResource() {
        return messageResource;
    }

    public Object[] getMessageArguments() {
        return messageArguments;
    }
}
