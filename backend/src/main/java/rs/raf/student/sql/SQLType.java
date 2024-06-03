package rs.raf.student.sql;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Types;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SQLType {

    protected static final int    UNKNOWN_CODE = Types.OTHER;
    protected static final String UNKNOWN_NAME = "unknown";

    protected final int    code;
    protected final String name;

    public SQLType() {
        this.code = UNKNOWN_CODE;
        this.name = UNKNOWN_NAME;
    }

    public static final SQLType BOOLEAN                = of(Types.BOOLEAN,               "boolean"               );
    public static final SQLType CHARACTER              = of(Types.CHAR,                  "character"             );
    public static final SQLType CHARACTER_VARYING      = of(Types.VARCHAR,               "character varying"     );
    public static final SQLType CHAR                   = of(CHARACTER.getCode(),         "char"                  );
    public static final SQLType VARCHAR                = of(CHARACTER_VARYING.getCode(), "varchar"               );
    public static final SQLType BIT                    = of(Types.BIT,                   "bit"                   );
    public static final SQLType BIT_VARYING            = of(Types.VARBINARY,             "bit varying"           );
    public static final SQLType NUMERIC                = of(Types.NUMERIC,               "numeric"               );
    public static final SQLType INTEGER                = of(Types.INTEGER,               "integer"               );
    public static final SQLType DECIMAL                = of(Types.DECIMAL,               "decimal"               );
    public static final SQLType SMALLINT               = of(Types.SMALLINT,              "smallint"              );
    public static final SQLType INT                    = of(INTEGER.getCode(),           "int"                   );
    public static final SQLType DEC                    = of(DECIMAL.getCode(),           "dec"                   );
    public static final SQLType DOUBLE_PRECISION       = of(Types.DOUBLE,                "double precision"      );
    public static final SQLType FLOAT                  = of(Types.FLOAT,                 "float"                 );
    public static final SQLType REAL                   = of(Types.REAL,                  "real"                  );
    public static final SQLType DATE                   = of(Types.DATE,                  "date"                  );
    public static final SQLType TIME                   = of(Types.TIME,                  "time"                  );
    public static final SQLType TIMESTAMP              = of(Types.TIMESTAMP,             "timestamp"             );
    public static final SQLType INTERVAL               = of(UNKNOWN_CODE,                "interval"              );
    public static final SQLType CHARACTER_LARGE_OBJECT = of(Types.LONGVARCHAR,           "character large object");
    public static final SQLType BINARY_LARGE_OBJECT    = of(Types.LONGVARBINARY,         "binary large object"   );

    protected static SQLType of(int code, String name) {
        return new SQLType(code, name);
    }

    protected static SQLType of(SQLType type) {
        return type;
    }

}
