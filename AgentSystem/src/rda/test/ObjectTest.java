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
        
        long total = 0;
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i=0; i < 2 ; i++){
            for(int j=0; j < Integer.MAX_VALUE-1; j++){
                arr.add(j);
                total = total + 1;
            }
            arr.clear();
        }
        
        
        long stop = System.currentTimeMillis();
        
        System.out.println("time:"+(stop-start)+" [ms]");
        System.out.println("Total:"+total+" ,ArrLength_"+arr.size());
    }
}
