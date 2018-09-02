public class TimedRunClient extends RunClient {
    private int milliseconds;
    TimedRunClient(TileAndBallStorage tileAndBallStorage, int millis) {
        tb = tileAndBallStorage;
        milliseconds = millis;
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tb.update();
        } while(!toStop);
    }

    @Override
    public void stop() {
        toStop = true;
    }
}
