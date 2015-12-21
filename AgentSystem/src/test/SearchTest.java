/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.HashMap;

/**
 *
 * @author kaeru
 */
public class SearchTest {
    public static HashMap<String, String> familyMap = new HashMap();
    public static void main(String[] args) {
        familyMap.put("R#00", "R#00");
        familyMap.put("R#01", "R#00");
        familyMap.put("R#02", "R#01");
        familyMap.put("R#03", "R#01");
        familyMap.put("R#11", "R#11");
        familyMap.put("R#10", "R#11");
        familyMap.put("R#04", "R#11");
        
        System.out.println("Search Parent:"+searchParent("R#03"));
    }
    
    private static String searchParent(String pid){
            if(pid == familyMap.get(pid)){
                return pid;
            }else{
                return searchParent(familyMap.get(pid));
            }
        }
}
