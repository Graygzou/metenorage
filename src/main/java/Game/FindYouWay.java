package Game;

import Engine.GameEngine;
import Engine.Helper.Loader.OBJLoader;
import Engine.Main.Entity;
import Engine.Main.Light.DirectionalLight;
import Engine.Main.Light.PointLight;
import Engine.Main.Light.SpotLight;
import Engine.Main.Material;
import Engine.System.Graphics.Camera;
import Engine.System.Graphics.Component.Mesh3D;
import Engine.System.Physics.Component.BoxRigidBodyComponent;
import Engine.Utils;
import Game.Input.CameraKeyboard;
import org.joml.Vector3f;

/**
 * @author Noemy Artigouha
 * @author Matthieu Le Boucher
 */

public class FindYouWay {
    static int BED_ROCK_DEPTH = -2;

    public static void main(String[] args) {
        try {
            boolean testParser = false;

            if(!testParser) {
                GameEngine gameEngine = new GameEngine("FindYourWay", 800, 600);

                // Create a game sound
                //Sound son = new Sound("Test", "./resources/Game/Sounds/sonTest.wav");
                //gameEngine.addSound(son);

                //ScriptFile script = new ScriptFile("ScriptTest");
                //gameEngine.addScript(script);

                // Create materials.
                Material playerMaterial = new Material("/Game/Textures/feathers.png", 1f);
                Material groundMaterial = new Material("/Game/Textures/leaf.png", 1f);
                Material groundSolutionMaterial = new Material("/Game/Textures/block.png", 1f);
                gameEngine.addMaterial(playerMaterial);
                gameEngine.addMaterial(groundMaterial);
                gameEngine.addMaterial(groundSolutionMaterial);


                // Player block
                Mesh3D cubeMesh = OBJLoader.loadMesh("/Game/Models/cube.obj");
                cubeMesh.setMaterial(playerMaterial);
                Entity block = new Entity("My block");
                cubeMesh.setEntity(block);
                block.addComponent(cubeMesh);
                block.addComponent(new BoxRigidBodyComponent(block, 1, 0.5f,0.5f,0.5f));
                block.setPosition(1f, -1f, -3.5f);
                block.setScale(0.2f);
                gameEngine.addEntity(block);

                //Ground blocks
                cubeMesh = OBJLoader.loadMesh("/Game/Models/cube.obj");
                cubeMesh.setMaterial(groundMaterial);
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 2; j++) {
                        block = new Entity("My block");
                        cubeMesh.setEntity(block);
                        block.addComponent(cubeMesh);
                        block.addComponent(new BoxRigidBodyComponent(block, 1, 0.5f, 0.5f, 0.5f));
                        block.setPosition(i, -2f, -4f+j);
                        block.setScale(0.5f);
                        gameEngine.addEntity(block);

                        if(i < 2) {
                            block = new Entity("My block");
                            cubeMesh.setEntity(block);
                            block.addComponent(cubeMesh);
                            block.addComponent(new BoxRigidBodyComponent(block, 1, 0.5f, 0.5f, 0.5f));
                            block.setPosition(2f+i, -2f, -6.5f+j);
                            block.setScale(0.5f);
                            gameEngine.addEntity(block);
                        }
                        if(j == 0) {
                            block = new Entity("My block");
                            cubeMesh.setEntity(block);
                            block.addComponent(cubeMesh);
                            block.addComponent(new BoxRigidBodyComponent(block, 1, 0.5f, 0.5f, 0.5f));
                            block.setPosition(2.5f+i, -1.5f, -8.5f+j);
                            block.setScale(0.5f);
                            gameEngine.addEntity(block);
                        }
                        if(j == 1 && i != 1) {
                            block = new Entity("My block");
                            cubeMesh.setEntity(block);
                            block.addComponent(cubeMesh);
                            block.addComponent(new BoxRigidBodyComponent(block, 1, 0.5f, 0.5f, 0.5f));
                            block.setPosition(2.5f+i, -0.5f, -9.5f+j);
                            block.setScale(0.5f);
                            gameEngine.addEntity(block);
                        }
                    }
                    block = new Entity("My block");
                    cubeMesh.setEntity(block);
                    block.addComponent(cubeMesh);
                    block.addComponent(new BoxRigidBodyComponent(block, 1, 0.5f, 0.5f, 0.5f));
                    block.setPosition(5.5f+i, 0f, -8.5f);
                    block.setScale(0.5f);
                    gameEngine.addEntity(block);
                }

                for(int i = 0; i < 6; i++) {
                    block = new Entity("My block");
                    cubeMesh.setEntity(block);
                    block.addComponent(cubeMesh);
                    block.addComponent(new BoxRigidBodyComponent(block, 1, 0.5f, 0.5f, 0.5f));
                    if(i == 4) {
                        block.setPosition(7.5f, 0f-i+1, -7.5f+i*2-1);
                    } if(i == 5) {
                        block.setPosition(7.5f, 0f-i+1, -7.5f+i*2-1);
                    } else {
                        block.setPosition(7.5f, 0f-i, -7.5f+i*2);
                    }
                    block.setScale(0.5f);
                    gameEngine.addEntity(block);

                    block = new Entity("My block");
                    cubeMesh.setEntity(block);
                    block.addComponent(cubeMesh);
                    block.addComponent(new BoxRigidBodyComponent(block, 1, 0.5f, 0.5f, 0.5f));
                    if(i < 3) {
                        block.setPosition(7.5f, -4f, 3f+i*0.7f);
                    } else {
                        block.setPosition(9.5f-i, -4f, 2.5f+i);
                    }
                    block.setScale(0.35f);
                    gameEngine.addEntity(block);
                }
                cubeMesh = OBJLoader.loadMesh("/Game/Models/cube.obj");
                cubeMesh.setMaterial(groundSolutionMaterial);
                for(int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        block = new Entity("My block");
                        cubeMesh.setEntity(block);
                        block.addComponent(cubeMesh);
                        block.addComponent(new BoxRigidBodyComponent(block, 1, 0.5f, 0.5f, 0.5f));
                        block.setPosition(4f+i, -3.5f, 9f+j);
                        block.setScale(0.5f);
                        gameEngine.addEntity(block);
                    }
                }


                // Set lighting.
                int gridWidth = 8, gridHeight = 8;
                Vector3f ambientLight = new Vector3f(0.6f, 0.6f, 0.6f);
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
                lightPosition = new Vector3f(0, 8f, -gridHeight / 4);
                pointLight = new PointLight(new Vector3f(0.2f, 1, 0), lightPosition, 1f);
                pointLight.setAttenuation(new PointLight.Attenuation(0.0f, 0.0f, 0.02f));
                Vector3f coneDirection = new Vector3f(0.5f, -1f, -0.5f);
                float cutOff = (float) Math.toRadians(240);
                SpotLight spotLight = new SpotLight(pointLight, coneDirection, cutOff);
                gameEngine.addEntity(spotLight);

                lightPosition = new Vector3f(0, 7.2f, -gridHeight / 4);
                pointLight = new PointLight(new Vector3f(1, 0.2f, 0), lightPosition, 1f);
                pointLight.setAttenuation(new PointLight.Attenuation(0.0f, 0.0f, 0.02f));
                coneDirection = new Vector3f(0.5f, -1f, -0.5f);
                cutOff = (float) Math.toRadians(170);
                spotLight = new SpotLight(pointLight, coneDirection, cutOff);
                gameEngine.addEntity(spotLight);

                // Set the main camera.
                Camera mainCamera = new Camera();
                mainCamera.setName("Caméra principale");
                mainCamera.addComponent(new CameraKeyboard(mainCamera));
                gameEngine.setCamera(mainCamera);

                gameEngine.start();

            } else {

                GameEngine gameEngine = new GameEngine("FindYourWay", 800, 600);

                Utils.parser("Game/example.json", gameEngine);

                gameEngine.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
