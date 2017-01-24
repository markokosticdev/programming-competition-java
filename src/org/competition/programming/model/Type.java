package org.competition.programming.model;

public enum Type {

    ADMINISTRATOR(1),
    COMMISSION(2),
    COMPETITOR(3);

    private final int typeCode;

    Type(int typeCode) {
        this.typeCode = typeCode;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public static int getTypeCode(Type type) {
        return type.typeCode;
    }

    public static Type getType(int typeCode) {
        switch (typeCode) {

            case 1:
                return ADMINISTRATOR;
            case 2:
                return COMMISSION;
            case 3:
                return COMPETITOR;
        }
        return null;
    }


}