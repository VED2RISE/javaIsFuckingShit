import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Sorting {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> week = new ArrayList<>();
        ArrayList<ArrayList<Integer>> sorted = new ArrayList<>();
        ArrayList<ArrayList<Integer>> sortedReversed = new ArrayList<>();

        int[] itemsPerDay = {1000, 5000, 10000, 50000, 75000, 100000, 500000};
        Random random = new Random();
        for (int day = 0; day < itemsPerDay.length; day++) {
            ArrayList<Integer> currDay = new ArrayList<>();
            for (int item = 0; item < itemsPerDay[day]; item++) {
                currDay.add(random.nextInt(Integer.MAX_VALUE));
            }
            week.add(currDay);
            ArrayList<Integer> sortedDay = new ArrayList<>(currDay);
            quickSort(sortedDay, 0, sortedDay.size() - 1);
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
            quickSort(array, 0, array.size() - 1);
            long endTime = System.currentTimeMillis();
            System.out.println("Sorted " + array.size() + " elements in " + (endTime - startTime) + " ms");
        }
    }

    public static void quickSort(ArrayList<Integer> list, int first, int last) {
        while (first < last) {
            int pivotIndex = partition(list, first, last);
            if (pivotIndex - first < last - pivotIndex) {
                quickSort(list, first, pivotIndex - 1);
                first = pivotIndex + 1;
            } else {
                quickSort(list, pivotIndex + 1, last);
                last = pivotIndex - 1;
            }
        }
    }
    
    private static int partition(ArrayList<Integer> list, int first, int last) {
        int pivotValue = list.get(last);
        int pivotIndex = first - 1;
        for (int i = first; i < last; i++) {
            if (list.get(i) < pivotValue) {
                pivotIndex++;
                Integer temp = list.get(pivotIndex);
                list.set(pivotIndex, list.get(i));
                list.set(i, temp);
            }
        }
        pivotIndex++;
        Integer temp = list.get(pivotIndex);
        list.set(pivotIndex, list.get(last));
        list.set(last, temp);
        return pivotIndex;
    }
}