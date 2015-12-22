/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.data;

import java.util.ArrayList;
import org.apache.commons.math3.random.RandomDataGenerator;
import rda.data.profile.ProfileGenerator;
import rda.queue.obj.MessageObject;

/**
 *
 * @author kaeru
 */
public class Data{
    private static int count = -1;
    private static final RandomDataGenerator rand = new RandomDataGenerator();
    private int numOfUser, value, mode;
    
    private ProfileGenerator prof = ProfileGenerator.getInstance();

    public Data() {}

    //Set All UserID
    public void init(int n, int value, int mode){
        this.numOfUser = n;
        this.value = value;
        this.mode = mode;
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
        return new MessageObject(prof.getUser(idNo()), value);
    }
    
    public MessageObject getPoison(){
        return new MessageObject(prof.getUser(idNo()), -1);
    }
    
}