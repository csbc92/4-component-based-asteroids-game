/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroids;

/**
 *
 * @author ccl
 */
public class AsteroidShape {
    private float[] shapeX = new float[10];
    private float[] shapeY = new float[10];
    private int shapeSize;
    private float posX;
    private float posY;
    private float radians = 0;
    private float pi = 3.1415f;


    public AsteroidShape(int shapeSize, float posX, float posY, float radians) {
        if (shapeSize <= 0) {
            this.shapeSize = 1;
        } else {
            this.shapeSize = shapeSize;
        }

        this.posX = posX;
        this.posY = posY;
        this.radians = radians;
    }

    public float[] getShapeX() {
        shapeX[0] = (float)(posX + Math.cos(radians) * shapeSize * 0.9296f);
        shapeX[1] = (float)(posX + Math.cos(radians + 7*pi/45) * shapeSize * 0.9718f);
        shapeX[2] = (float)(posX + Math.cos(radians + 5*pi/18) * shapeSize * 0.8944f);
        shapeX[3] = (float)(posX + Math.cos(radians + 5*pi/9) * shapeSize * 0.8182f);
        shapeX[4] = (float)(posX + Math.cos(radians + 5*pi/6) * shapeSize * 0.7871f);
        shapeX[5] = (float)(posX + Math.cos(radians + 89*pi/90) * shapeSize * 0.9226f);
        shapeX[6] = (float)(posX + Math.cos(radians + 107*pi/90) * shapeSize);
        shapeX[7] = (float)(posX + Math.cos(radians + 25*pi/18) * shapeSize * 0.8471f);
        shapeX[8] = (float)(posX + Math.cos(radians + 59*pi/36) * shapeSize * 0.9621f);
        shapeX[9] = (float)(posX + Math.cos(radians + 67*pi/36) * shapeSize);

        return this.shapeX;

    }

    public float[] getShapeY() {
        shapeY[0] = (float)(posY + Math.sin(radians) * shapeSize * 0.9296f);
        shapeY[1] = (float)(posY + Math.sin(radians + 7*pi/45) * shapeSize * 0.9718f);
        shapeY[2] = (float)(posY + Math.sin(radians + 5*pi/18) * shapeSize * 0.8944f);
        shapeY[3] = (float)(posY + Math.sin(radians + 5*pi/9) * shapeSize * 0.8182f);
        shapeY[4] = (float)(posY + Math.sin(radians + 5*pi/6) * shapeSize * 0.7871f);
        shapeY[5] = (float)(posY + Math.sin(radians + 89*pi/90) * shapeSize * 0.9226f);
        shapeY[6] = (float)(posY + Math.sin(radians + 107*pi/90) * shapeSize);
        shapeY[7] = (float)(posY + Math.sin(radians + 25*pi/18) * shapeSize * 0.8471f);
        shapeY[8] = (float)(posY + Math.sin(radians + 59*pi/36) * shapeSize * 0.9621f);
        shapeY[9] = (float)(posY + Math.sin(radians + 67*pi/36) * shapeSize);

        return this.shapeY;
    }
}  
