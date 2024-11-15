package com.example.SGT.configs;

public class ConfigVariables {
    private int typeMap, typeDirection;
    /*Formato tipo 0=Vetorial tipo 1=Satelite*/
    /*Formato tipo 0=NorthUP tipo 1=CourseUP*/
    public int getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(int typeMap) {
        this.typeMap = typeMap;
    }

    public int getTypeDirection() {
        return typeDirection;
    }

    public void setTypeDirection(int typeDirection) {
        this.typeDirection = typeDirection;
    }
}
