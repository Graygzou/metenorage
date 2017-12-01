package Game;

import Engine.GameEngine;
import Engine.Helper.Loader.OBJLoader;
import Engine.Main.Entity;
import Engine.System.Graphics.Camera;
import Engine.System.Graphics.Component.Mesh3D;
import Game.Input.CameraFollow;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 *
 * This is a simplistic implementation of the game Minecraft.
 */

public class Minedraft {
    public static void main(String[] args) {
        try {
            GameEngine gameEngine = new GameEngine("Minedraft", 800, 600);

            int gridWidth = 2, gridHeight = 2;
            Mesh3D cubeMesh = OBJLoader.loadMesh("/Game/Models/cube.obj");
            cubeMesh.setTextureName("/Game/Textures/grassblock.png");

            for (float i = 0; i < gridWidth; i++) {
                for (float j = 0; j < gridHeight; j++) {
                    Entity block = new Entity("Block (" + i + ", " + j + ")");

                    cubeMesh.setEntity(block);
                    block.addComponent(cubeMesh);

                    block.setPosition(i, 0, -2f-j);
                    block.setScale(0.5f);
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
