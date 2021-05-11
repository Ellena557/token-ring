import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;

public class MainApp {
    public static void main(String[] args) {
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("logsRing.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }


        // вводятся параметры
        TokenRingConfiguration configuration = new TokenRingConfiguration(10, 100);
        ExecutorService executorService = Executors.newFixedThreadPool(configuration.getNumThreads());
        int warmUps = 5;
        int runs = 5;

        for (int i = 0; i < warmUps; i++) {
            System.out.println("Running warm-up number " + i);
            RingProcessor ringProcessor = new RingProcessor(configuration.getNumThreads(),
                    configuration.getNumDataPackages(), fileHandler);
            ringProcessor.startProcessing(executorService);
            System.gc();
        }

        for (int i = 0; i < runs; i++) {
            System.out.println("Running run-up number " + i);
            RingProcessor ringProcessor = new RingProcessor(configuration.getNumThreads(),
                    configuration.getNumDataPackages(), fileHandler);
            ringProcessor.startProcessing(executorService);
            // TODO: save results
            System.gc();
        }

        executorService.shutdown();
    }
}
