/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;

/**
 *
 * @author 悠也
 */
public class AllAnalisysExecute {
    public static void main(String[] args) throws IOException {
        AnalisysAgentFamily.main(null);
        
        AnalisysTransactionSec.main(null);
    }
}
