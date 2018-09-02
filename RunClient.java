public abstract class RunClient implements Runnable {
    TileAndBallStorage tb;
    boolean toStop = false;
    public abstract void stop();
}
