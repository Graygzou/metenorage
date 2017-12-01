package Game;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.GameEngine;
import Engine.Main.Entity;
import Engine.System.Graphics.Component.Mesh3D;

public class Minedraft {
    public static void main(String[] args) {
        try {
            GameEngine gameEngine = new GameEngine("Minedraft", 800, 600);

            float[] positions = new float[] {
                    // VO
                    -0.5f,  0.5f,  0.5f, 1f,
                    // V1
                    -0.5f, -0.5f,  0.5f, 1f,
                    // V2
                    0.5f, -0.5f,  0.5f, 1f,
                    // V3
                    0.5f,  0.5f,  0.5f, 1f,
                    // V4
                    -0.5f,  0.5f, -0.5f, 1f,
                    // V5
                    0.5f,  0.5f, -0.5f, 1f,
                    // V6
                    -0.5f, -0.5f, -0.5f, 1f,
                    // V7
                    0.5f, -0.5f, -0.5f, 1f,
            };
            float[] colours = new float[]{
                    0.5f, 0.0f, 0.0f,
                    0.0f, 0.5f, 0.0f,
                    0.0f, 0.0f, 0.5f,
                    0.0f, 0.5f, 0.5f,
                    0.5f, 0.0f, 0.0f,
                    0.0f, 0.5f, 0.0f,
                    0.0f, 0.0f, 0.5f,
                    0.0f, 0.5f, 0.5f,
            };
            float[] textCoords = new float[]{
                    0.0f, 0.0f,
                    0.0f, 0.5f,
                    0.5f, 0.5f,
                    0.5f, 0.0f,
                    0.0f, 0.0f,
                    0.5f, 0.0f,
                    0.0f, 0.5f,
                    0.5f, 0.5f,
                    0.0f, 0.5f,
                    0.5f, 0.5f,
                    0.0f, 1.0f,
                    0.5f, 1.0f,
                    0.0f, 0.0f,
                    0.0f, 0.5f,
                    0.5f, 0.0f,
                    0.5f, 0.5f,
                    0.5f, 0.0f,
                    1.0f, 0.0f,
                    0.5f, 0.5f,
                    1.0f, 0.5f,
            };
            int[] indices = new int[] {
                    0, 1, 3, 3, 1, 2,
                    4, 0, 3, 5, 4, 3,
                    3, 2, 7, 5, 3, 7,
                    6, 1, 0, 6, 0, 4,
                    2, 1, 6, 2, 6, 7,
                    7, 6, 4, 7, 4, 5,
            };

            Entity testTriangle = new Entity();
            testTriangle.addComponent(new Mesh3D(testTriangle, positions, indices, textCoords, "/Game/Textures/grassblock.png"));
            testTriangle.setPosition(0, 0, -6f);
            testTriangle.setRotation(0, 0, 0);
            testTriangle.setScale(1f);

            gameEngine.addEntity(testTriangle);

            gameEngine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
