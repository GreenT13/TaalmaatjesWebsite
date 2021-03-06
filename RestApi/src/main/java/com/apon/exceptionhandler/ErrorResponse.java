package com.apon.exceptionhandler;

@SuppressWarnings("unused")
public class ErrorResponse {
    private String title;
    private String detail;

    public ErrorResponse() { }

    ErrorResponse(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
