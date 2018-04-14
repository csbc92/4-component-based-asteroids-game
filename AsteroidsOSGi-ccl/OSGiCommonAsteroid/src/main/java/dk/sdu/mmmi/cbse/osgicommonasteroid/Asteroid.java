package dk.sdu.mmmi.cbse.osgicommonasteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;


public class Asteroid extends Entity {
    
    public static final int SIZE_BIG = 13; //13
    public static final int SIZE_MEDIUM = 8; //8
    public static final int SIZE_SMALL = 4; //4
    
    private boolean isMoving;
    private int size;
    
    public Asteroid() {
        setColor(0.88f, 0.88f, 0.88f, 1); // white
        isMoving = false;
        size = SIZE_BIG;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public boolean getIsMoving() {
        return this.isMoving;
    }
    
    public void setIsMoving(boolean b) {
        this.isMoving = b;
    }
}
