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
        }

        for (ArrayList<Integer> day : week) {
            quickSort(day, 0, day.size() - 1);
            sorted.add(new ArrayList<>(day)); 
            ArrayList<Integer> reversed = new ArrayList<>(day);
            Collections.reverse(reversed); 
            sortedReversed.add(reversed); 
        }

        System.out.println(week);

    }

    public static void quickSort(ArrayList<Integer> list, int first, int last) {
        if (first < last) {
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

            quickSort(list, first, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, last);
        }
    }

    public static test 
}