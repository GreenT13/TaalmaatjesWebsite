package com.apon.database.mydao;

import com.apon.database.generated.tables.daos.LogonuserDao;
import com.apon.database.jooq.DbContext;

public class LogonUserMyDao extends LogonuserDao {

    public LogonUserMyDao(DbContext context) {
        super(context.getConfiguration());
    }
}
