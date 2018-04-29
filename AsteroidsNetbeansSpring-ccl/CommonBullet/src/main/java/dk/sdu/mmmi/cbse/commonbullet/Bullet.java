/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.commonbullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

/**
 *
 * @author ccl
 */
public class Bullet extends Entity {
        
    public Bullet(float x, float y, float radians) {
        super.setColor(0.8f, 0.0f, 1f, 0);
        super.add(new MovingPart(0, 1000, 200, 0));
        super.add(new PositionPart(x, y, radians));
        super.add(new LifePart(1, 2));
    }
}
