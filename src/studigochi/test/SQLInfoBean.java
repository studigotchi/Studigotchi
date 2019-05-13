package studigochi.test;

import org.jetbrains.annotations.Contract;

import java.sql.Connection;

public class SQLInfoBean {
    private final Connection connection;

    @Contract(pure = true)
    public SQLInfoBean(Connection connection) {
        this.connection = connection;
    }

}
