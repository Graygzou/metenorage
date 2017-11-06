package Logic;

/*
 * This interface gives the minimal function any game logic system should provide.
 *
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public interface GameLogic {
    /**
     * Perform the initialization tasks such a preparing the memory or loading game-specific data.
     *
     * @throws Exception
     */
    void initialize() throws Exception;

    /**
     * Retrieves and processes the inputs.
     */
    void processInput();

    /**
     * Wraps the whole game logic per se and updates the game state.
     *
     * @param timeInterval The time step after which the game logic should be updated.
     */
    void update(float timeInterval);

    /**
     * Renders the logic objects and game-specific data.
     */
    void render();
}
