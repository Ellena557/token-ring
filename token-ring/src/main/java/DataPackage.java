public class DataPackage {
    private final String data;
    private final int destinationNode;
    private final long startTime;
    private long endTime;


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

    public long getEndTime() {
        return endTime;
    }

    public int getDestinationNode() {
        return destinationNode;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
