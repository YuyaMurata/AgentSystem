package rda.main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
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
    private static AgentLogSchedule task2;
    
    private static final Marker mainMarker = MarkerFactory.getMarker("AgentSystem Main");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    private static Long initStart, initStop;
    private static void init(){
        //Time
        initStart = System.currentTimeMillis();
        
        //Start System Out
        init_debug();

        // Set Window 
        task = new MainSchedule(
                new WindowController(NUMBER_OF_QUEUE , WINDOW_SIZE, "DataWindow", AGENT_WAIT),
                TIME_PERIOD);
        
        task2 = new AgentLogSchedule();
        
        initStop = System.currentTimeMillis();
    }

    private static Long createStart, createStop;
    private static void create(int numOfAgents, int mode, int reserve, int numOfReserve){
        //Time
        createStart = System.currentTimeMillis();
        
        //Start Manager
        MessageQueueManager manager = MessageQueueManager.getInstance();
        manager.initMessageQueue(numOfAgents, mode, reserve, numOfReserve);
        
        createStop = System.currentTimeMillis();
    }

    private static Long start, stop;
    public static void main(String[] args) {
        // Start Time
        start = System.currentTimeMillis();
        
        //initialize
        init();

        //Agentの生成
        create(NUMBER_OF_RANK_AGENTS, AGENT_MODE_AUTONOMY,
                AGENT_MODE_RESERVE, NUMBER_OF_RESERVE);

        //Execute Agent System
        execute();
        
        // Stop Time
        stop = System.currentTimeMillis();
        
        //Stop
        main_debug();
    }

    private static Long execStart, execStop;
    private static void execute(){
        FutureMap fMap = new FutureMap();
        
        //Start
        exec_debug();
        
        //Time
        execStart = System.currentTimeMillis();
        
        //Start Main Schedule
        //fMap.put(task, 
        mainTask.scheduleAtFixedRate
                (task, TIME_DELAY, TIME_PERIOD, TimeUnit.MILLISECONDS);
        //);
        
        //Start Agen Logging Schedule
        //fMap.put(task2, 
        loggingTask.scheduleAtFixedRate
                (task2,TIME_DELAY, TIME_PERIOD, TimeUnit.MILLISECONDS);
        //);
        
        //Stop Main Schedule
        try {
            Thread.sleep(TIME_RUN*1000+TIME_DELAY);
        
            mainTask.shutdown();
            loggingTask.shutdown();
        } catch (InterruptedException ex) {
        } finally{
            mainTask.shutdownNow();
            loggingTask.shutdownNow();
            //fMap.logFuture.cancel(true);
            
            MessageQueueTimer.getInstance().close();
            MessageQueueManager.getInstance().stopAll();
        }
        
        execStop = System.currentTimeMillis();
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
        logger.printResults(logger.titleMarker, 
                "Mode: AgentAutonomy_{} AgentReserve_{} DataGenerator_KeyBalance_{} ProfileGenerator_AgeBalance_{}", 
                new Object[]{AGENT_MODE_AUTONOMY, AGENT_MODE_RESERVE, DATA_MODE_GENERATE, DATA_MODE_PROFILE});
    }
    
    private static void exec_debug(){
        logger.print(mainMarker, "Start Agent System", null);
        
        logger.printMQEvent(logger.fieldMarker, "MQ LimitEvent, MQName, EventMessage", null);
        
        IDToMQN id = IDToMQN.getInstance();
        logger.printResults(logger.fieldMarker, "RootID:{}",new Object[]{id.getAGIDListToString()});
    }

    private static void main_debug(){
        logger.print(mainMarker, "Stop Agent System", null);       
        logger.printResults(logger.resultMarker, 
                "<ALL TransactionTime>_{} [ms]", 
                new Object[]{stop - start - TIME_DELAY});
        logger.printResults(logger.resultMarker, 
                "(<Initialize>_{} [ms] <Create>_{} [ms] <Main>_{}[ms])", 
                new Object[]{initStop - initStart, createStop - createStart, execStop - execStart - TIME_DELAY});
        logger.printResults(logger.dataMarker, "Time,{}", new Object[]{stop - start - TIME_DELAY});
    }
}