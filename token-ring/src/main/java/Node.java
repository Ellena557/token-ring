import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Node implements Runnable {
    private final int nodeId;
    private Node next;
    private ConcurrentLinkedQueue<DataPackage> bufferStack;
    private DataCounter dataCounter;

    Node(int nodeId, DataCounter dataCounter) {
        this.nodeId = nodeId;
        this.dataCounter = dataCounter;
        bufferStack = new ConcurrentLinkedQueue();
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
        while (!dataCounter.isAllDataReceived()) {
            // ждем, пока не появится возможность обработать
            if (!bufferStack.isEmpty()) {
                processData();
            }
        }
    }

    private void processData() {
        DataPackage data = bufferStack.poll();

        if (data.getDestinationNode() == this.nodeId) {
            // log
            data.setEndTime(System.nanoTime());
            dataCounter.incrementCounter();
        } else {
            next.addData(data);
//            logger.log(Level.INFO, data.getData() + " sent from "
//                    + nodeId + " to " + next.getNodeId());
        }
    }
}
