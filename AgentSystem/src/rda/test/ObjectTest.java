/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test;

import java.util.ArrayList;

/**
 *
 * @author 悠也
 */
public class ObjectTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i = 0; i < 1000000; i++){
            //ArrayList<Integer> arr = new ArrayList<>();
            arr.add(i);
            arr.clear();
        }
        long stop = System.currentTimeMillis();
        
        System.out.println("time:"+(stop-start)+" [ms]");
    }
}
