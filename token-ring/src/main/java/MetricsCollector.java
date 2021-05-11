public class MetricsCollector {
    private int nodesAmount;
    private long dataAmount;
    private long latency;
    private long throughput;
    private long tokenRingTime;

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

    public long getLatency() {
        return latency;
    }

    public void setLatency(long latency) {
        this.latency = latency;
    }

    public long getThroughput() {
        return throughput;
    }

    public void setThroughput(long throughput) {
        this.throughput = throughput;
    }

    public long getTokenRingTime() {
        return tokenRingTime;
    }

    public void setTokenRingTime(long tokenRingTime) {
        this.tokenRingTime = tokenRingTime;
    }

    public void saveMetrics(String filePath) {
        throughput = dataAmount / tokenRingTime;

        String data = "Metrics for " + nodesAmount + " nodes and "
                + dataAmount + " messages: /n" + "";
    }
}
