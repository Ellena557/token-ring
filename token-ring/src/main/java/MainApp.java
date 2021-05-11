import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApp {
    public static void main(String[] args) {

        // вводятся параметры
        TokenRingConfiguration configuration = new TokenRingConfiguration(10, 100);
        ExecutorService executorService = Executors.newFixedThreadPool(configuration.getNumThreads());
        int warmUps = 5;
        int runs = 5;

        for (int i = 0; i < warmUps; i++) {
            System.out.println("Running warm-up number " + i);
            RingProcessor ringProcessor = new RingProcessor(configuration.getNumThreads(),
                    configuration.getNumDataPackages());
            ringProcessor.startProcessing(executorService);
            System.gc();
        }

        for (int i = 0; i < runs; i++) {
            System.out.println("Running run-up number " + i);
            RingProcessor ringProcessor = new RingProcessor(configuration.getNumThreads(),
                    configuration.getNumDataPackages());
            ringProcessor.startProcessing(executorService);
            // TODO: save results

            System.gc();
        }

        executorService.shutdown();
    }
}
