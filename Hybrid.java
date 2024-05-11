import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Hybrid {
    private static final int THRESHOLD = 30; // Switch to Insertion Sort when segment size <= 30

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> week = new ArrayList<>();
        ArrayList<ArrayList<Integer>> sorted = new ArrayList<>();
        ArrayList<ArrayList<Integer>> sortedReversed = new ArrayList<>();

        int[] itemsPerDay = {1000, 5000, 10000, 50000, 75000, 100000};
        Random random = new Random();
        for (int day = 0; day < itemsPerDay.length; day++) {
            ArrayList<Integer> currDay = new ArrayList<>();
            for (int item = 0; item < itemsPerDay[day]; item++) {
                currDay.add(random.nextInt(Integer.MAX_VALUE));
            }
            week.add(currDay);
            ArrayList<Integer> sortedDay = new ArrayList<>(currDay);
            hybridSort(sortedDay, 0, sortedDay.size() - 1);
            sorted.add(sortedDay);

            ArrayList<Integer> reversedDay = new ArrayList<>(sortedDay);
            Collections.reverse(reversedDay);
            sortedReversed.add(reversedDay);
        }

        test(week, "Random");
        test(sorted, "Sorted");
        test(sortedReversed, "Reverse-Sorted");
    }

    public static void test(ArrayList<ArrayList<Integer>> data, String description) {
        System.out.println("Testing " + description + " arrays:");
        for (ArrayList<Integer> array : data) {
            long startTime = System.currentTimeMillis();
            hybridSort(array, 0, array.size() - 1);
            long endTime = System.currentTimeMillis();
            System.out.println("Sorted " + array.size() + " elements in " + (endTime - startTime) + " ms");
        }
    }

    public static void hybridSort(ArrayList<Integer> list, int first, int last) {
        while (first < last) {
            if (last - first + 1 <= THRESHOLD) {
                insertionSort(list, first, last);
                break; // Exit after sorting with insertion sort
            } else {
                int pivotIndex = partition(list, first, last);
                if (pivotIndex - first < last - pivotIndex) {
                    hybridSort(list, first, pivotIndex - 1);
                    first = pivotIndex + 1; // Continue in the loop with the second half
                } else {
                    hybridSort(list, pivotIndex + 1, last);
                    last = pivotIndex - 1; // Continue in the loop with the first half
                }
            }
        }
    }

    private static void insertionSort(ArrayList<Integer> list, int first, int last) {
        for (int i = first + 1; i <= last; i++) {
            int current = list.get(i);
            int j = i - 1;
            while (j >= first && list.get(j) > current) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, current);
        }
    }

    private static int partition(ArrayList<Integer> list, int first, int last) {
        int pivot = list.get(last);  // Choosing the last element as pivot
        int i = first - 1;

        for (int j = first; j < last; j++) {
            if (list.get(j) <= pivot) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, last);
        return i + 1;
    }
}