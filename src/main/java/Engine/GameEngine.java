package Engine;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.Helper.Timer;
import Engine.Main.Entity;
import Engine.System.Graphics.Component.Cube;
import Engine.System.Graphics.Component.Plane;
import Engine.System.Graphics.GraphicsSystem;
import Engine.System.Logic.Component.TestComponent;
import Engine.System.Logic.LogicSystem;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class GameEngine implements Runnable {
    private float timePerUpdate = 1f / 50;

    private float timePerRendering = 1f / 30;

    private final Thread gameLoopThread;

    private Window window;

    private Timer timer;

    private List<Entity> entities;

    private LogicSystem logicSystem;

    private GraphicsSystem graphicsSystem;

    public GameEngine(String windowTitle, int windowWidth, int windowHeight) {
        this.gameLoopThread = new Thread(this);

        this.window = new Window(windowTitle, windowWidth, windowHeight, true);
        this.timer = new Timer();

        // Systems setup.
        this.logicSystem = new LogicSystem();
        this.graphicsSystem = new GraphicsSystem(this.window);

        this.entities = new ArrayList<>();
    }

    public void start() {
        gameLoopThread.start();
    }

    @Override
    public void run() {
        try {
            System.out.println("Metenorage game engine started...");

            initialize();
            gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void initialize() throws Exception {
        // Todo: implement this logic.
        // e.g. initialize the renderer (when we'll have one. (:).

        window.initialize();
        timer.initialize();

        this.graphicsSystem.initialize();
    }

    /**
     * Delegates the input handling to the game logic.
     */
    protected void handleInput() {
        // Todo: implement this logic.
        float xIncrement = 0;
        float yIncrement = 0;
        float zIncrement = 0;
        float scaleIncrement = 0;

        if (window.isKeyPressed(GLFW_KEY_UP)) {
            xIncrement = 1;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            xIncrement = -1;
        } else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            yIncrement = -1;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            yIncrement = 1;
        } else if (window.isKeyPressed(GLFW_KEY_A)) {
            zIncrement = -1;
        } else if (window.isKeyPressed(GLFW_KEY_Q)) {
            zIncrement = 1;
        } else if (window.isKeyPressed(GLFW_KEY_Z)) {
            scaleIncrement = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            scaleIncrement = 1;
        }

        for(Entity entity: entities) {
            // Update position
            Vector3f entityPosition = entity.getPosition();
            float newXPosition = entityPosition.x + yIncrement * 0.01f;
            float newYPosition = entityPosition.y + xIncrement * 0.01f;
            float newZPosition = entityPosition.z + zIncrement * 0.01f;
            entity.setPosition(newXPosition, newYPosition, newZPosition);

            // Update scale
            float scale = entity.getScale().x;
            scale += scaleIncrement * 0.05f;
            if (scale < 0) {
                scale = 0;
            }
            entity.setScale(scale);

            // Update rotation angle
            /*
            float rotation = entity.getRotation().x + 1.5f;
            if (rotation > 360) {
                rotation = 0;
            }

            entity.setRotation(rotation, rotation, rotation);
            */
        }
    }

    /**
     * Delegates the update to the game logic.
     * @param timeStep The theoretical time step between each update.
     */
    protected void update(float timeStep) {
        // Todo: implement this logic.
        logicSystem.iterate(entities);
    }

    /**
     * Delegates the rendering to the game logic, and then updates the window.
     */
    protected void render() {
        // Todo: implement this logic.
        window.update();
        graphicsSystem.iterate(entities);
    }

    private void cleanUp() {
        graphicsSystem.cleanUp();
    }

    /**
     * Core function of the game engine.
     */
    private void gameLoop() {
        double previousLoopTime = Timer.getTime();
        double timeSteps = 0;

        while (!window.windowShouldClose()) {
            // Keep track of the elapsed time and time steps.
            double currentLoopStartTime = Timer.getTime();
            double elapsedTime = currentLoopStartTime - previousLoopTime;
            previousLoopTime = currentLoopStartTime;
            timeSteps += elapsedTime;

            handleInput();

            while (timeSteps >= timePerUpdate) {
                update((float) timeSteps);
                timeSteps -= timePerUpdate;
            }

            render();
            synchronizeRenderer(currentLoopStartTime);
        }
    }

    /**
     * Avoids exhausting the system resources by controlling the rendering rate independently..
     *
     * @param currentLoopStartTime The time at with the current loop started.
     */
    private void synchronizeRenderer(double currentLoopStartTime) {
        while(Timer.getTime() < currentLoopStartTime + this.timePerRendering) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {}
        }
    }

    public static void main(String[] args) {
        try {
            // GameLogic gameLogic = new Game();
            GameEngine gameEngine = new GameEngine("Metenorage", 800, 600);

            Entity testEntity = new Entity();
            Entity testTriangle = new Entity();
            Entity testEntityPlane = new Entity();

            // Read from a json file to load Entity and Components attached
           // Utils.parser("Game/example.json", gameEngine);


            // Create and active a component
            TestComponent test = new TestComponent(testEntity);
            test.setActiveState(true);

            // Create a graphics components
            Plane testPlane = new Plane(testEntityPlane);
            Cube testCube = new Cube(testTriangle);

            // Set the position, scale and rotation of graphics entities
            testEntityPlane.setPosition(0, 0, -2);
            testEntityPlane.setRotation(0, 0, 0);
            testEntityPlane.setScale(1f);

            testTriangle.setPosition(0, 0, -2);
            testTriangle.setRotation(0, 45, 45);
            testTriangle.setScale(1f);


            // Add all the components to their entities
            testEntity.addComponent(test);
            testEntityPlane.addComponent(testPlane);
            testTriangle.addComponent(testCube);

            // Add all the entities to the gameEngine
            gameEngine.addEntity(testEntityPlane);
            gameEngine.addEntity(testEntity);
            gameEngine.addEntity(testTriangle);


            gameEngine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void setUpdatesPerSecond(int updatesPerSecond) {
        this.timePerUpdate = 1f / updatesPerSecond;
    }

    public void setRenderingsPerSecond(int renderingsPerSecond) {
        this.timePerRendering = 1f / renderingsPerSecond;
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }
}
