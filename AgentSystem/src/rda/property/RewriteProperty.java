/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.property;

/**
 *
 * @author kaeru
 */
public class RewriteProperty implements SetProperty{
    public static void main(String[] args){
        String number = "1";
        if(args.length == 1)
            number = args[0];
        
        System.out.println("Change Properties :");
        System.out.println("agent : number.user.agent ("+NUMBER_OF_USER_AGENTS+" -> "+number+")");
        System.out.println("queue : number.queue ("+NUMBER_OF_QUEUE+" -> "+number+")");
        
        prop.setValue("agent", "number.user.agent", number);
        prop.setValue("queue", "number.queue", number);
    }
}
