public class DataPackage {
    private final String data;
    private final int destinationNode;
    private long startTime;
    private long ringTime;

    public DataPackage(String data, int destinationNode) {
        this.destinationNode = destinationNode;
        this.data = data;

        // Фиксируется время, когда создаётся пакет данных. Необходимо для
        // вычисления времени доставки до узла назначения.
        startTime = System.nanoTime();
    }

    public long getStartTime() {
        return this.startTime;
    }

    public String getData() {
        return this.data;
    }

    public int getDestinationNode() {
        return destinationNode;
    }

    public long getRingTime() {
        return ringTime;
    }

    public void setRingTime(long ringTime) {
        this.ringTime = ringTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
