/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.unit;

/**
 *
 * @author kaeru
 */
public class SBTest {
    public static void main(String[] args) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb1.append("a");sb1.append("b");sb1.append("c");
        sb2.append("d");sb2.append("e");sb2.append("f");
        
        sb1.deleteCharAt(sb1.length()-1);
        sb1.append(sb2);
        
        System.out.println(sb1.toString());
    }
}
