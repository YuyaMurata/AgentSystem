package rda.main;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.agent.CreateAgent;
import rda.data.SetDataType;
import rda.log.AgentLogSchedule;
import rda.log.AgentSystemLogger;
import rda.property.SetProperty;
import rda.queue.MessageQueueTimer;
import rda.window.WindowController;

public class Main implements SetProperty, SetDataType{
    private static final ScheduledExecutorService mainTask = Executors.newSingleThreadScheduledExecutor();
    private static final ScheduledExecutorService endTask = Executors.newSingleThreadScheduledExecutor();
    private static final ScheduledExecutorService loggingTask = Executors.newSingleThreadScheduledExecutor();
    private static MainSchedule task;
    
    private static final Marker mainMarker = MarkerFactory.getMarker("AgentSystem Main");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    private static void init(){
        //Time
        initStart = System.currentTimeMillis();
        
        //Start System Out
        init_debug();

        // MQ Window Start 
        task = new MainSchedule(
                new WindowController(NUMBER_OF_QUEUE , WINDOW_SIZE, "DataWindow"),
                TIME_PERIOD ); 
    }

    private static void createUser(int numOfAgents){
        //TIme
        createStart = System.currentTimeMillis();
        
        CreateAgent agent = new CreateAgent();
        agent.create("U#00", numOfAgents);
    }

    private static Long start, stop, initStart, createStart;
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
        final ScheduledFuture mainTaskFuture = mainTask.scheduleAtFixedRate
                (task, 0, TIME_PERIOD, TimeUnit.MILLISECONDS);
        
        //Start Agen Logging Schedule
        loggingTask.scheduleAtFixedRate
                (new AgentLogSchedule(), 1000, TIME_PERIOD, TimeUnit.MILLISECONDS);
        
        //Stop Main Schedule
        ScheduledFuture future = endTask.schedule(
            new Runnable(){
                @Override
                public void run(){
                    mainTaskFuture.cancel(true);
                    
                    logger.print(mainMarker, "Main Task is Cancelled !", null);
                    
                    task.isFinish();
                }
            }, TIME_RUN, TimeUnit.SECONDS);
        
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
        } finally {
            MessageQueueTimer.getInstance().close();
            
            mainTask.shutdownNow();
            loggingTask.shutdownNow();
            endTask.shutdownNow();
            
            // Stop Time
            stop = System.currentTimeMillis();
            
            stop_debug();
        }
    }
    
    // DEBUG SYSTEM OUT
    private static final Marker initMarker = MarkerFactory.getMarker("init");
    private static void init_debug(){
        logger.printAgentSystemSettings(initMarker,
                "ExecTime_{} [sec] DataPeriod_{} [ms] DataType_{}",
                new Object[]{TIME_RUN, TIME_PERIOD, DATA_TYPE.toString()});
        logger.printAgentSystemSettings(initMarker, 
                "Server: N_{} Host_{}", 
                new Object[]{NUMBER_OF_SERVER, HOST_ADDRESS.toString()});
        logger.printAgentSystemSettings(initMarker, 
                "UserAgentN_{} Wait[ms]: Agent_{}", 
                new Object[]{NUMBER_OF_USER_AGENTS, AGENT_WAIT});      
        logger.printAgentSystemSettings(initMarker, 
                "MsgQueueN_{} MaxMQLength_{} WindowSize_{} Wait[ms]: Queue_{} ", 
                new Object[]{NUMBER_OF_QUEUE, QUEUE_LENGTH, WINDOW_SIZE, QUEUE_WAIT});
    }
    
    private static final Marker startMarker = MarkerFactory.getMarker("start");
    private static final Marker fieldMarker = MarkerFactory.getMarker("field");
    private static void start_debug(){
        logger.printAgentSystemSettings(startMarker, "Start Agent System", null);
        
        logger.printMQEvent(fieldMarker, "MQ LimitEvent, MQName, EventMessage", null);
    }
    
    private static final Marker stopMarker = MarkerFactory.getMarker("stop");
    private static final Marker dataMarker = MarkerFactory.getMarker("data");
    private static final Marker endMarker = MarkerFactory.getMarker("end");
    private static void stop_debug(){
        logger.printAgentSystemSettings(stopMarker, "Stop Agent System", null);       
        logger.printAgentSystemSettings(initMarker, 
                "<ALL> TransactionTime:_{} [ms]", 
                new Object[]{stop - initStart});
        logger.printAgentSystemSettings(endMarker, 
                "(<Initialize> _{} [ms] <CreateAgent> _{} [ms] <MainExec> _{}[ms])", 
                new Object[]{createStart - initStart, start - createStart, stop - start});
        
        logger.printResults(dataMarker, "Time,{}", new Object[]{stop - start});
    }
}