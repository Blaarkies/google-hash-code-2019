package Group;

import static java.lang.Integer.parseInt;

public class Group {

    private int a;
    private int b;
    private int c;

    public Group(String inputLine) {
        this.a = parseInt(inputLine.substring(0, 1));
        this.b = parseInt(inputLine.substring(1, 1));
        this.c = parseInt(inputLine.substring(2, 1));
    }
}
