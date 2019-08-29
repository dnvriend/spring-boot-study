package com.github.dnvriend;

public class FirstKey {
    public static String SELECT_ALL = "select id_k1, id_k2, id_k3 from triple";

    public static FirstKey fromArray(Object[] that) {
        return new FirstKey((String) that[0]);
    }

    private String k1;

    public FirstKey() {}

    public FirstKey(String k1) {
        this.k1 = k1;
    }

    public String getK1() {
        return k1;
    }

    public void setK1(String k1) {
        this.k1 = k1;
    }
}
