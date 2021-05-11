public class TokenRingConfiguration {
    private int numThreads;
    private int numDataPackages;

    public TokenRingConfiguration(int numThreads, int numDataPackages) {
        this.numThreads = numThreads;
        this.numDataPackages = numDataPackages;
    }

    public int getNumThreads() {
        return numThreads;
    }

    public int getNumDataPackages() {
        return numDataPackages;
    }

    @Override
    public String toString() {
        return "TokenRingConfiguration: " +
                "numThreads = " + numThreads +
                ", numData = " + numDataPackages;
    }
}
