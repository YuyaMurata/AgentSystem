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
        ArrayList<ArrayList> list = new ArrayList<>();
        ArrayList<String> str = new ArrayList<>();
        str.add("TESTEST");
        list.add(str);
        map.put("TEST1", list);
        System.out.println("MAP"+map);
        
        Object after = map.get("TEST1");
        after = null;
        System.out.println("MAP"+map);
        System.out.println("LIST"+str);
        
        map.put("TEST1", after);
        System.out.println("MAP"+map);
        System.out.println("LIST"+str);
    }
}
