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
        
        private Integer getAge(){
            Integer age = (int) rand.nextGaussian(mu, sigma);
            if((age > 100) || (age < 0)) age = rand.nextInt(0, 100);
            return age;
        }
        
        private Integer getAgeFrat(){
            Integer age = fratRand.nextInt(101);
            return age;
        }
	
	public final HashMap getProf(String id) {
            if(user.get(id) == null){
                HashMap<String, String> prof = new HashMap<>();
		
                prof.put("UserID", id);
                prof.put("Name", "Name-" + id);
                if(rand.nextInt(0, 1) == 0) prof.put("Sex", "M");  
                else prof.put("Sex", "F");
                //prof.put("Age", getAge().toString());
                prof.put("Age", getAgeFrat().toString());
                prof.put("Address", "Address-" + id);
                
                user.put(id, prof);
            }
            
            return user.get(id);
        }
}
