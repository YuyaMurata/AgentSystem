
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 悠也
 */
public class QueueSizeConcreate {
    
    public static void main(String[] args) {
        
        ValueCal a = new ValueCal("a", 10);
        ValueCal b = new ValueCal("b", 20);
        Integer c = 30;
        
        Map<String, Integer> m = new HashMap<String, Integer>();
        m.put("c", c);
        
        List<Object> list = new ArrayList();
        
        list.add(a);
        list.add(b);
        list.add(m);
        
        System.out.println("list: a="+a.getName()+" l="+((ValueCal)list.get(0)).getValue());
        System.out.println("list: b="+b.getName()+" l="+((ValueCal)list.get(1)).getValue());
        System.out.println("list: c="+"c"+" l="+((Map<String, Integer>)list.get(2)).get("c"));
        
        a.plus(100);
        b.plus(100);
        c += 100;
        
        System.out.println("list: a="+a.getName()+" l="+((ValueCal)list.get(0)).getValue());
        System.out.println("list: b="+b.getName()+" l="+((ValueCal)list.get(1)).getValue());
        System.out.println("list: c="+"c"+" l="+((Map<String, Integer>)list.get(2)).get("c"));
        
    }
}