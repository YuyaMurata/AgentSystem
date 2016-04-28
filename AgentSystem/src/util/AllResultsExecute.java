/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;

/**
 *
 * @author kaeru
 */
public class AllResultsExecute {
    public static void main(String[] args) throws IOException {
        ResultsAgentSystemInfo.main(null);
        
        ResultsAgentSystemCPU.main(null);
        
        ResultsAgentTransaction.main(null);
        
        ResultsMessageQueue.main(null);
        
        ResultsMessageLatency.main(null);
        
        ResultsAgentCloning.main(null);
        
        DetectCloningAgent.main(null);
    }
}
