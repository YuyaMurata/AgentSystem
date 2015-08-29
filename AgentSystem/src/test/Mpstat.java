package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import rda.data.OutputData;
import rda.property.SetProperty;

public class Mpstat implements Runnable, SetProperty{
	private static OutputData outcpudata;
	public Mpstat() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	private static Process proc;
	private static BufferedReader reader;
	private static String mpstatLine;
	public static String getCPULoad(){
		StringBuilder sb = new StringBuilder();
		
		try {
			proc = Runtime.getRuntime().exec("mpstat "+TIME_PERIOD/1000+" "+TIME_RUN);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return "";
		}
		
		reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		
		try {
			//Header Reject
			reader.readLine();
			reader.readLine();
			reader.readLine();
		
			sb.append("0,"+ reader.readLine().split(" ")[6]);
			
			for(int i = 1; i < TIME_RUN; i++){
				sb.append("\n"+i +","+ reader.readLine().split(" ")[6]);
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	
	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ
		/*outcpudata = new OutputData("MQ_"+NUMBER_OF_QUEUE+"_CPU_availablity_"+System.currentTimeMillis());
		outcpudata.write(getCPULoad());
		
		outcpudata.close();
            */
	}
	
	/**
	public static void main(String[] args) {
		System.out.println("Mpstat.java start");
		
		//while(true){
			System.out.println(getCPULoad());
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		//}
	}
	**/
	
}