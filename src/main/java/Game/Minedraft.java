package Game;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.GameEngine;
import Engine.Main.Entity;
import Engine.System.Graphics.Camera;
import Engine.System.Graphics.Component.Cube;
import Game.Input.CameraFollow;

import java.util.Random;

public class Minedraft {
    public static void main(String[] args) {
        try {
            GameEngine gameEngine = new GameEngine("Minedraft", 800, 600);

            int gridWidth = 10, gridHeight = 10;

            for (float i = 0; i < gridWidth; i++) {
                for (float j = 0; j < gridHeight; j++) {
                    Entity block = new Entity("Block (" + i + ", " + j + ")");
                    block.addComponent(new Cube(block, "/Game/Textures/grassblock.png"));
                    block.setPosition(i, (new Random()).nextFloat() * 3, -2f -j);
                    gameEngine.addEntity(block);
                }
            }

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
