import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class pset1b {

    public static void main(String[] args) throws FileNotFoundException {
        
        File file = new File("input.txt");
        Scanner sc = new Scanner(file);
        int sum = 0;
        while (sc.hasNext()) {
            int i = Integer.parseInt(sc.nextLine());
            sum += calcFuel(i);
        }
        sc.close();
        System.out.println(sum);
    }

    public static int calcFuel(int initial) {
        int first = Math.floorDiv(initial, 3) - 2;
        if (first <= 0) {
            return 0;
        } else {
            return first + calcFuel(first);
        }
    }


}