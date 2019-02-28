import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        solve("a_example");
        solve("b_lovely_landscapes");
        solve("c_memorable_moments");
        solve("d_pet_pictures");
        solve("e_shiny_selfies");
    }

    private static void solve(final String name) throws IOException {

        Scanner scanner = new Scanner(new FileInputStream("input/" + name + ".txt"));

        Solution solution = new Solution(scanner);
        Files.write(Paths.get("output/" + name + ".out"), solution.solve());
    }
}
