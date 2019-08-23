package com.github.dnvriend.services;

import org.springframework.data.r2dbc.core.DatabaseClient;

public class PostgresqlService {

    private final DatabaseClient client;

    private void executeSql(String sql) {
        client.execute().sql(sql).fetch().rowsUpdated().block();
    }

    public PostgresqlService(DatabaseClient client) {
        this.client = client;
    }

    public void dropTable() {
        executeSql("DROP TABLE IF EXISTS todo");
    }

    public void createTable() {
        executeSql("CREATE TABLE todo (id serial,user_id integer,title varchar(255),completed BOOLEAN);");
    }
}
