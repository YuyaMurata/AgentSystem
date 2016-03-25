/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.data;

import rda.data.type.MountData;
import rda.property.SetProperty;

/**
 *
 * @author kaeru
 */
public interface SetDataType extends SetProperty{
    public static final MountData type = new MountData(
                TIME_RUN, 
                TIME_PERIOD, 
                DATA_VOLUME, 
                NUMBER_OF_USER_AGENTS, 
                AGENT_DEFAULT_VALUE,
                DATA_MODE_GENERATE,
                Integer.MAX_VALUE // Random Seed
            );
    
    public static final DataGenerator DATA_TYPE = new DataGenerator(type);
}