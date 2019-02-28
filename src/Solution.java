import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Solution {

    static final String HORIZONTAL = "H";
    static final String VERTICAL = "V";

    static class Photo {
        int id;
        String orientation;
        List<String> tags;

        public Photo(int aId, String aOrientation, List<String> aTags) {
            id = aId;
            orientation = aOrientation;
            tags = aTags;
        }

        @Override
        public boolean equals(Object aO) {
            if (this == aO) return true;
            if (!(aO instanceof Photo)) return false;
            Photo photo = (Photo) aO;
            return id == photo.id &&
                    Objects.equals(orientation, photo.orientation) &&
                    Objects.equals(tags, photo.tags);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, orientation, tags);
        }
    }

    private List<Photo> photos;

    public Solution(Scanner input) {
        int picCount = input.nextInt();

        photos = new ArrayList<>(picCount);

        for (int i =0; i < picCount; i++) {
            String orientation = input.next();
            int tagCount = input.nextInt();

            List<String> tags = Arrays.asList(input.nextLine().split(" "));

            photos.add(new Photo(i, orientation, tags))

        }
    }

    public List<String> solve() {
        return Collections.emptyList();
    }
}
