package Game;

import Engine.GameEngine;
import Engine.Helper.Loader.OBJLoader;
import Engine.Main.Entity;
import Engine.Main.Material;
import Engine.Main.Light.PointLight;
import Engine.System.Graphics.Camera;
import Engine.System.Graphics.Component.Mesh3D;
import Game.Input.CameraFollow;
import org.joml.Vector3f;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 *
 * This is a simplistic implementation of the game Minecraft.
 */

public class Minedraft {
    public static void main(String[] args) {
        try {
            GameEngine gameEngine = new GameEngine("Minedraft", 800, 600);

            // Create materials.
            Material material = new Material("/Game/Textures/grassblock.png", 1f);
            gameEngine.addMaterial(material);

            int gridWidth = 6, gridHeight = 6;
            Mesh3D cubeMesh = OBJLoader.loadMesh("/Game/Models/cube.obj");
            cubeMesh.setMaterial(material);

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

            // Set lighting.
            Vector3f ambientLight = new Vector3f(0.3f, 0.3f, 0.3f);
            Vector3f lightColor = new Vector3f(1, 0.7f, 0.7f);
            Vector3f lightPosition = new Vector3f(gridWidth / 2, 2f, -2 - gridHeight / 2);
            float lightIntensity = 5.0f;
            PointLight pointLight = new PointLight(lightColor, lightPosition, lightIntensity);
            pointLight.setAttenuation(new PointLight.Attenuation(0.0f, 0.0f, 1.0f));

            gameEngine.setPointLight(pointLight);
            gameEngine.setAmbientLight(ambientLight);

            // Set the main camera.
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
