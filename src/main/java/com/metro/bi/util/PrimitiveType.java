package com.metro.bi.util;

public enum PrimitiveType {
    BYTE("byte", 1),SHORT("short", 2), INT("int", 3), LONG("long", 4),
    FLOAT("float", 7), DOUBLE("double", 8),
    BOOLEAN("boolean", 11),
    STRING("string", 13);

    private String name;
    private int index;

    private PrimitiveType(String name, int index) {
        this.name = name;
        this.index = index;
    }
    public static String getName(int index) {
        for (PrimitiveType c : PrimitiveType.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

}
