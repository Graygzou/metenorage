package Game;

import Engine.GameEngine;
import Engine.Helper.Loader.OBJLoader;
import Engine.Main.Entity;
import Engine.Main.Light.DirectionalLight;
import Engine.Main.Light.SpotLight;
import Engine.Main.Material;
import Engine.Main.Light.PointLight;
import Engine.System.Graphics.Camera;
import Engine.System.Graphics.Component.Mesh3D;
import Engine.System.Sound.SoundSystem;
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

            //gameEngine.addEntity(pointLight);
            gameEngine.setAmbientLight(ambientLight);

            lightPosition = new Vector3f(0, 1f, -2);
            lightColor = new Vector3f(0, 0, 1);
            double angle = Math.toRadians(-60);

            DirectionalLight directionalLight = new DirectionalLight(lightColor, lightPosition, 2f);
            directionalLight.getDirection().x = (float) Math.sin(angle);
            directionalLight.getDirection().y = (float) Math.cos(angle);
            gameEngine.addEntity(directionalLight);

            // Set up a spotlight.
            lightPosition = new Vector3f(0, 8f, - gridHeight / 4);
            pointLight = new PointLight(new Vector3f(0.2f, 1, 0), lightPosition, 1f);
            pointLight.setAttenuation(new PointLight.Attenuation(0.0f, 0.0f, 0.02f));
            Vector3f coneDirection = new Vector3f(0.5f, -1f, -0.5f);
            float cutOff = (float) Math.toRadians(240);
            SpotLight spotLight = new SpotLight(pointLight, coneDirection, cutOff);
            gameEngine.addEntity(spotLight);

            lightPosition = new Vector3f(0, 7.2f, - gridHeight / 4);
            pointLight = new PointLight(new Vector3f(1, 0.2f, 0), lightPosition, 1f);
            pointLight.setAttenuation(new PointLight.Attenuation(0.0f, 0.0f, 0.02f));
            coneDirection = new Vector3f(0.5f, -1f, -0.5f);
            cutOff = (float) Math.toRadians(170);
            spotLight = new SpotLight(pointLight, coneDirection, cutOff);
            gameEngine.addEntity(spotLight);

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
