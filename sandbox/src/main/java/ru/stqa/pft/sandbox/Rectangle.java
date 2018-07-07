package ru.stqa.pft.sandbox;

public class Rectangle {
    public Rectangle(double a, double b) {
        this.a = a;
        this.b = b;
    }
    private double a;
    private double b;

    double area () {
        return a * b;
    }

}
