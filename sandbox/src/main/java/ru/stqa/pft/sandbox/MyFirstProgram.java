package ru.stqa.pft.sandbox;

public class MyFirstProgram {
    public static void main(String args[]) {
        Rectangle r = new Rectangle(2,3);
        Square s = new Square(4);

        System.out.println(r.area());
        System.out.println(s.area());
    }
}