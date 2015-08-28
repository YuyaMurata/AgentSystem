/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kaeru
 */
public class Sl4jSample {
    public static void main(String[] args){
        Logger logger = LoggerFactory.getLogger(Sl4jSample.class);
        for (int i = 0 ; i < 1; i++) {
            logger.debug("DEBUG_TEST");
            logger.info("INFO TEST");
            logger.trace("TRACE TEST");
            logger.warn("WARN TEST1,{},{}","WARN TEST2","WARN TEST3");
            logger.error("ERROR TEST");
        }
    }
}
