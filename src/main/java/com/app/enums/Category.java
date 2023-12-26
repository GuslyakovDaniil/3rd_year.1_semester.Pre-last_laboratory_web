package com.app.enums;

public enum Category {
    CAR(1),BUSS(2),TRUCK(3),MOTORCYCLE(4);

    private int cars;

    Category(int cars){
        this.cars = cars;
    }
    public int getCars(){
        return cars;
    }
}
