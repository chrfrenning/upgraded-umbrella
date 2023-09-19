package g7asmt1.client;

public class TurnaroundTimer {
    private final long startTime;
    private long stopTime;

    public TurnaroundTimer() {
        this.startTime = System.currentTimeMillis();
        stopTime = 0;
    }

    public long clock() {
        if ( stopTime == 0 )
            stopTime = System.currentTimeMillis();
            
        return stopTime - startTime;
    }
}
