/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.data;

import java.util.ArrayList;
import org.apache.commons.math3.random.RandomDataGenerator;
import rda.queue.obj.MessageObject;

/**
 *
 * @author kaeru
 */
public class Data{
    private static int count = -1;
    private static final RandomDataGenerator rand = new RandomDataGenerator();
    private int numOfUser, value, mode;

    public Data() {}

    //Set All UserID
    private ArrayList<String> userList = new ArrayList<>();
    public void init(int n, int value, int mode){
        this.numOfUser = n;
        this.value = value;
        this.mode = mode;
        
        for(int i=0; i < numOfUser; i++){
            String userID = "U#00"+ i;
            userList.add(userID);
        }
    }
        
    private Integer idNo(){
        switch(mode){
            case 0 : return idSequentialNo();
            case 1 : return idRandomNo();
        }
        return null;
    }
    
    private Integer idSequentialNo(){
        count++;
        if(count == numOfUser) count = 0;
        return count;
    }
    
    private Integer idRandomNo(){
        return rand.nextInt(0, numOfUser-1);
    }

    //Get Data userID = Call % NUMBER_USER_AGENTS
    public MessageObject getData(){
        return new MessageObject(userList.get(idNo()), value);
    }
    
    public MessageObject getPoison(){
        return new MessageObject(userList.get(idNo()), -1);
    }
    
}