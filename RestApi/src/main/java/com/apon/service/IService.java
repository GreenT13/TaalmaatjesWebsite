package com.apon.service;

import com.apon.database.jooq.DbContext;

public interface IService {
    DbContext getContext();
    void setContext(DbContext dbContext);
}
