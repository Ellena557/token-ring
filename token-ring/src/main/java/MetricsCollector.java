import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class MetricsCollector {
    private int nodesAmount;
    private long dataAmount;
    private double latency;
    private double throughput;
    private double tokenRingTime;

    public int getNodesAmount() {
        return nodesAmount;
    }

    public void setNodesAmount(int nodesAmount) {
        this.nodesAmount = nodesAmount;
    }

    public long getDataAmount() {
        return dataAmount;
    }

    public void setDataAmount(long dataAmount) {
        this.dataAmount = dataAmount;
    }

    public double getLatency() {
        return latency;
    }

    public void setLatency(double latency) {
        this.latency = latency;
    }

    public double getThroughput() {
        return throughput;
    }

    public void setThroughput(double throughput) {
        this.throughput = throughput;
    }

    public double getTokenRingTime() {
        return tokenRingTime;
    }

    public void setTokenRingTime(double tokenRingTime) {
        this.tokenRingTime = tokenRingTime;
    }

    public void saveMetrics(String filePath) {
        throughput = dataAmount / tokenRingTime;
        DecimalFormat df = new DecimalFormat("#.####");

        String data = "Metrics for " + nodesAmount + " nodes and "
                + dataAmount + " messages: \n" + "Latency: " + df.format(latency)
                + " ms \n" + "Throughput: " + df.format(throughput);

        File file = new File(filePath);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
