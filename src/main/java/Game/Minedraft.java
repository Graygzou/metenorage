package Game;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.GameEngine;
import Engine.Main.Entity;
import Engine.System.Graphics.Camera;
import Engine.System.Graphics.Component.Cube;
import Game.Input.CameraFollow;

public class Minedraft {
    public static void main(String[] args) {
        try {
            GameEngine gameEngine = new GameEngine("Minedraft", 800, 600);

            float[] positions = new float[]{
                    // V0
                    ,};

            Entity block1 = new Entity("Block 1");
            Entity block2 = new Entity("Block 2");
            Entity block3 = new Entity("Block 3");
            block1.addComponent(new Cube(block1, "/Game/Textures/grassblock.png"));
            block2.addComponent(new Cube(block2, "/Game/Textures/grassblock.png"));
            block3.addComponent(new Cube(block3, "/Game/Textures/grassblock.png"));


            block1.setPosition(0, 0, -2f);
            block1.setScale(1f);
            block2.setPosition(1f, 1f, -2f);
            block2.setScale(1f);
            block3.setPosition(-1f, 0f, -2f);
            block3.setScale(1f);

            gameEngine.addEntity(block1);
            gameEngine.addEntity(block2);
            gameEngine.addEntity(block3);


            Camera mainCamera = new Camera();
            mainCamera.setName("Caméra principale");
            mainCamera.addComponent(new CameraFollow(mainCamera));
            gameEngine.setCamera(mainCamera);

            gameEngine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
