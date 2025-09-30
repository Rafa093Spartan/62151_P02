import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PerformanceAnalysis {

    public static void main(String[] args) {
        
        final int SIZE = 1_000_000;
        long[] numbers = new Random().longs(SIZE, 1, 100).toArray();

        
        final int PROCESSORS = Runtime.getRuntime().availableProcessors();
        
        System.out.println("Problema: Sumar " + SIZE + " números en un array.");
        System.out.println("Número de procesadores detectados: " + PROCESSORS);
        System.out.println("-------------------------------------------------");


        
        long startTimeSeq = System.nanoTime();
        long sequentialSum = 0;
        for (long value : numbers) {
            sequentialSum += value;
        }
        long endTimeSeq = System.nanoTime();
        
        
        double timeSequential = TimeUnit.NANOSECONDS.toMillis(endTimeSeq - startTimeSeq);


        
        long startTimePar = System.nanoTime();
        long parallelSum = Arrays.stream(numbers).parallel().sum();
        long endTimePar = System.nanoTime();

       
        double timeParallel = TimeUnit.NANOSECONDS.toMillis(endTimePar - startTimePar);

        System.out.printf("Suma secuencial: %d (calculado en %.2f ms)\n", sequentialSum, timeSequential);
        System.out.printf("Suma paralela:   %d (calculado en %.2f ms)\n", parallelSum, timeParallel);
        System.out.println("-------------------------------------------------");


        
        System.out.println("Análisis Práctico de Rendimiento:");

       
        double speedup = timeSequential / timeParallel;
        System.out.printf("Speedup (S) = T_s / T_p = %.2f / %.2f = %.2fx\n", timeSequential, timeParallel, speedup);

        
        double efficiency = speedup / PROCESSORS;
        System.out.printf("Eficiencia (E) = S / p = %.2f / %d = %.4f (%.2f%%)\n", speedup, PROCESSORS, efficiency, efficiency * 100);

        
        double overhead = (PROCESSORS * timeParallel) - timeSequential;
        System.out.printf("Overhead (T_o) = p * T_p - T_s = %d * %.2f - %.2f = %.2f ms\n", PROCESSORS, timeParallel, timeSequential, overhead);
    }
}