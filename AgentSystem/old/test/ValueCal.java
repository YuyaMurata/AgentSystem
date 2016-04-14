/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 悠也
 */
public class ValueCal {
    private String name;
    private Integer v;
    
    public ValueCal(String name, Integer value){
        this.name = name;
        this.v = value;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Integer getValue(){
        return this.v;
    }
    
    public void plus(Integer p){
        this.v = p;
    }
}