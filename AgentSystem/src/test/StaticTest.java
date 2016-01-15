/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author kaeru
 */
public class StaticTest {
    public static void main(String[] args) {
        StaticObject.test1 = 10;
        StaticObject obj1 = new StaticObject();
        StaticObject obj2 = new StaticObject();
        obj1.print();
        obj1.addTest();
        obj1.print();
        obj2.print();
        System.out.println("StaticOBJ1="+StaticObject.test1);
        System.out.println("NonStaticOBJ1="+obj1.test2);
        System.out.println("StaticOBJ2="+StaticObject.test1);
        System.out.println("NonStaticOBJ2="+obj1.test2);
    }
}
