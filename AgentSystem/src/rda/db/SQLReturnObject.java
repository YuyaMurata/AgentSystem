/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.db;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kaeru
 */
public class SQLReturnObject {
    private Map results = new HashMap();
    public void setResultSet(Map res){
        this.results.putAll(res);
    }
    
    public void print(){
        System.out.println("\n--");
        
        Long total= 0L;
        for(Object id : results.keySet()){
            System.out.println(id+"="+results.get(id));
            total = total+ (Long)results.get(id);
        }
        System.out.println("> Total : "+total);
    }
}
