import Group.Group;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        solve("problem1");
    }

    private static void solve(final String name) throws IOException {
        List<String> input = Files.readAllLines(Paths.get("input/" + name + ".in"));

        List<String> inputLines = Files.readAllLines(Paths.get("input/a_example.in"));
        List<Group> groups = inputLines.stream()
                .map(il -> new Group(il))
                .collect(Collectors.toList());


//        Scanner scanner = new Scanner(new FileInputStream("input/a_example.in"));


/*        int testCases = scanner.nextInt();
        for (int t = 0; t < testCases; t++) {
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            int k = scanner.nextInt();

        }*/

        Files.write(Paths.get("output/problem1.txt"), inputLines);

    }
}
