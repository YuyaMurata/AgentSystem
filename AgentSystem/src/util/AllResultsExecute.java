package util;

import java.io.IOException;

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
