import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс для подсчета доставленных данных.
 */
public class DataCounter {
    private AtomicLong counter;
    private long amountData;

    public DataCounter(long amountData) {
        counter = new AtomicLong(0);
        this.amountData = amountData;
    }

    public void incrementCounter() {
        this.counter.getAndIncrement();
    }

    public boolean isAllDataReceived() {
        return (counter.get() == amountData);
    }
}
