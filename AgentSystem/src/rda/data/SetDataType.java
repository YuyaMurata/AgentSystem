/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.data;

/**
 *
 * @author kaeru
 */
public interface SetDataType {
    public final MountData type = new MountData();
    //public final ImpulseData type = new ImpulseData();
    
    public static final DataGenerator DATA_TYPE = new DataGenerator(type);
}
