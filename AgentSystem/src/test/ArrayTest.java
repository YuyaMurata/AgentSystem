/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author 悠也
 */
public class ArrayTest {
    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.put("TEST1", "TESTTEST");
        
        ArrayList list = new ArrayList();
        
        list.add(map);
        list.add(map.clone());
        
        System.out.println("map:"+list);
        
        map.put("TEST1", "NONE");
        
        System.out.println("map:"+list);
    }
}
