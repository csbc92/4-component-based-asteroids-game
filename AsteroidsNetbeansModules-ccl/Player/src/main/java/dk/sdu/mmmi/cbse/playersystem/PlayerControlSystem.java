package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.SPACE;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IWeapon;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import java.util.Optional;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;

/**
 *
 * @author jcs
 */

@ServiceProvider(service = IEntityProcessingService.class)
public class PlayerControlSystem implements IEntityProcessingService {

    private Lookup lookup = Lookup.getDefault(); // TODO: Consider using dependency injection somehow instead..
    
    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            
            MovingPart movingPart = player.getPart(MovingPart.class);
            movingPart.setLeft(gameData.getKeys().isDown(LEFT));
            movingPart.setRight(gameData.getKeys().isDown(RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(UP));
            
            boolean shoot = gameData.getKeys().isDown(SPACE);
            if (shoot) {
                IWeapon weaponImpl = this.locateWeapon();
                if (weaponImpl != null) {
                    weaponImpl.spawnBullet(player, gameData, world);
                }
            }
            
            movingPart.process(gameData, player);
            positionPart.process(gameData, player);

            updateShape(player);
        }
    }
    
    private IWeapon locateWeapon() {
        
        Lookup.Result<IWeapon> result = lookup.lookupResult(IWeapon.class);
        Optional optional = result.allInstances().stream().findFirst();
        IWeapon weapon = null;

        if (optional.isPresent()) {
            weapon = (IWeapon)optional.get();
        } else {
            System.out.println("No IWeapon implementation found.");
        }
            
        return weapon;
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        int size = 8;

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

}
