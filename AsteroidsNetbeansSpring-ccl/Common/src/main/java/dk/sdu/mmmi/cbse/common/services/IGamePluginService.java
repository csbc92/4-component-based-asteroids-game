package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IGamePluginService {
    
    /**
     * Adds data on component/plugin install
     * @param gameData
     * @param world 
     */
    void start(GameData gameData, World world);

    /**
     * Removes data on component/plugin uninstall
     * @param gameData
     * @param world 
     */
    void stop(GameData gameData, World world);
}
