/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 *
 * @author kaeru
 */
public class IDCreateTest {
    public static void main(String[] args) {
        Integer n = 10;
        
        String digit = "";
        for(int i=0; i < n.toString().length(); i++)
            digit = digit + "0";
        
        
        DecimalFormat dformat = new DecimalFormat(digit);
        
        System.out.println("R#"+dformat.format(5));
        System.out.println("R#"+dformat.format(55));
        System.out.println("R#"+dformat.format(555));
        
        HashMap<String, String> map = new HashMap();
        System.out.println("mapsize:"+map.size());
    }
}
