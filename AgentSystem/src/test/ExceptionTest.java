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
public class ExceptionTest {
    public static void main(String[] args) {
        try{
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                System.out.println("Internal Catch!");
                throw new IllegalStateException();
            }
        } catch(Exception e){
            System.out.println("External Catch!");
        }
    }
}