package rda.agent.user;

import java.util.HashMap;
import java.util.Random;
import org.apache.commons.math3.random.RandomDataGenerator;

public class ProfileGenerator {
    private static final ProfileGenerator profgen; 
    private HashMap<String, HashMap> user = new HashMap();
    private static final RandomDataGenerator rand = new RandomDataGenerator();
    private static final Random fratRand = new Random();
    private Integer mu, sigma;
        
    static {
        profgen = new ProfileGenerator();
    }
	
    public static ProfileGenerator getInstance(){
        return profgen;
    }
	
    public ProfileGenerator() {
        // TODO 自動生成されたコンストラクター・スタブ
        mu = 100/2;
        sigma = 100/10;
    }
    
    private HashMap<String, HashMap> profMap = new HashMap<>();
    public void generate(Integer n){
        for(int i=0; i < n; i++){
            String uid = "U#00"+i;
            profMap.put(uid, generateProfile(uid));
        }
    }
    
    public HashMap getAGIDProf(String agID){
        return generateProfile(agID);
    }
    
    public HashMap getProf(String uid){
        return profMap.get(uid);
    }
    
    private Integer getAge(){
        Integer age = (int) rand.nextGaussian(mu, sigma);
        
        return getAgeFrat();
        /*
        if((age > 100) || (age < 0)) 
            age = rand.nextInt(0, 100);
        return age;*/
    }
        
    private Integer getAgeFrat(){
        Integer age = (int) fratRand.nextInt(101);
        return age;
    }
	
    private HashMap generateProfile(String id) {
        //Store Profile
        HashMap<String, String> prof = new HashMap<>();
		
        //ID
        prof.put("UserID", id);
        
        //Name
        prof.put("Name", "Name-" + id);
        
        //Sex
        if(rand.nextInt(0, 1) == 0) prof.put("Sex", "M");  
        else prof.put("Sex", "F");
        
        //Age
        prof.put("Age", getAge().toString());
        
        //Address
        prof.put("Address", "Address-" + id);
        
        return prof;
    }
}