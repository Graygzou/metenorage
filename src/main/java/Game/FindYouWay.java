package Game;

import Engine.GameEngine;
import Engine.Helper.Loader.OBJLoader;
import Engine.Main.Entity;
import Engine.Main.Light.DirectionalLight;
import Engine.Main.Light.PointLight;
import Engine.Main.Light.SpotLight;
import Engine.Main.Material;
import Engine.Main.ScriptFile;
import Engine.Main.Sound;
import Engine.System.Graphics.Camera;
import Engine.System.Graphics.Component.Mesh3D;
import Engine.System.Physics.Component.BoxRigidBodyComponent;
import Engine.System.Scripting.Component.Script;
import Engine.System.Sound.Component.Source;
import Engine.Utils;;
import Game.Input.PlayerKeyboard;
import org.joml.Vector3f;

/**
 * @author Noemy Artigouha
 * @author Matthieu Le Boucher
 * @author Gregoire Boiron
 */

public class FindYouWay {
    static int BED_ROCK_DEPTH = -2;

    public static void main(String[] args) {
        try {
            boolean testParser = true;

            if(!testParser) {
                GameEngine gameEngine = new GameEngine("FindYourWay", 800, 600);

                ScriptFile scriptRotateHealth = new ScriptFile("ScriptRotateHealth");
                gameEngine.addScript(scriptRotateHealth);

                ScriptFile scriptCamera = new ScriptFile("ScriptPlayerCamera");
                gameEngine.addScript(scriptCamera);

                ScriptFile scriptPlayer = new ScriptFile("ScriptPlayer");
                gameEngine.addScript(scriptPlayer);

                // Create materials.
                Material playerMaterial = new Material("/Game/Textures/feathers.png", 1f);
                Material groundMaterial = new Material("/Game/Textures/block.png", 1f);
                Material healthMaterial = new Material("/Game/Textures/heart.png", 1f);
                gameEngine.addMaterial(playerMaterial);
                gameEngine.addMaterial(groundMaterial);
                gameEngine.addMaterial(healthMaterial);

                //Sound when the player jump
                Sound soundJump = new Sound("Test", "./resources/Game/Sounds/rebond.wav");
                gameEngine.addSound(soundJump);

                // Player block
                Mesh3D cubeMesh = OBJLoader.loadMesh("/Game/Models/cube.obj");
                cubeMesh.setMaterial(playerMaterial);
                Entity blockPlayer = new Entity("Player");
                cubeMesh.setEntity(blockPlayer);
                blockPlayer.addComponent(cubeMesh);
                //Rigidbody MUST BE attached before EntityKeyboard
                blockPlayer.addComponent(new BoxRigidBodyComponent(blockPlayer, 1, 0.2f,0.2f,0.2f));
                blockPlayer.getTransform().setPosition(1f, -1f, -3.5f);
                blockPlayer.getTransform().setScale(0.2f);
                blockPlayer.addComponent(new Script(blockPlayer, scriptPlayer));
                blockPlayer.addComponent(new Source(blockPlayer,soundJump));
                blockPlayer.setTag("player");
                gameEngine.addEntity(blockPlayer);

                //Ground blocks
                cubeMesh = OBJLoader.loadMesh("/Game/Models/cube.obj");
                cubeMesh.setMaterial(groundMaterial);
                Entity block = null;
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 2; j++) {
                        block = new Entity("My block");
                        cubeMesh.setEntity(block);
                        block.addComponent(cubeMesh);
                        block.addComponent(new BoxRigidBodyComponent(block, 0, 0.5f, 0.5f, 0.5f));
                        block.getTransform().setPosition(i, -2f, -4f+j);
                        block.getTransform().setScale(0.5f);
                        gameEngine.addEntity(block);

                        if(i < 2) {
                            block = new Entity("My block");
                            cubeMesh.setEntity(block);
                            block.addComponent(cubeMesh);
                            block.addComponent(new BoxRigidBodyComponent(block, 0, 0.5f, 0.5f, 0.5f));
                            block.getTransform().setPosition(2f+i, -2f, -6.5f+j);
                            block.getTransform().setScale(0.5f);
                            gameEngine.addEntity(block);
                        }
                        if(j == 0) {
                            block = new Entity("My block");
                            cubeMesh.setEntity(block);
                            block.addComponent(cubeMesh);
                            block.addComponent(new BoxRigidBodyComponent(block, 0, 0.5f, 0.5f, 0.5f));
                            block.getTransform().setPosition(2.5f+i, -1.5f, -8.5f+j);
                            block.getTransform().setScale(0.5f);
                            gameEngine.addEntity(block);
                        }
                        if(j == 1 && i != 1) {
                            block = new Entity("My block");
                            cubeMesh.setEntity(block);
                            block.addComponent(cubeMesh);
                            block.addComponent(new BoxRigidBodyComponent(block, 0, 0.5f, 0.5f, 0.5f));
                            block.getTransform().setPosition(2.5f+i, -0.5f, -9.5f+j);
                            block.getTransform().setScale(0.5f);
                            gameEngine.addEntity(block);
                        }
                    }
                    block = new Entity("My block");
                    cubeMesh.setEntity(block);
                    block.addComponent(cubeMesh);
                    block.addComponent(new BoxRigidBodyComponent(block, 0, 0.5f, 0.5f, 0.5f));
                    block.getTransform().setPosition(5.5f+i, 0f, -8.5f);
                    block.getTransform().setScale(0.5f);
                    gameEngine.addEntity(block);

                    //bonus heart blocks
                    if(i == 2) {
                        block = new Entity("My block");
                        cubeMesh.setEntity(block);
                        block.addComponent(cubeMesh);
                        block.addComponent(new BoxRigidBodyComponent(block, 0, 0.5f, 0.5f, 0.5f));
                        block.getTransform().setPosition(9.15f+i, -0.15f, -8.5f);
                        block.getTransform().setScale(0.5f);
                        gameEngine.addEntity(block);
                    } else {
                        block = new Entity("My block");
                        cubeMesh.setEntity(block);
                        block.addComponent(cubeMesh);
                        block.addComponent(new BoxRigidBodyComponent(block, 0, 0.35f, 0.35f, 0.35f));
                        block.getTransform().setPosition(9f+i*1.3f, 0f, -8.5f);
                        block.getTransform().setScale(0.35f);
                        gameEngine.addEntity(block);
                    }

                }

                for(int i = 0; i < 6; i++) {
                    block = new Entity("My block");
                    cubeMesh.setEntity(block);
                    block.addComponent(cubeMesh);
                    block.addComponent(new BoxRigidBodyComponent(block, 0, 0.5f, 0.5f, 0.5f));
                    if(i == 4) {
                        block.getTransform().setPosition(7.5f, 0f-i+1, -7.5f+i*2-1);
                    } if(i == 5) {
                        block.getTransform().setPosition(7.5f, 0f-i+1, -7.5f+i*2-1);
                    } else {
                        block.getTransform().setPosition(7.5f, 0f-i, -7.5f+i*2);
                    }
                    block.getTransform().setScale(0.5f);
                    gameEngine.addEntity(block);

                    block = new Entity("My block");
                    cubeMesh.setEntity(block);
                    block.addComponent(cubeMesh);
                    block.addComponent(new BoxRigidBodyComponent(block, 0, 0.35f, 0.35f, 0.35f));
                    if(i < 3) {
                        block.getTransform().setPosition(7.5f, -4f, 3f+i*0.7f);
                    } else {
                        block.getTransform().setPosition(9.5f-i, -4f, 2.5f+i);
                    }
                    block.getTransform().setScale(0.35f);
                    gameEngine.addEntity(block);
                }
                //final point
                for(int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        block = new Entity("Final block");
                        cubeMesh.setEntity(block);
                        block.addComponent(cubeMesh);
                        block.addComponent(new BoxRigidBodyComponent(block, 0, 0.5f, 0.5f, 0.5f));
                        block.getTransform().setPosition(4f+i, -3.5f, 9f+j);
                        block.getTransform().setScale(0.5f);
                        block.setTag("final block");
                        gameEngine.addEntity(block);
                    }
                }

                // bonus heart element
                cubeMesh = OBJLoader.loadMesh("/Game/Models/cube.obj");
                cubeMesh.setMaterial(healthMaterial);
                block = new Entity("My block");
                cubeMesh.setEntity(block);
                block.addComponent(cubeMesh);
                block.addComponent(new BoxRigidBodyComponent(block, 0, 0.2f, 0.2f, 0.2f));
                block.addComponent(new Script(block, scriptRotateHealth));
                block.getTransform().setPosition(11.2f, 0.7f, -8.5f);
                block.getTransform().setScale(0.2f);
                block.setTag("life");
                gameEngine.addEntity(block);


                // Set lighting.
                int gridWidth = 8, gridHeight = 8;
                Vector3f ambientLight = new Vector3f(0.9f, 0.9f, 0.9f);
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

                // Set the main camera.
                Camera mainCamera = new Camera();
                mainCamera.setName("Caméra principale");
                mainCamera.addComponent(new Script(mainCamera, scriptCamera));
                gameEngine.setCamera(mainCamera);
                //Set the player controls
                blockPlayer.addComponent(new PlayerKeyboard(blockPlayer));

                gameEngine.start();

            } else {

                GameEngine gameEngine = new GameEngine("FindYourWay", 800, 600);

                Utils.parser("Game/gameEditor.json", gameEngine);

                // Set lighting.
                int gridWidth = 8, gridHeight = 8;
                Vector3f ambientLight = new Vector3f(0.9f, 0.9f, 0.9f);
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

                gameEngine.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
