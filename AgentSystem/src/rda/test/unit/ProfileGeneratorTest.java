/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.unit;

import rda.manager.TestCaseManager;
import rda.test.param.TestParameter;
import static rda.test.param.TestParameter.dataParam;
import static rda.test.param.TestParameter.profParam;

/**
 *
 * @author kaeru
 */
public class ProfileGeneratorTest extends TestParameter{
    public static void main(String[] args) {
        TestCaseManager tcmanager = TestCaseManager.getInstance();
        tcmanager.initTestCase(dataParam, profParam);
        
        System.out.println(tcmanager.profgen.toString());
    }
}
