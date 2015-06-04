package rda.agent.user;

import java.util.HashMap;
import java.util.Random;

public class ProfileGenerator {
	private static final ProfileGenerator profgen; 
	private static Random rand = new Random();
	
	static {
		profgen = new ProfileGenerator();
	}
	
	public static ProfileGenerator getInstance(){
		return profgen;
	}
	
	public ProfileGenerator() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	synchronized public final HashMap getProf(String id) {
		HashMap<String, String> prof = new HashMap<>();
		
		prof.put("UserID", id);
		prof.put("Name", "Name-" + id);
		if(rand.nextInt(2) == 0) prof.put("Sex", "M");  
		else prof.put("Sex", "F");
		prof.put("Age", String.valueOf(rand.nextInt(100)));
		prof.put("Address", "Address-" + id);
		
		return prof;
	}
}
