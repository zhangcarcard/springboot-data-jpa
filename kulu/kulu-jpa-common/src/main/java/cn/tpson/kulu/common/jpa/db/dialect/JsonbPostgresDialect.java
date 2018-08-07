package cn.tpson.kulu.common.jpa.db.dialect;

import org.hibernate.dialect.PostgreSQL95Dialect;

import java.sql.Types;

/**
 * Created by Zhangka in 2018/07/20
 */
public class JsonbPostgresDialect extends PostgreSQL95Dialect {
    public JsonbPostgresDialect() {
        registerColumnType(Types.OTHER, "jsonb");
    }
}
