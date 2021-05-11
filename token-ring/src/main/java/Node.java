import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Node implements Runnable {
    private final int nodeId;
    private Node next;
    private ConcurrentLinkedQueue<DataPackage> bufferStack;
    private DataCounter dataCounter;
    private long workingTime;

    Node(int nodeId, DataCounter dataCounter) {
        this.nodeId = nodeId;
        this.dataCounter = dataCounter;
        bufferStack = new ConcurrentLinkedQueue();
        workingTime = 0;
    }

    public void addData(DataPackage dataPackage) {
        bufferStack.add(dataPackage);
    }


    public Collection getBuffer() {
        return bufferStack;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public int getNodeId() {
        return nodeId;
    }


    /**
     * Начало работы узла. То есть из Node.bufferStack берётся пакет с данными
     * и отправляется на обработку, после чего передаётся следующему узлу.
     */
    @Override
    public void run() {
        long startTime = System.nanoTime();
        while (!dataCounter.isAllDataReceived()) {
            // ждем, пока не появится возможность обработать
            if (!bufferStack.isEmpty()) {
                processData();
            }
        }
        workingTime += System.nanoTime() - startTime;
    }

    private void processData() {
        DataPackage data = bufferStack.poll();

        if (data.getDestinationNode() == this.nodeId) {
            data.setEndTime(System.nanoTime());
            dataCounter.incrementCounter();
        } else {
            next.addData(data);
        }
    }

    public long getWorkingTime() {
        return workingTime;
    }
}
