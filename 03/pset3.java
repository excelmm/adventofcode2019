import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class pset3{

    public static void main(String[] args) throws FileNotFoundException {
        
        File file = new File("input.txt");
        Scanner sc = new Scanner(file);
        ArrayList<Point> visited = new ArrayList<Point>();
        ArrayList<Point> doubleVisited = new ArrayList<Point>();
        
        String path = sc.nextLine();
        String[] pathDir = path.split("[\\d,]+");
        String[] pathNum = path.substring(1).split("[A-Za-z,]+");
        Wire wire1 = new Wire(0, 0, pathDir, pathNum);
        for (int i = 0; i < wire1.pathDir.length; i++) {
            visited.addAll(wire1.step(i));
        }
        visited.add(new Point(wire1.x, wire1.y, wire1.wiresteps));
        path = sc.nextLine();
        pathDir = path.split("[\\d,]+");
        pathNum = path.substring(1).split("[A-Za-z,]+");
        Wire wire2 = new Wire(0, 0, pathDir, pathNum);
        for (int i = 0; i < wire2.pathDir.length; i++) {
            doubleVisited.addAll(wire2.doubleStep(i, visited));
        }
        int minlen = 10000000,  minsteps = 10000000;
        for (Point i: doubleVisited){
            int manhattan = Math.abs(i.x) + Math.abs(i.y);
            int mansteps = 0;
            for (Point j: visited) {
                if ((i.x == j.x) && (i.y == j.y)) {
                    mansteps = i.steps + j.steps;
                    break;
                }
            }
            if ((manhattan < minlen) && manhattan != 0) minlen = manhattan;
            if ((mansteps < minsteps) && mansteps != 0) minsteps = mansteps;
        }
        System.out.printf("Part A answer: %d\n", minlen);
        System.out.printf("Part B answer: %d\n", minsteps);
        sc.close();
    }

}

class Wire {

    public int x, y, wiresteps = 0;
    public String[] pathDir, pathNum;
    
    public Wire(int x, int y, String[] pathDir, String[] pathNum) {
        this.x = x;
        this.y = y;
        this.pathDir = pathDir;
        this.pathNum = pathNum;
    }

    public ArrayList<Point> step(int i) {
        String direction = pathDir[i];
        int steps = Integer.parseInt(pathNum[i]);
        ArrayList<Point> visited = new ArrayList<Point>();
        for (int j = 0; j < steps; j++) {
            visited.add(new Point(x, y, this.wiresteps));
            if (direction.equals("R")) {
                x += 1;
            } else if (direction.equals("D")) {
                y -= 1;
            } else if (direction.equals("L")) {
                x -= 1;
            } else {
                y += 1;
            }
            this.wiresteps++;
        }
        return visited;
    }

    public ArrayList<Point> doubleStep(int i, ArrayList<Point> visited) {
        
        String direction = pathDir[i];
        int steps = Integer.parseInt(pathNum[i]);
        ArrayList<Point> doubleVisited = new ArrayList<Point>();
        for (int j = 0; j < steps; j++) {
            Point current = new Point(x, y, this.wiresteps);
            for (Point k: visited) {
                if (current.x == k.x && current.y == k.y) {
                    doubleVisited.add(current);
                }
            }
            if (direction.equals("R")) {
                x += 1;
            } else if (direction.equals("D")) {
                y -= 1;
            } else if (direction.equals("L")) {
                x -= 1;
            } else {
                y += 1;
            }
            this.wiresteps++;
        }
        return doubleVisited;
    }
}

class Point {
    public int x, y, steps;
    public Point(int x, int y, int steps) {
        this.x = x;
        this.y = y;
        this.steps = steps;
    }
}