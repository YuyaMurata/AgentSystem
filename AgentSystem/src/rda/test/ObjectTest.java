/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test;

import java.util.ArrayList;
import java.util.HashMap;
import rda.agent.user.ProfileGenerator;

/**
 *
 * @author 悠也
 */
public class ObjectTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        
        HashMap<String, String> prof;
        ProfileGenerator profg = ProfileGenerator.getInstance();
        for(int i=0; i < 10000; i++){
            
            prof = profg.getProf("U#00"+i);
        }
        
        long stop = System.currentTimeMillis();
        
        System.out.println("time:"+(stop-start)+" [ms]");
    }
}
