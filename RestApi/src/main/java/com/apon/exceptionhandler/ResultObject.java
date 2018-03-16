package com.apon.exceptionhandler;

public class ResultObject {
    private String messageCode;
    private String[] messageArguments;
    private Exception e;

    public ResultObject(String messageCode, Exception e, String... messageArguments) {
        this.messageCode = messageCode;
        this.e = e;
        this.messageArguments = messageArguments;
    }

    public ResultObject(String messageCode, String... messageArguments) {
        this.messageCode = messageCode;
        this.messageArguments = messageArguments;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public Exception getE() {
        return e;
    }

    public String[] getMessageArguments() {
        return messageArguments;
    }
}
