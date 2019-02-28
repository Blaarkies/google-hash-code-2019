import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {


        List<String> results = Files.readAllLines(Paths.get("input/problem1.txt"));


        Scanner scanner = new Scanner(new FileInputStream("input.txt"));


        int testCases = scanner.nextInt();
        for (int t = 0; t < testCases; t++) {
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            int k = scanner.nextInt();

        }

        Files.write(Paths.get("output/problem1.txt"), results);

    }

    public static Integer distance(Point start, Point end) {
        return Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
    }

}
