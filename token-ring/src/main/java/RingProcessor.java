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

    public void findLatency() {
        double res = 0;
        for (DataPackage dataPackage : dataPackages) {
            double dataTime = (dataPackage.getEndTime() - dataPackage.getStartTime());// / 1000000.0;
            res += dataTime;
        }
        System.out.println(res / dataPackages.size());
    }

//    /**
//     * Считается среднее время задержки в сети
//     */
//    public void countAverageNetworkDelay() {
//        DecimalFormat df = new DecimalFormat("#.###");
//        Optional<Long> delay = dataPackages.stream()
//                .map(it -> (it.getEndTime() - it.getStartTime()))
//                .reduce(Long::sum);
//
//        double res = (double) delay.get() / (double) (dataAmount * 1_000_000);
//        logger.info("Average network delay is " + df.format(res) + " ms");
//    }
//
//    /**
//     * Считается среднее время задержки в буфере
//     */
//    public void countAverageBufferDelay() {
//        DecimalFormat df = new DecimalFormat("#.###");
//        Optional<Long> delay = dataPackages.stream()
//                .map(DataPackage::getTotalBufferTime)
//                .reduce(Long::sum);
//
//        double res = (double) delay.get() / (double) (dataAmount * 1_000_000);
//        logger.info("Average buffer delay is " + df.format(res) + " ms");
//    }
}