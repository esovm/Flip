public abstract class RunClient implements Runnable {
    Flip flip;
    boolean toStop = false;
    public abstract void stop();
}
