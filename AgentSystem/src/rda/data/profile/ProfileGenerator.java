package rda.data.profile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.math3.random.RandomDataGenerator;
import rda.manager.AgentMessageQueueManager;

public class ProfileGenerator {
    private static final ProfileGenerator profgen; 
    private static final RandomDataGenerator rand = new RandomDataGenerator();
    private static Integer mu, sigma, mode;
        
    static {
        profgen = new ProfileGenerator();
        mode = 0;
        mu = 100/2;
        sigma = 100/10;
    }
	
    public static ProfileGenerator getInstance(){
        return profgen;
    }
    
    public void initProfile(Integer numberOfUser, Integer mode, Long seed){
        //Random Parameter
        rand.reSeed(Integer.MAX_VALUE);
        mu = 100/2;
        sigma = 100/10;
        
        generate(numberOfUser, mode);
    }
    
    private HashMap<String, HashMap> profMap = new HashMap<>();
    private List<String> userList = new ArrayList<>();
    private void generate(Integer n, Integer mode){
        StringBuilder digit = new StringBuilder();
        for(int i=0; i < n.toString().length(); i++)
            digit.append("0");
        
        DecimalFormat dformat= new DecimalFormat(digit.toString());
        
        //Data Profile Mode (Topic Balance)
        this.mode = mode;
        
        for(int i=0; i < n; i++){
            String uid = "usid-"+dformat.format(i);
            HashMap prof = genUserProfile(uid);
            profMap.put(uid, prof);
            userList.add(uid);
        }
    }
    
    public HashMap getProf(String uid){
        //UserのProfileを生成
        return profMap.get(uid);
    }
    
    public String getUser(int index){
        return userList.get(index);
    }
    
    private Integer getGaussAge(){
        Integer age = (int) rand.nextGaussian(mu, sigma);
        
        if((age > 100) || (age < 0)) 
            age = rand.nextInt(0, 100);
        return age;
    }
        
    private Integer getFlatAge(){
        Integer age = (int) rand.nextInt(1, 100);
        return age;
    }
    
    private HashMap genUserProfile(String id) {
        //Store Profile
        HashMap prof = new HashMap();
	
        //ID
        prof.put("UserID", id);
        
        //Name
        prof.put("Name", "Name-" + id);
        
        //Sex
        if(rand.nextInt(0, 1) == 0) prof.put("Sex", "M");  
        else prof.put("Sex", "F");
        
        //Age
        if(mode == 0)prof.put("Age", getFlatAge());
        else prof.put("Age", getGaussAge());
        
        //Address
        prof.put("Address", "Address-" + id);
        
        //TargetID
        prof.put("TargetID", 
        AgentMessageQueueManager.getInstance().getIDManager().ageToID((Integer)prof.get("Age")));
       
        return prof;
    }
}