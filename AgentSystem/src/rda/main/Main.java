package rda.main;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.agent.user.ProfileGenerator;
import rda.data.SetDataType;
import rda.log.AgentLogSchedule;
import rda.log.AgentSystemLogger;
import rda.property.SetProperty;
import rda.queue.id.IDToMQN;
import rda.queue.manager.MessageQueueManager;
import rda.queue.timer.MessageQueueTimer;
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
        
        //Generate User
        ProfileGenerator prof = ProfileGenerator.getInstance();
        prof.generate(NUMBER_OF_USER_AGENTS);

        // Set Window 
        task = new MainSchedule(
                new WindowController(NUMBER_OF_QUEUE , WINDOW_SIZE, "DataWindow", AGENT_WAIT),
                TIME_PERIOD); 
    }

    private static void create(int numOfAgents, int mode){
        //TIme
        createStart = System.currentTimeMillis();
        
        //Start Manager
        MessageQueueManager manager = MessageQueueManager.getInstance();
        manager.initMessageQueue(numOfAgents, mode);
        manager.startAll();
    }

    private static Long start, stop, initStart, createStart;
    public static void main(String[] args) {
        //initialize
        init();

        //Agentの生成
        create(NUMBER_OF_RANK_AGENTS, AGENT_MODE);

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
    private static void init_debug(){
        logger.printResults(logger.titleMarker,
                "ExecTime_{} [sec] DataPeriod_{} [ms] DataType_{}",
                new Object[]{TIME_RUN, TIME_PERIOD, DATA_TYPE.toString()});
        logger.printResults(logger.titleMarker, 
                "Server: N_{} Host_{}", 
                new Object[]{NUMBER_OF_SERVER, HOST_ADDRESS.toString()});
        logger.printResults(logger.titleMarker, 
                "UserN_{} AgentN_{} Wait[ms]: Agent_{}", 
                new Object[]{NUMBER_OF_USER_AGENTS, NUMBER_OF_RANK_AGENTS, AGENT_WAIT});      
        logger.printResults(logger.titleMarker, 
                "MsgQueueN_{} MaxMQLength_{} WindowSize_{} Wait[ms]: Queue_{} ", 
                new Object[]{NUMBER_OF_QUEUE, QUEUE_LENGTH, WINDOW_SIZE, QUEUE_WAIT});
    }
    
    private static void start_debug(){
        logger.print(mainMarker, "Start Agent System", null);
        
        logger.printMQEvent(logger.fieldMarker, "MQ LimitEvent, MQName, EventMessage", null);
    }

    private static void stop_debug(){
        logger.print(mainMarker, "Stop Agent System", null);       
        logger.printResults(logger.resultMarker, 
                "<ALL TransactionTime>_{} [ms]", 
                new Object[]{stop - initStart});
        logger.printResults(logger.resultMarker, 
                "(<Initialize>_{} [ms] <Create>_{} [ms] <Main>_{}[ms])", 
                new Object[]{createStart - initStart, start - createStart, stop - start});
        
        IDToMQN id = IDToMQN.getInstance();
        logger.printResults(logger.fieldMarker, "ID{}",new Object[]{id.getMQNameList()});
        logger.printResults(logger.fieldMarker, "ID{}",new Object[]{id.getAGIDList()});
        
        logger.printResults(logger.dataMarker, "Time,{}", new Object[]{stop - start});
    }
}