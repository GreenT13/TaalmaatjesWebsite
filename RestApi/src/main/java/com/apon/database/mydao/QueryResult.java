package com.apon.database.mydao;

public class QueryResult<S,T> {
    private final S s;
    private final T t;

    QueryResult(S s, T t) {
        this.s = s;
        this.t = t;
    }

    public S getS() {
        return s;
    }

    public T getT() {
        return t;
    }
}
