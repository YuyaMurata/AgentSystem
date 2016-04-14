/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kaeru
 */
public class MapTest {
    public static void main(String[] args) {
        Map m1 = new HashMap();
        Map m2 = new HashMap();
        Map mt = new HashMap();
        
        m1.put("A1", 1);
        m1.put("A2", 2);
        
        m2.put("B1", 3);
        m2.put("B2", 4);
        
        mt.putAll(m1);
        mt.putAll(m2);
        
        System.out.println("MT:"+mt);
        
    }
}
