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
public class StaticObject {
    public static Integer test1=0;
    public Integer test2=0;
    
    public void addTest(){
        test1++;
        test2++;
    }
    
    public void print(){
        System.out.println("Test1(Static)="+test1);
        System.out.println("Test2(Nomal)="+test2);
    }
}
