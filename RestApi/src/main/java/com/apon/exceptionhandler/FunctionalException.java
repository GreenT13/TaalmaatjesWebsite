package com.apon.exceptionhandler;

public class FunctionalException extends Exception {
    private String messageResource;
    private Object[] messageArguments;
    private ResultObject resultObject;

    public FunctionalException(String messageResource, Object... messageArguments) {
        this.messageResource = messageResource;
        this.messageArguments = messageArguments;
    }

    public FunctionalException(ResultObject resultObject) {
        this.resultObject = resultObject;
    }

    public String getMessageResource() {
        return messageResource;
    }

    public Object[] getMessageArguments() {
        return messageArguments;
    }

    public ResultObject getResultObject() {
        return resultObject;
    }
}
