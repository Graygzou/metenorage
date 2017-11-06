package Logic;

/*
 * This interface gives the minimal function any game logic system should provide.
 *
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public interface GameLogic {
    void init() throws Exception;

    void processInput();

    void update(float timeInterval);

    void render();
}
