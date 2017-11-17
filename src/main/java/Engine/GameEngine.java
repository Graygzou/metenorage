package Engine;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.Helper.Timer;
import Engine.Main.Entity;
import Engine.System.Graphics.Component.Mesh3D;
import Engine.System.Graphics.GraphicsSystem;
import Engine.System.Logic.Component.TestComponent;
import Engine.System.Logic.LogicSystem;

import java.util.ArrayList;
import java.util.List;

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

        this.window = new Window(windowTitle, windowWidth, windowHeight, false);
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

        while (true) {
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
            
            // Read from a json file to load Entity and Components attached 
           // Utils.parser("Game/example.json", gameEngine);
            
            
            // Create and active a component
            TestComponent test = new TestComponent(testEntity);
            test.setActiveState(true);

            Entity testTriangle = new Entity();
            testTriangle.addComponent(new Mesh3D(testTriangle, new float[]{
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
            }, new int[]{
                    // Front face
                    0, 1, 3, 3, 1, 2,
                    // Top Face
                    4, 0, 3, 5, 4, 3,
                    // Right face
                    3, 2, 7, 5, 3, 7,
                    // Left face
                    6, 1, 0, 6, 0, 4,
                    // Bottom face
                    2, 1, 6, 2, 6, 7,
                    // Back face
                    7, 6, 4, 7, 4, 5,
            }, new float[]{
                    0.5f, 0.0f, 0.0f, 1f,
                    0.0f, 0.5f, 0.0f, 1f,
                    0.0f, 0.0f, 0.5f, 1f,
                    0.0f, 0.5f, 0.5f, 1f,
                    0.5f, 0.0f, 0.0f, 1f,
                    0.0f, 0.5f, 0.0f, 1f,
                    0.0f, 0.0f, 0.5f, 1f,
                    0.0f, 0.5f, 0.5f, 1f,
            }));
            testTriangle.setPosition(0, 0, -2);
            testTriangle.setRotation(0, 45, 45);
            testTriangle.setScale(1f);

            // Add the component to the entity
            testEntity.addComponent(test);
            //gameEngine.addEntity(testEntity);
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
