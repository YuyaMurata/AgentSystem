/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kaeru
 */
public class QueueSizeTest {
    public static void main(String[] args) {
        List<Integer> size = new ArrayList();
        Integer s = size.size();
        
        size.add(1);
        size.add(2);
        
        System.out.println("xSIZE:"+s);
    }
}
