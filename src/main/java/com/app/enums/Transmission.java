package com.app.enums;

public enum Transmission {
    MANUAL(1),AUTOMATIC(2);
    private int tran;

    Transmission(int tran){
        this.tran = tran;
    }
    public int getTran(){
        return tran;
    }
}
