import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class RingProcessor {
    private final int nodesAmount;
    private final long dataAmount;
    private List<Node> nodeList;
    private List<DataPackage> dataPackages;
    private MetricsCollector metricsCollector;

    RingProcessor(int nodesAmount, long dataAmount) {
        this.nodesAmount = nodesAmount;
        this.dataAmount = dataAmount;
        this.nodeList = new ArrayList<>();
        this.dataPackages = new ArrayList<>();
        metricsCollector = new MetricsCollector();

        init();
    }

    private void init() {
        DataCounter dataCounter = new DataCounter(dataAmount);
        metricsCollector.setDataAmount(dataAmount);
        metricsCollector.setNodesAmount(nodesAmount);

        // nodes
        for (int i = 0; i < nodesAmount; i++) {
            Node node = new Node(i, dataCounter);
            nodeList.add(node);
            if (i > 0) {
                nodeList.get(i - 1).setNext(node);
            }
        }
        // add reference to next for the last node
        nodeList.get(nodesAmount - 1).setNext(nodeList.get(0));

        // data
        for (int i = 0; i < dataAmount; i++) {
            String data = "Data_" + i;
            int dest = (int) ((Math.random() * nodesAmount * 19.753) % nodesAmount);

            DataPackage dataPackage = new DataPackage(data, dest);
            // отправляем на какой-то узел (не координатор!)
            nodeList.get(i % nodesAmount).addData(dataPackage);

            // список нужен для подсчета статистики
            dataPackages.add(dataPackage);
        }
    }

    /**
     * Запуск работы кольца.
     */
    public void startProcessing(ExecutorService service) {
        for (int i = 0; i < nodesAmount; i++)
            service.execute(nodeList.get(i));
    }

    private void findLatency() {
        double res = 0;
        for (DataPackage dataPackage : dataPackages) {
            double dataTime = (dataPackage.getEndTime() - dataPackage.getStartTime());// / 1000000.0;
            res += dataTime;
        }
        metricsCollector.setLatency(res / dataPackages.size());
    }

    private void findRingTime() {
        double res = 0;
        for (Node node : nodeList) {
            //double dataTime = (dataPackage.getEndTime() - dataPackage.getStartTime());// / 1000000.0;
            double dataTime = node.getWorkingTime() / 1_000_000_000.0;
            res += dataTime;
        }
        metricsCollector.setTokenRingTime(res / nodeList.size());
    }

    public void saveResults(String filePath) {
        findLatency();
        findRingTime();
        metricsCollector.saveMetrics(filePath);
    }
}
