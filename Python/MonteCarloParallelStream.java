import java.util.stream.IntStream;
import java.util.concurrent.ThreadLocalRandom;

public class MonteCarloParallelStream {

    public static void main(String[] args) {
        int totalSamples = 1_000_000;

        long globalCount = IntStream.range(0, totalSamples)
                                    .parallel()
                                    .filter(i -> {
                                        
                                        double x = ThreadLocalRandom.current().nextDouble();
                                        double y = ThreadLocalRandom.current().nextDouble();
                                        return x * x + y * y <= 1.0;
                                    })
                                    .count();

        double piApprox = (4.0 * globalCount) / totalSamples;

        System.out.println("--- Usando Parallel Streams ---");
        System.out.println("Puntos dentro del círculo: " + globalCount);
        System.out.println("Aproximación de pi: " + piApprox);
        System.out.println("Error: " + Math.abs(piApprox - Math.PI));
    }
}