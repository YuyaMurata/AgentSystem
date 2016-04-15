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
        
        System.out.println(">"+toString());
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        StringBuilder sbval = new StringBuilder();
        Long total= 0L;
        for(Object id : results.keySet()){
            sb.append(id);
            sb.append(",");
            
            Long value = (Long)results.get(id);
            sbval.append(value);
            sbval.append(",");
            
            total = total + value;
        }
        
        sb.append("Total");
        sbval.append(total);
        
        sb.append("\n");
        sb.append(sbval);
        
        return sb.toString();
    }
}
