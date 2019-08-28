package com.github.dnvriend;

public class SecondKey {
    public static String SELECT_ALL = "select id_k1, id_k2 from triple";

    public static SecondKey fromArray(Object[] that) {
        return new SecondKey((String) that[1]);
    }

    private String k1;

    public SecondKey() {}

    public SecondKey(String k1) {
        this.k1 = k1;
    }

    public String getK1() {
        return k1;
    }

    public void setK1(String k1) {
        this.k1 = k1;
    }
}
