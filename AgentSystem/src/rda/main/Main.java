package rda.main;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.agent.CreateAgent;
import rda.log.AgentSystemLogger;
import rda.property.SetProperty;
import rda.queue.MessageQueueTimer;
import rda.queue.WindowController;

public class Main implements SetProperty{
    private static final ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
    private static MainSchedule mainTask;
    
    private static final Marker mainMarker = MarkerFactory.getMarker("AgentSystem Main");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    private static void init(){
        //Start System Out
        init_debug();

        // MQ Window Start
        mainTask = new MainSchedule(new WindowController(NUMBER_OF_QUEUE , WINDOW_SIZE, "DataWindow"));
    }

    private static void createUser(int numOfAgents){
        CreateAgent agent = new CreateAgent();
        agent.create("U#00", numOfAgents);
    }

    private static Long start, stop, transaction;
    public static void main(String[] args) {
        //initialize
        init();

        //Agentの生成
        createUser(NUMBER_OF_USER_AGENTS);

        //Execute Agent System
        execute();
    }

    private static void execute(){
        start_debug();
        
        // Start Time
        start = System.currentTimeMillis();
        
        //Start Main Schedule
        final ScheduledFuture mainTaskFuture = ex.scheduleAtFixedRate
                (mainTask, 0, TIME_PERIOD, TimeUnit.MILLISECONDS);
        
        //Stop Main Schedule
        ScheduledFuture future = ex.schedule(
            new Runnable(){
                public void run(){
                    mainTaskFuture.cancel(true);
                    
                    logger.print(mainMarker, "Main Task is Cancelled !", null);
                    
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
        }
    }
    
    // DEBUG SYSTEM OUT
    private static void init_debug(){
        logger.printAgentSystemSettings(mainMarker,
                    "ExecTime_{}[sec] MessagePeriod_{}[ms] UserAgentN_{} DataType_{} DataN_{} \n MsgQueueN_{} MaxMQLength_{} WindowSize_{} Wait[ms]: Agent_{} Queue_{} \n Server: N_{} Host_{}",
                    new Object[]{TIME_RUN, TIME_PERIOD, NUMBER_OF_USER_AGENTS, DATA_TYPE.name, DATA_TYPE.getAmountData(), NUMBER_OF_QUEUE, QUEUE_LENGTH, WINDOW_SIZE, AGENT_WAIT, QUEUE_WAIT, NUMBER_OF_SERVER, HOST_ADDRESS.toString()});
    }
    
    private static void start_debug(){
        logger.printAgentSystemSettings(mainMarker, "Start Agent System", new Object[0]);
        
        Object[] obj = new Object[NUMBER_OF_QUEUE];
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < NUMBER_OF_QUEUE; i++){
            obj[i] = "RMQ"+i;
            sb.append("{} ");
        }
        logger.printMQLFile(mainMarker, sb.toString(), obj);
    }
    
    private static void stop_debug(){
        logger.printAgentSystemSettings(mainMarker, "Stop AgentSystem", null);
        
        transaction = stop - start;
        logger.printAgentSystemSettings(mainMarker, 
                    "<Initialise-ThreadShutdown>TransactionTime: {} [ms]", 
                    new Object[]{transaction});
    }
}