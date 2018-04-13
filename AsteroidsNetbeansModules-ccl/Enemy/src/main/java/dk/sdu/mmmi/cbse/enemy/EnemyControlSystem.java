package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IWeapon;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import java.util.Optional;
import java.util.Random;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;


@ServiceProvider(service = IEntityProcessingService.class)
public class EnemyControlSystem implements IEntityProcessingService {

    private Random randomMovement = new Random();
    private Random randomShoot = new Random();
    private int updateDirectionAfter = 35;
    private int updateDirectionCounter = 0;
    private IWeapon weapon;
    private Lookup lookup = Lookup.getDefault(); // TODO: Consider using dependency injection somehow instead..
    
    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);

            // Movement logic
            if (updateDirectionCounter == updateDirectionAfter) {
                movingPart.setLeft(randomMovement.nextBoolean());
                movingPart.setRight(randomMovement.nextBoolean());
                movingPart.setUp(randomMovement.nextBoolean());
                updateDirectionCounter = 0; // reset the counter
            }
            updateDirectionCounter++;
            
            randomShooting(enemy, gameData, world);
            
            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);

            updateShape(enemy);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float size = 8;

        shapex[0] = (float) (x + Math.cos(radians) * size);
        shapey[0] = (float) (y + Math.sin(radians) * size);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * size);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * size);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * size * 0.625);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * size * 0.625);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * size);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * size);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

    private void randomShooting(Entity enemy, GameData gameData, World world) {
        boolean shoot = randomShoot.nextDouble() >= 0.98;
            if (shoot) {
                IWeapon weaponImpl = this.locateWeapon();
                if (weaponImpl != null) {
                    weaponImpl.spawnBullet(enemy, gameData, world);
                }
            }
    }
    
    private IWeapon locateWeapon() {
        if (this.weapon == null) {
            Lookup.Result<IWeapon> result = lookup.lookupResult(IWeapon.class);
            Optional optional = result.allInstances().stream().findFirst();
            
            if (optional.isPresent()) {
                IWeapon weapon = (IWeapon)optional.get();
                System.out.println("IWeapon implementation found: " + optional);
                this.weapon = weapon;
            } else {
                System.out.println("No IWeapon implementation found.");
            }
            
        }
        
        return weapon;
    }

}
