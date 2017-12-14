package Engine;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Noemy Artigouha
 * @author Grégoire Boiron
 * @author Florian Vidal
 */

import Engine.Helper.Timer;
import Engine.Main.Entity;
import Engine.Main.Material;
import Engine.Main.ScriptFile;
import Engine.Main.Sound;
import Engine.Managers.ComponentManager;
import Engine.Managers.MetadataManager;
import Engine.System.Component.Messaging.MessageQueue;
import Engine.System.Graphics.Camera;
import Engine.System.Graphics.GraphicsSystem;
import Engine.System.Input.InputSystem;
import Engine.System.Logic.LogicSystem;
import Engine.System.Physics.PhysicsSystem;
import Engine.System.Scripting.ScriptingSystem;
import Engine.System.Sound.SoundSystem;
import org.joml.Vector3f;

import static org.lwjgl.openal.AL10.alDeleteBuffers;

public class GameEngine implements Runnable {

    private Window window;

    private Timer timer;

    private final Thread gameLoopThread;

    private float timePerUpdate = 1f / 50;
    private float timePerRendering = 1f / 30;

    // Systems
    private LogicSystem logicSystem;
    private PhysicsSystem physicsSystem;
    private GraphicsSystem graphicsSystem;
    private InputSystem inputSystem;
    private SoundSystem soundSystem;
    private ScriptingSystem scriptingSystem;

    public static MessageQueue messageQueue;
    // Resources Manager
    public static MetadataManager metadataManager;
    public static ComponentManager componentManager;

    public GameEngine(String windowTitle, int windowWidth, int windowHeight) {
        this.gameLoopThread = new Thread(this);

        this.window = new Window(windowTitle, windowWidth, windowHeight, true);
        this.timer = new Timer();
        this.messageQueue = new MessageQueue();

        this.componentManager = new ComponentManager();

        // Systems setup.
        this.logicSystem = new LogicSystem();
        this.graphicsSystem = new GraphicsSystem(this.window);
        this.physicsSystem = new PhysicsSystem();
        this.inputSystem = new InputSystem(window, messageQueue);
        this.soundSystem = new SoundSystem();
        this.scriptingSystem = new ScriptingSystem();

        // Resources setup.
        this.metadataManager = MetadataManager.getInstance();
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
        window.initialize();
        timer.initialize();

        for (Material material : this.metadataManager.getMaterials()) {
            material.initialize();
        }

        this.inputSystem.initialize();
        this.graphicsSystem.initialize();
        this.physicsSystem.initialize();
        this.soundSystem.initialize();
        this.scriptingSystem.initialize();
    }

    /**
     * Delegates the input handling to the input handling system.
     */
    protected void handleInput() {
        inputSystem.iterate(this.metadataManager.getEntities());
    }

    /**
     * Delegates the update to the game logic system and physics system.
     * @param timeStep The theoretical time step between each update.
     */
    protected void update(float timeStep) {
        //System.out.println(this.componentManager.getComponents().size());
        //System.out.println(this.metadataManager.getEntities().size());
        logicSystem.iterate(this.metadataManager.getEntities());
        soundSystem.iterate(this.metadataManager.getEntities());
        scriptingSystem.iterate(this.metadataManager.getEntities());
        physicsSystem.iterate(this.metadataManager.getEntities(), timeStep);
    }

    /**
     * Delegates the rendering to the graphics system, and then updates the window.
     */
    protected void render() {
        window.update();
        graphicsSystem.iterate(this.metadataManager.getEntities());
    }

    /**
     * Delegates the control of the sounds to the sound system.
     */
    protected void playSounds() {
    }

    /**
     * Delegates the control of the sounds to the sound system.
     */
    protected void executeScripts() {
    }

    private void cleanUp() {
        // TODO ça marche ?
        //this.graphicsSystem.cleanUp();
        // Clean up song from the engine
        for (Sound s : this.metadataManager.getSounds()) {
            alDeleteBuffers(s.getUniqueID());
        }
        this.soundSystem.cleanUp();
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

            //playSounds();
            //executeScripts();

            render();
            synchronizeRenderer(currentLoopStartTime);
        }

        cleanUp();
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

    public void setUpdatesPerSecond(int updatesPerSecond) {
        this.timePerUpdate = 1f / updatesPerSecond;
    }

    public void setRenderingsPerSecond(int renderingsPerSecond) {
        this.timePerRendering = 1f / renderingsPerSecond;
    }

    public void addEntity(Entity entity) {
      
        this.entities.add(entity);
        this.inputSystem.addEntity(entity);
        this.scriptingSystem.addEntity(entity);
        this.logicSystem.addEntity(entity);
        this.graphicsSystem.addEntity(entity);
        this.soundSystem.addEntity(entity);

        this.metadataManager.registerEntity(entity);

        this.physicsSystem.addEntity(entity);
    }

    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
        this.inputSystem.removeEntity(entity);
        this.scriptingSystem.removeEntity(entity);
        this.logicSystem.removeEntity(entity);
        this.graphicsSystem.removeEntity(entity);
        this.soundSystem.removeEntity(entity);
        this.physicsSystem.removeEntity(entity);
    }

    public void addMaterial(Material material) {
        this.metadataManager.registerMaterial(material);
    }

    public void addSound(Sound sound) { this.metadataManager.registerSound(sound); }

    public void addScript(ScriptFile script) { this.metadataManager.registerScript(script); }

    public void setCamera(Camera camera) {
        if(this.graphicsSystem != null)
            this.graphicsSystem.setCamera(camera);

        this.addEntity(camera);
    }

    public void setAmbientLight(Vector3f ambientLight) {
        graphicsSystem.setAmbientLight(ambientLight);
    }

}
