package rda.main;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.agent.CreateAgent;
import rda.property.SetProperty;
import rda.queue.MessageQueueTimer;
import rda.queue.WindowController;

public class Main implements SetProperty{
    private static final ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
    private static MainSchedule mainTask;
    private static final Marker mainMarker = MarkerFactory.getMarker("AgentSystem Main");
    
    private static void init(){
        //Start System Out
        init_debug();
        logger.info(mainMarker, "Time_{}[sec] Message Period_{}[ms] UserAgent N_{} DataType_{} Amount Data N_{}", 
                    TIME_RUN, TIME_PERIOD, NUMBER_OF_USER_AGENTS, DATA_TYPE.name, DATA_TYPE.getAmountData().toString());
        logger.info(mainMarker, "MsgQueue N_{} Max MQ Length_{} Window Size_{} Wait[ms]: Agent_{} Queue_{}", 
                    NUMBER_OF_QUEUE, QUEUE_LENGTH, WINDOW_SIZE, AGENT_WAIT, QUEUE_WAIT);
        logger.info(mainMarker, "Server: N_{} host_{}",NUMBER_OF_SERVER, HOST_ADDRESS);
        
        System.out.println("DATA_N"+DATA_TYPE.getAmountData().toString()+" Server Host "+HOST_ADDRESS);
        
        // MQ Window Start
        mainTask = new MainSchedule(new WindowController(NUMBER_OF_QUEUE ,String.valueOf("Win_Main")));
    }

    private static void createUser(int numOfAgents){
        CreateAgent agent = new CreateAgent();
        agent.create("U#00", numOfAgents);
    }

    private static Long start, stop, transaction;
    public static void main(String[] args) {
        // Start Time
        start = System.currentTimeMillis();
        
        //initialize
        init();

        //Agentの生成
        createUser(NUMBER_OF_USER_AGENTS);

        //Execute Agent System
        execute();
    }

    private static void execute(){
        start_debug();
        logger.info(mainMarker, "Start Agent System : {}[ms]", start);
        
        //Start Main Schedule
        final ScheduledFuture mainTaskFuture = ex.scheduleAtFixedRate
                (mainTask, 0, TIME_PERIOD, TimeUnit.MILLISECONDS);
        
        //Stop Main Schedule
        ScheduledFuture future = ex.schedule(
            new Runnable(){
                public void run(){
                    mainTaskFuture.cancel(true);
                    
                    logger.info("Main Task is Cancelled !");
                    
                    mainTask.close();
                }
            }, TIME_RUN, TimeUnit.SECONDS);
        
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
        } finally {
            ex.shutdownNow();
            MessageQueueTimer.getInstance().close();
            
            // Stop Time
            stop = System.currentTimeMillis();
            
            stop_debug();
            logger.info(mainMarker, "Stop Agent System : {}[ms]", stop);
            logger.info(mainMarker, 
                    "<Initialise - Thread Shutdown> Transaction Time : {}[ms]", stop-start);
        }
    }
    
    // DEBUG SYSTEM OUT
    private static void init_debug(){
        logger.debug(mainMarker, "Time_{}[sec] MessagePeriod_{}[ms] UserAgentN_{} DataType_{} DataN_{}", 
                    TIME_RUN, TIME_PERIOD, NUMBER_OF_USER_AGENTS, DATA_TYPE.name, DATA_TYPE.getAmountData());
        logger.debug(mainMarker, "MsgQueueN_{} MaxMQLength_{} WindowSize_{} Wait[ms]: Agent_{} Queue_{}", 
                    NUMBER_OF_QUEUE, QUEUE_LENGTH, WINDOW_SIZE, AGENT_WAIT, QUEUE_WAIT);
        logger.debug(mainMarker, "Server: N_{} Host_{}",NUMBER_OF_SERVER, HOST_ADDRESS.toString());
        
        logger.trace(mainMarker, "Time_{}[sec] MessagePeriod_{}[ms] UserAgentN_{} DataType_{} DataN_{}", 
                    TIME_RUN, TIME_PERIOD, NUMBER_OF_USER_AGENTS, DATA_TYPE.name, DATA_TYPE.getAmountData());
        logger.trace(mainMarker, "MsgQueueN_{} MaxMQLength_{} WindowSize_{} Wait[ms]: Agent_{} Queue_{}", 
                    NUMBER_OF_QUEUE, QUEUE_LENGTH, WINDOW_SIZE, AGENT_WAIT, QUEUE_WAIT);
        logger.trace(mainMarker, "Server: N_{} Host_{}",NUMBER_OF_SERVER, HOST_ADDRESS.toString());
    }
    
    private static void start_debug(){
        logger.debug(mainMarker, "StartAgentSystem: {} [ms]", start);
        
        ArrayList<String> str = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < NUMBER_OF_QUEUE; i++){
            str.add("RMQ"+i);
            sb.append("{} ");
        }
        logger.debug(mainMarker, sb.toString(), str.toArray());
        
        logger.trace(mainMarker, "StartAgentSystem: {} [ms]", start);
    }
    
    private static void stop_debug(){
        logger.debug(mainMarker, "StopAgentSystem: {} [ms]", stop);
        transaction = stop - start;
        logger.debug(mainMarker, 
                    "<Initialise-ThreadShutdown>TransactionTime: {} [ms]", transaction);
        
        logger.trace(mainMarker, "StopAgentSystem: {} [ms]", stop);
        logger.trace(mainMarker, 
                    "<Initialise-ThreadShutdown>TransactionTime: {} [ms]", stop-start);
    }
}