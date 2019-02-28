import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        solve("problem1");
    }

    private static void solve(final String name) throws IOException {
        List<String> input = Files.readAllLines(Paths.get("input/" + name + ".in"));

//        Scanner scanner = new Scanner(new FileInputStream("input.txt"));
//
//        int testCases = scanner.nextInt();
//        for (int t = 0; t < testCases; t++) {
//            int r = scanner.nextInt();
//            int c = scanner.nextInt();
//            int k = scanner.nextInt();
//
//        }

        Files.write(Paths.get("output/problem1.out"), new Solution(input).solve());
    }
}
