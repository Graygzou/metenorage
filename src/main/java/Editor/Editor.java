package Editor;

import Editor.LeftWindow.LeftFrame;
import Engine.GameEngine;
import Engine.Main.Light.DirectionalLight;
import Engine.Main.Light.PointLight;
import Engine.Main.Light.SpotLight;
import Engine.Main.Material;
import Engine.Main.ScriptFile;
import Engine.System.Graphics.Camera;
import Engine.System.Scripting.Component.Script;
import Game.Input.PlayerKeyboard;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Gregoire Boiron
 */
public class Editor {

    public static List<Material> materials;

    public static void main(String[] args) {

        materials = new ArrayList<>();

        // Center panel that will contains a preview of the game
        GameEngine gameEngine = new GameEngine("FindYourWay", 800, 600, 1);

        ScriptFile scriptRotateHealth = new ScriptFile("ScriptRotateHealth");
        gameEngine.addScript(scriptRotateHealth);

        ScriptFile scriptCamera = new ScriptFile("ScriptPlayerCamera");
        gameEngine.addScript(scriptCamera);

        // Create materials.
        materials.add(new Material("/Game/Textures/feathers.png", 1f));
        materials.add(new Material("/Game/Textures/grassblock.png", 1f));
        materials.add(new Material("/Game/Textures/block.png", 1f));
        materials.add(new Material("/Game/Textures/heart.png", 1f));
        gameEngine.addMaterial(materials.get(0));
        gameEngine.addMaterial(materials.get(1));
        gameEngine.addMaterial(materials.get(2));

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
        mainCamera.setName("Camera principale");
        gameEngine.setCamera(mainCamera);

        gameEngine.start();

        // Create the tools panel that will be on the left side
        new LeftFrame(gameEngine);

        // Create the Components panel that will be on the right side
        // TODO
    }
}
