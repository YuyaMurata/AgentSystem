package rda.main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import rda.agent.CreateAgent;
import rda.data.MountData;
import rda.property.SetPropertry;
import rda.queue.MessageObject;
import rda.queue.WindowController;

public class Main extends SetPropertry{
    private static final ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
    private static MainSchedule mainTask;
        
    private static void init(){
        //File
        out.write("MessageQueue_Event Time: Agent_"+NUMBER_OF_USER_AGENTS+", Run_"+TIME_RUN);
        out.write("MessageQueue Property: Window Size_"+WINDOW_SIZE+", MQ Length_"+QUEUE_LENGTH+", wait_"+QUEUE_WAIT);

        mainTask = new MainSchedule(new WindowController(NUMBER_OF_QUEUE ,String.valueOf("Win_Main")));
    }

    private static void createUser(int numOfAgents){
        CreateAgent agent = new CreateAgent();
        agent.create("U#00", numOfAgents);
    }

    public static void main(String[] args) {
        //initialize
        init();

        //Agentの生成
        createUser(NUMBER_OF_USER_AGENTS);

        //Execute Agent System
        long start = System.currentTimeMillis();
        System.out.println("Start Agent System : "+start);

        execute();

        long stop = System.currentTimeMillis();
        System.out.println("Stop Agent System : "+stop);

        // System Log
        System.out.println("transaction_time : "+(stop-start));
        out.write("start_time:"+start+",stop_time:"+stop+","+(stop-start)+",[ms]");

        out.close();
    }

    private static void execute(){
        //Start Main Schedule
        final ScheduledFuture future = ex.scheduleAtFixedRate
                (mainTask, 0, TIME_PERIOD, TimeUnit.MILLISECONDS);
        
        //Stop Main Schedule
        ex.schedule(new Runnable(){
                public void run(){
                    future.cancel(true);
                    mainTask.close();
                }
            }, TIME_RUN, TimeUnit.SECONDS);
    }
}