package com.github.dnvriend.repository;

public class ThirdKey {
    public static String SELECT_ALL = "select id_k1, id_k2, id_k3 from triple";

    public static ThirdKey fromArray(Object[] that) {
        return new ThirdKey((String) that[2]);
    }

    private String k3;

    public ThirdKey() {}

    public ThirdKey(String k3) {
        this.k3 = k3;
    }

    public String getK3() {
        return k3;
    }

    public void setK3(String k3) {
        this.k3 = k3;
    }
}
