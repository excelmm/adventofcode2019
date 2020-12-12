import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class pset1a {

    public static void main(String[] args) throws FileNotFoundException {
        
        File file = new File("input.txt");
        Scanner sc = new Scanner(file);
        int sum = 0;
        while (sc.hasNext()) {
            int i = Integer.parseInt(sc.nextLine());
            sum += Math.floorDiv(i, 3) - 2;
        }
        sc.close();
        System.out.println(sum);
    }

}