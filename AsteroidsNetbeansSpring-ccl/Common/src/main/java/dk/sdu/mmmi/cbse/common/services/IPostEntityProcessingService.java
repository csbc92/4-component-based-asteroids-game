package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService  {
    
        /**
         * This method is called after the entities has already been processed.
         * For instance this method can be called if there is a need of collision detection.
         * @param gameData
         * @param world 
         */
        void process(GameData gameData, World world);
}
