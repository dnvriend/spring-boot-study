package com.github.dnvriend.repository;

public class SecondKey {
    public static String SELECT_ALL = "select id_k1, id_k2, id_k3 from triple";

    public static SecondKey fromArray(Object[] that) {
        return new SecondKey((String) that[1]);
    }

    private String k2;

    public SecondKey() {}

    public SecondKey(String k2) {
        this.k2 = k2;
    }

    public String getK2() {
        return k2;
    }

    public void setK2(String k2) {
        this.k2 = k2;
    }
}
