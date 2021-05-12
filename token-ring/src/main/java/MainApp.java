public class MainApp {
    public static void main(String[] args) {

        // вводятся параметры
        TokenRingConfiguration configuration = new TokenRingConfiguration(10, 10000);
        int warmUps = 10;
        int runs = 8;
        String startPath = "C:\\Users\\Elena\\Desktop\\token-ring\\logs\\";

        for (int i = 0; i < warmUps; i++) {
            System.out.println("Running warm-up number " + i);
            RingProcessor ringProcessor = new RingProcessor(configuration.getNumThreads(),
                    configuration.getNumDataPackages());
            ringProcessor.startProcessing();
            System.gc();
        }

        for (int i = 0; i < runs; i++) {
            System.out.println("Running run-up number " + i);
            RingProcessor ringProcessor = new RingProcessor(configuration.getNumThreads(),
                    configuration.getNumDataPackages());
            ringProcessor.startProcessing();

            // wait to collect statistics
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted!");
            }

            // here we save results
            ringProcessor.saveResults(startPath +"logs " + i + ".txt");
            System.gc();
        }
    }
}
