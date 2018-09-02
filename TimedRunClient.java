public class TimedRunClient extends RunClient {
    private int milliseconds;
    TimedRunClient(Flip f, int millis) {
        flip = f;
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
            flip.tb.update();
            flip.draw();

        } while(!toStop);
    }

    @Override
    public void stop() {
        toStop = true;
    }
}
