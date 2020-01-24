import java.math.BigDecimal;
import java.util.Arrays;

public class Application {

    public static void main(String[] args) {

        int[] array = {4, 9, 0, 8, 1, 7, 2, 6, 3, 5};
        timeCode(bubbleSort(array));
        timeCode(insertionSort(array));
        timeCode(mergeSort(array));

    }

    private static Runnable bubbleSort(int[] originalArray) {
        return () -> {
            int[] array = Arrays.copyOf(originalArray, originalArray.length);

            for (int iteration = array.length - 1; iteration >= 0; iteration--) { // Step iteration
                for (int currentIndex = 0; currentIndex < iteration; currentIndex++) { // Sort iteration

                    int nextIndex = currentIndex + 1;

                    int currentNumber = array[currentIndex];
                    int nextNumber = array[nextIndex];

                    if (currentNumber > nextNumber) {
                        array[currentIndex] = nextNumber;
                        array[nextIndex] = currentNumber;
                    }

                }
            }

            printArray(array);
        };
    }

    private static Runnable insertionSort(int[] originalArray) {
        return () -> {
            int[] array = Arrays.copyOf(originalArray, originalArray.length);

            for (int iteration = 0; iteration < array.length; iteration++) { // Step iteration

                for (
                        int previousIndex = iteration - 1, currentIndex = iteration;
                        previousIndex >= 0;
                        currentIndex--, previousIndex--
                ) { // Sort Iteration

                    int currentNumber = array[currentIndex];
                    int previousNumber = array[previousIndex];

                    if (previousNumber > currentNumber) {
                        array[currentIndex] = previousNumber;
                        array[previousIndex] = currentNumber;
                    }

                }

            }

            printArray(array);
        };
    }

    private static Runnable mergeSort(int[] originalArray) {
        return () -> {
            int[] leftArray = Arrays.copyOfRange(originalArray, 0, originalArray.length / 2);
            int[] rightArray = Arrays.copyOfRange(originalArray, originalArray.length / 2, originalArray.length);

            if (leftArray.length > 1) {
                mergeSort(leftArray).run();
            }

            if (rightArray.length > 1) {
                mergeSort(rightArray).run();
            }

            for (
                    int iteration = 0, leftArrayIndex = 0, rightArrayIndex = 0;
                    iteration < originalArray.length;
                    iteration++) {

                boolean leftArrayIsDone = !(leftArrayIndex < leftArray.length);
                boolean rightArrayIsDone = !(rightArrayIndex < rightArray.length);

                if (leftArrayIsDone) {

                    while (rightArrayIndex < rightArray.length) {
                        originalArray[iteration] = rightArray[rightArrayIndex];
                        iteration++;
                        rightArrayIndex++;
                    }

                    continue;

                } else if (rightArrayIsDone) {

                    while (leftArrayIndex < leftArray.length) {
                        originalArray[iteration] = leftArray[leftArrayIndex];
                        iteration++;
                        leftArrayIndex++;
                    }

                    continue;

                }

                if (leftArray[leftArrayIndex] < rightArray[rightArrayIndex]) {

                    originalArray[iteration] = leftArray[leftArrayIndex];
                    leftArrayIndex++;

                } else {

                    originalArray[iteration] = rightArray[rightArrayIndex];
                    rightArrayIndex++;

                }
            }

            printArray(originalArray);
        };
    }

    public static void timeCode(Runnable code) {
        BigDecimal start = BigDecimal.valueOf(System.nanoTime());
        code.run();
        BigDecimal end = BigDecimal.valueOf(System.nanoTime());
        BigDecimal elapsed = end.subtract(start)
                .divide(BigDecimal.valueOf(1_000_000_000), 4, BigDecimal.ROUND_HALF_UP); // From nanosecond to second
        System.out.println(String.format("Finished in %s seconds.", elapsed));
    }

    public static void printArray(int[] array) {
        Arrays.stream(array).forEach(e -> System.out.print(e + " "));
        System.out.println();
    }

}
