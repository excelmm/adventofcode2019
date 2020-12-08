import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class pset2a {

    public static void main(String[] args) throws FileNotFoundException{
        
        File file = new File("input.txt");
        Scanner sc = new Scanner(file);
        ArrayList<Integer> input = new ArrayList<Integer>();
        while(sc.hasNext()) {
            sc.useDelimiter(",");
            input.add(Integer.parseInt(sc.next()));
        }
        int inputlen = input.size();
        input.set(1, 12);
        input.set(2, 2);
        for (int i = 0; i < inputlen - 3; i += 4) {
            if (input.get(i) == 1) {
                input.set(input.get(i + 3), input.get(input.get(i + 1)) + input.get(input.get(i + 2)));
            } else if (input.get(i) == 2) {
                input.set(input.get(i + 3), input.get(input.get(i + 1)) * input.get(input.get(i + 2)));
            } else {
                break;
            }
        }
        sc.close();
        System.out.println(input.get(0));
    }
    
}
