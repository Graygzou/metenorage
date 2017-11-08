package Engine;/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class Window {
    /**
     * The title displayed at the top of the window.
     */
    private final String title;

    /**
     * The width, in pixels, of the window.
     */
    private int width;

    /**
     * The height, in pixels, of the window.
     */
    private int height;

    /**
     * Tells whether or not the window can be resized by the user.
     */
    private boolean isResizeable;

    public Window(String title, int width, int height, boolean isResizeable) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.isResizeable = isResizeable;
    }

    public void initialize() {
        // Todo: implement the window initialization logic.
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isResizeable() {
        return isResizeable;
    }
}
