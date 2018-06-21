package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IEntityProcessingService {
    
    /**
     * The process operation is responsible for processing the game, by using
     * the provided GameData and World.
     * @param gameData Parameter that contains data about the game itself such as
     * game width- and height, events etc.
     * @param world Parameter that contains every entity of the game world.
     * This is basically vector data.
     * 
     * Precondition: The GameData and World should be in the state where it can be processed.
     * Postcondition: The World is processed into the GameData?.
     */
    void process(GameData gameData, World world);
}
