import java.text.DecimalFormat;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Enter a list of number separated by a comma: ");

        String[] input = scanner.nextLine().split(",");


        ArrayList<Integer> arr = new ArrayList<>();

        for (String s : input) {
            try {
                arr.add(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
                return;
            }
        }

        sortArray(arr);

        var quartiles = findQuartiles(arr);

        HashMap<String, Object> statistics = new HashMap<>(
            Map.of(
                "Mean", df.format(findMean(arr)),
                "Mode", findMode(arr),
                "Min", df.format(quartiles.get("Min")),
                "Q1", df.format(quartiles.get("Q1")),
                "Median", df.format(quartiles.get("Median")),
                "Q3", df.format(quartiles.get("Q3")),
                "Max", df.format(quartiles.get("Max")),
                "Range", findRange(arr)
            )
        );


        System.out.println("Sorted Array: ");
        System.out.println(arr);
        System.out.println("Statistics: ");
        for (Map.Entry<String, Object> entry : statistics.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }



    }

    public static Double findMean(ArrayList<Integer> arr) {
        int sum = 0;

        for (int num : arr) {
            sum += num;
        }

        return ((double) sum / arr.size());
    }

    public static List<Integer> findMode(ArrayList<Integer> arr) {
        Map<Integer, Integer> countMap = new HashMap<>();

        int maxCount = 0;

        for (int num : arr) {
            int count = countMap.getOrDefault(num, 0) + 1;
            countMap.put(num, count);
            maxCount = Math.max(maxCount, count);
        }

        List<Integer> modes = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == maxCount) {
                modes.add(entry.getKey());
            }
        }

        return modes;
    }

    public static Integer findRange(ArrayList<Integer> arr) {
        return arr.getLast() - arr.getFirst();
    }

    public static Double findMedian(ArrayList<Integer> arr) {
        if (arr.size() % 2 == 0) {
            return ((double) arr.get(arr.size() / 2) + (double) arr.get(arr.size() / 2 - 1)) / 2;
        } else {
            return (double) arr.get(arr.size() / 2);
        }
    }

    public static HashMap<String, Double> findQuartiles(ArrayList<Integer> arr) {
        HashMap<String, Double> quartiles = new HashMap<>();


        int min = arr.getFirst();
        int max = arr.getLast();

        double q1 = 0;
        double q3 = 0;


        double lowerIndex = Math.floor(arr.size() * 0.25);
        double upperIndex = Math.floor(arr.size() * 0.75);

        if(arr.size() % 2 == 0) {
            ArrayList<Integer> lowerHalf = new ArrayList<>(arr.subList(0, arr.size() / 2));
            ArrayList<Integer> upperHalf = new ArrayList<>(arr.subList(arr.size() / 2, arr.size()));

            q1 = findMedian(lowerHalf);
            q3 = findMedian(upperHalf);
        }
        else {
           q1 = arr.get((int) lowerIndex);
           q3 = arr.get((int) upperIndex);
        }

        quartiles.put("Min", (double) min);
        quartiles.put("Q1", q1);
        quartiles.put("Median", findMedian(arr));
        quartiles.put("Q3", q3);
        quartiles.put("Max", (double) max);

        return quartiles;
    }

    public static void sortArray(ArrayList<Integer> arr) {
        Collections.sort(arr);
    }

}