/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroids;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author ccl
 */
public class AsteroidPlugin implements IGamePluginService {

    private List<Asteroid> asteroids;
    
    public AsteroidPlugin() {
        System.out.println("Asteroid Plugin ctor..");
    }
    
    @Override
    public void start(GameData gameData, World world) {
        asteroids = createAsteroids(20, gameData);
        
        for (Asteroid asteroid : asteroids) {
            world.addEntity(asteroid);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Asteroid asteroid : asteroids) {
            world.removeEntity(asteroid);
        }
    }

    private List<Asteroid> createAsteroids(int amount, GameData gameData) {
        asteroids = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < amount; i++) {
            Asteroid a = new Asteroid();
            
            float deacceleration = 0; // Never stop moving
            float acceleration = 100000000; // Accelerate like a fucking rocket
            float maxSpeed = 50;
            float rotationSpeed = 5;
            float x = random.nextFloat() * gameData.getDisplayWidth();
            float y = random.nextFloat() * gameData.getDisplayHeight();
            // Set a random radians/direction/orientation for this asteroid
            float radians = random.nextFloat() * 3.1415f;
            
            a.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
            a.add(new PositionPart(x, y, radians));
            
            asteroids.add(a);
        }
        
        return asteroids;
    }
    
}
