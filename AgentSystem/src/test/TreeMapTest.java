/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Random;
import java.util.TreeMap;

/**
 *
 * @author kaeru
 */
public class TreeMapTest {
    public static void main(String[] args) {
        TreeMap map = new TreeMap();
        String[] sa = new String[]{"R#001","R#004","R#003","R#002","R#000"};
        
        Random rand = new Random();
        
        for(String s : sa){
            int id = rand.nextInt();
            System.out.println("ID="+id);
            map.put(id, s);
        }
        
        //for(Object key : map.keySet())
            System.out.println("id="+map.firstEntry());
    }
}
