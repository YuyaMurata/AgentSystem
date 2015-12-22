package rda.data.profile;

import java.text.DecimalFormat;
import java.util.HashMap;
import org.apache.commons.math3.random.RandomDataGenerator;

public class ProfileGenerator {
    private static final ProfileGenerator profgen; 
    private static final RandomDataGenerator rand = new RandomDataGenerator();
    private Integer mu, sigma, mode;
        
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
    public void generate(Integer n, Integer mode){
        String digit = null;
        for(int i=0; i < n.toString().length(); i++)
            digit = digit + "0";
        
        DecimalFormat dformat= new DecimalFormat(digit);
        
        //Data Profile Mode (Topic Balance)
        this.mode = mode;
        
        for(int i=0; i < n; i++){
            String uid = "U#"+dformat.format(i);
            System.out.println("UID::"+uid);
            profMap.put(uid, generateProfile(uid));
        }
    }
    
    public HashMap getAGIDProf(String agID){
        //R#系のAgentのProfileを生成
        return generateProfile(agID);
    }
    
    public HashMap getProf(String uid){
        //U#系のProfileを生成
        return profMap.get(uid);
    }
    
    private Integer getGaussAge(){
        Integer age = (int) rand.nextGaussian(mu, sigma);
        
        if((age > 100) || (age < 0)) 
            age = rand.nextInt(0, 100);
        return age;
    }
        
    private Integer getFratAge(){
        Integer age = (int) rand.nextInt(1, 100);
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
        if(mode == 0)prof.put("Age", getFratAge().toString());
        else prof.put("Age", getGaussAge().toString());
        
        //Address
        prof.put("Address", "Address-" + id);
        
        return prof;
    }
}