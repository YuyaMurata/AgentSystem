/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.main;

import java.util.concurrent.Future;
import rda.log.AgentLogSchedule;

/**
 *
 * @author kaeru
 */
public class FutureMap {
    public MainSchedule main;
    public AgentLogSchedule log;
    public Future mainFuture;
    public Future logFuture;
    
    public void put(MainSchedule task, Future f){
        this.main = task;
        this.mainFuture = f;
    }
    public void put(AgentLogSchedule task, Future f){
        this.log = task;
        this.logFuture = f;
    }
}
