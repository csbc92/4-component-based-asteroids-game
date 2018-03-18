package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGameCoreInitialized;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService, IGameCoreInitialized {

    private Entity player;

    public PlayerPlugin() {
        System.out.println("Player plugin constructor...");
    }
    
    @Override
    public void gameCoreInitialized(GameData gameData, World world) {
        System.out.println("Player plugin gameCoreInitialized() method...");

        // Add entities to the world
        player = createPlayerShip(gameData);
        world.addEntity(player);
    }

    @Override
    public void start(GameData gameData, World world) {
        System.out.println("Player plugin start method...");
        gameData.addGameCoreInitializedListener(this);
    }

    private Entity createPlayerShip(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        System.out.println("Player spawn pos x: " + x);
        System.out.println("Player spawn pos y: " + y);
        float radians = 3.1415f / 2;
        
        Entity playerShip = new Player();
        playerShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        playerShip.add(new PositionPart(x, y, radians));
        playerShip.add(new LifePart(3, 0));
        
        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }
}
