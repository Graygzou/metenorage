package Engine.Helper;

public class Timer {
    /*
     * The instant at which last loop occurred.
     */
    private double lastLoopTime;

    /**
     * @return The current system time.
     */
    public static double getTime() {
        return System.nanoTime() / 1000000000.0;
    }

    /**
     * Computes the time since last loop.
     *
     * @return The elapsed time since last loop occurrence.
     */
    public float getElapsedTime() {
        double time = getTime();
        float elapsedTime = (float) (time - lastLoopTime);
        lastLoopTime = time;

        return elapsedTime;
    }

    public double getLastLoopTime() {
        return lastLoopTime;
    }

    public void initialize() {
        lastLoopTime = getTime();
    }
}