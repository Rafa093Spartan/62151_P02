import java.util.concurrent.atomic.AtomicInteger;

class MonteCarloTask implements Runnable {
    private int numSamples;
    private int threadId;
    private static AtomicInteger globalCount;

    public MonteCarloTask(int numSamples, int threadId, AtomicInteger globalCount) {
        this.numSamples = numSamples;
        this.threadId = threadId;
        MonteCarloTask.globalCount = globalCount;
    }

    @Override
    public void run() {
        int localCount = 0;
        for (int i = 0; i < numSamples; i++) {
            double x = Math.random();
            double y = Math.random();
            if (x * x + y * y <= 1.0) {
                localCount++;
            }
        }
        
        System.out.printf("Hilo %d: añadiendo %d puntos al total.\n", threadId, localCount);
        globalCount.addAndGet(localCount);
    }
}

public class MonteCarloThreads {

    public static void main(String[] args) throws InterruptedException {
        int totalSamples = 1_000_000;
        int numThreads = 4;
        int samplesPerThread = totalSamples / numThreads;

        AtomicInteger globalCount = new AtomicInteger(0);

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new MonteCarloTask(samplesPerThread, i, globalCount));
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        
        double piApprox = (4.0 * globalCount.get()) / totalSamples;

        System.out.println("\nNúmero total de puntos: " + totalSamples);
        System.out.println("Puntos dentro del círculo: " + globalCount.get());
        System.out.println("Aproximación de pi: " + piApprox);
        System.out.println("Error: " + Math.abs(piApprox - Math.PI));
    }
}