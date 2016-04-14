/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author kaeru
 */
public class CollectionTest {
    static Collection testcol;
    
    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.put("T1", "test1");
        
        testcol = map.values();
        
        map.put("T2", "test2");
        map.put("T3", "test3");
        
        System.out.println(testcol);
    }
}
