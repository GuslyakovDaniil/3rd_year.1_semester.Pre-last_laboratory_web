package com.app.enums;

public enum Engine {
    GASOLINE(1),DIESEL(2),ELECTRIC(3),HYBRID(4);

    private int eng;

    Engine(int eng){
        this.eng = eng;
    }
    public int getEng(){
        return eng;
    }
}
