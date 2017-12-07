package Engine;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Noemy Artigouha
 */

import Engine.Helper.Timer;
import Engine.Main.Entity;
import Engine.Main.Material;
import Engine.Main.Sound;
import Engine.System.Component.Messaging.MessageQueue;
import Engine.System.Graphics.Camera;
import Engine.System.Graphics.GraphicsSystem;
import Engine.System.Input.InputSystem;
import Engine.System.Logic.LogicSystem;
import Engine.System.Sound.SoundSystem;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.openal.AL10.alDeleteBuffers;

public class GameEngine implements Runnable {
    private float timePerUpdate = 1f / 50;

    private float timePerRendering = 1f / 30;

    private final Thread gameLoopThread;

    private Window window;

    private Timer timer;

    private List<Entity> entities;

    private List<Material> materials;

    private List<Sound> sounds;

    private LogicSystem logicSystem;

    private GraphicsSystem graphicsSystem;

    private InputSystem inputSystem;

    private SoundSystem soundSystem;

    public MessageQueue messageQueue;

    public GameEngine(String windowTitle, int windowWidth, int windowHeight) {
        this.gameLoopThread = new Thread(this);

        this.window = new Window(windowTitle, windowWidth, windowHeight, true);
        this.timer = new Timer();
        this.messageQueue = new MessageQueue();

        // Systems setup.
        this.logicSystem = new LogicSystem();
        this.graphicsSystem = new GraphicsSystem(this.window);
        this.inputSystem = new InputSystem(window, messageQueue);
        this.soundSystem = new SoundSystem();

        this.entities = new ArrayList<>();
        this.materials = new ArrayList<>();
        this.sounds = new ArrayList<>();
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

        for (Material material : materials) {
            material.initialize();
        }

        this.inputSystem.initialize();
        this.graphicsSystem.initialize();
        this.soundSystem.initialize();
    }

    /**
     * Delegates the input handling to the input handling system.
     */
    protected void handleInput() {
        inputSystem.iterate(entities);
    }

    /**
     * Delegates the update to the game logic system and physics system.
     * @param timeStep The theoretical time step between each update.
     */
    protected void update(float timeStep) {
        logicSystem.iterate(entities);
        messageQueue.dispatch();
    }

    /**
     * Delegates the rendering to the graphics system, and then updates the window.
     */
    protected void render() {
        window.update();
        graphicsSystem.iterate(entities);
    }

    /**
     * Delegates the control of the sounds to the sound system.
     */
    protected void playSounds() {
        soundSystem.iterate(entities);
    }

    private void cleanUp() {
        // TODO REMETTRE :
        // this.graphicsSystem.cleanUp();
        // Clean up song from the engine
        for (Sound s : this.sounds) {
            alDeleteBuffers(s.getId());
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
                playSounds();
                timeSteps -= timePerUpdate;
            }
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
    }

    public void addMaterial(Material material) {
        this.materials.add(material);
    }

    public void addSound(Sound sound) { this.sounds.add(sound); }

    public void setCamera(Camera camera) {
        if(this.graphicsSystem != null)
            this.graphicsSystem.setCamera(camera);

        this.addEntity(camera);
    }

    public void setAmbientLight(Vector3f ambientLight) {
        graphicsSystem.setAmbientLight(ambientLight);
    }
}
