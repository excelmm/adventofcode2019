import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class pset2b {

    public static void main(String[] args) throws FileNotFoundException{
        
        File file = new File("input.txt");
        Scanner sc = new Scanner(file);
        ArrayList<Integer> input = new ArrayList<Integer>();
        while(sc.hasNext()) {
            sc.useDelimiter(",");
            input.add(Integer.parseInt(sc.next()));
        }
        ArrayList<Integer> inputcopy = (ArrayList<Integer>) input.clone();
        int inputlen = input.size();
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                input = (ArrayList<Integer>) inputcopy.clone();
                input.set(1, noun);
                input.set(2, verb);
                try {
                    for (int i = 0; i < inputlen - 3; i += 4) {
                        if (input.get(i) == 1) {
                            input.set(input.get(i + 3), input.get(input.get(i + 1)) + input.get(input.get(i + 2)));
                        } else if (input.get(i) == 2) {
                            input.set(input.get(i + 3), input.get(input.get(i + 1)) * input.get(input.get(i + 2)));
                        } else {
                            break;
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
                if (input.get(0) == 19690720) {
                    System.out.println(noun * 100 + verb);
                    break;
                }

            }
            if (input.get(0) == 19690720) break;
        }
        sc.close();
    }
    
}
