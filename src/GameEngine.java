/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Logic.GameLogic;

public class GameEngine implements Runnable {
    private static int updatesPerSecond = 30;

    private final Thread gameLoopThread;

    private GameLogic gameLogic;

    public GameEngine(String windowTitle, int windowWidth, int windowHeight, GameLogic gameLogic) {
        this.gameLoopThread = new Thread(this);
        this.gameLogic = gameLogic;
        // Create window.
    }

    public void start() {
        gameLoopThread.start();
    }

    @Override
    public void run() {
        try {
            initialize();
            gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        // Todo: implement this logic.
        // e.g. initialize the renderer (when we'll have one. (:).
    }

    /**
     * Delegates the input handling to the game logic.
     */
    protected void handleInput() {
        gameLogic.handleInput();
    }

    /**
     * Delegates the update to the game logic.
     * @param timeStep The theoretical time step between each update.
     */
    protected void update(float timeStep) {
        gameLogic.update(timeStep);
    }

    /**
     * Delegates the rendering to the game logic, and then updates the window.
     */
    protected void render() {
        gameLogic.render();
        // Todo: should update the window as well.
    }

    /**
     * Core function of the game engine.
     */
    private void gameLoop() {
        double updateRate = 1.0d / updatesPerSecond;

        while (true) {
            // Todo: implement game loop. (issue #8)
        }
    }

    public static void main(String[] args) {
        try {
            // GameLogic gameLogic = new Game();
            GameEngine gameEngine = new GameEngine("Metenorage", 800, 600, null);
            gameEngine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
