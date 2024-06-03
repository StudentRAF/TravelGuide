package rs.raf.student.sql;

import java.sql.Types;

public class PostgresType extends SQLType {

    public static final SQLType BIGINT      = of(Types.BIGINT,               "bigint"     );
    public static final SQLType BOOL        = of(BOOLEAN.getCode(),          "bool"       );
    public static final SQLType VAR_BIT     = of(BIT_VARYING.getCode(),      "varbit"     );
    public static final SQLType INT8        = of(BIGINT.getCode(),           "int8"       );
    public static final SQLType BIG_SERIAL  = of(UNKNOWN_CODE,               "bigserial"  );
    public static final SQLType SERIAL8     = of(UNKNOWN_CODE,               "serial8"    );
    public static final SQLType BOX         = of(UNKNOWN_CODE,               "box"        );
    public static final SQLType BYTEA       = of(UNKNOWN_CODE,               "bytea"      );
    public static final SQLType CIDR        = of(UNKNOWN_CODE,               "cidr"       );
    public static final SQLType CIRCLE      = of(UNKNOWN_CODE,               "circle"     );
    public static final SQLType FLOAT8      = of(DOUBLE_PRECISION.getCode(), "float8"     );
    public static final SQLType INT4        = of(INTEGER.getCode(),          "int4"       );
    public static final SQLType JSON        = of(UNKNOWN_CODE,               "json"       );
    public static final SQLType JSONB       = of(UNKNOWN_CODE,               "jsonb"      );
    public static final SQLType LINE        = of(UNKNOWN_CODE,               "line"       );
    public static final SQLType LSEG        = of(UNKNOWN_CODE,               "lseg"       );
    public static final SQLType MAC_ADDR    = of(UNKNOWN_CODE,               "macaddr"    );
    public static final SQLType MAC_ADDR8   = of(UNKNOWN_CODE,               "macaddr8"   );
    public static final SQLType MONEY       = of(UNKNOWN_CODE,               "money"      );
    public static final SQLType PATH        = of(UNKNOWN_CODE,               "path"       );
    public static final SQLType PG_LSN      = of(UNKNOWN_CODE,               "pg_lsn"     );
    public static final SQLType PG_SNAPSHOT = of(UNKNOWN_CODE,               "pg_snapshot");
    public static final SQLType POINT       = of(UNKNOWN_CODE,               "point"      );
    public static final SQLType POLYGON     = of(UNKNOWN_CODE,               "polygon"    );
    public static final SQLType FLOAT4      = of(REAL.getCode(),             "float4"     );
    public static final SQLType INT2        = of(SMALLINT.getCode(),         "int2"       );
    public static final SQLType SMALL_SERIAL= of(UNKNOWN_CODE,               "smallserial");
    public static final SQLType SERIAL2     = of(SMALL_SERIAL.getCode(),     "serial2"    );
    public static final SQLType SERIAL      = of(UNKNOWN_CODE,               "serial"     );
    public static final SQLType SERIAL4     = of(SERIAL.getCode(),           "serial4"    );
    public static final SQLType TEXT        = of(UNKNOWN_CODE,               "text"       );
    public static final SQLType TS_QUERY    = of(UNKNOWN_CODE,               "tsquery"    );
    public static final SQLType TS_VECTOR   = of(UNKNOWN_CODE,               "tsvector"   );
    public static final SQLType UUID        = of(UNKNOWN_CODE,               "uuid"       );
    public static final SQLType XML         = of(UNKNOWN_CODE,               "xml"        );

}
