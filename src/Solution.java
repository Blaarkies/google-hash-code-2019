import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class Solution {

    static final String HORIZONTAL = "H";
    static final String VERTICAL = "V";

    static class Photo {
        String id;
        String orientation;
        List<String> tags;

        public Photo(String aId, String aOrientation, List<String> aTags) {
            id = aId;
            orientation = aOrientation;
            tags = aTags;
        }

        @Override
        public boolean equals(Object aO) {
            if (this == aO) return true;
            if (!(aO instanceof Photo)) return false;
            Photo photo = (Photo) aO;
            return id.equals(photo.id);
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

            List<String> tags = Arrays.asList(input.nextLine().trim().split(" "));

            photos.add(new Photo(String.valueOf(i), orientation, tags));

        }
    }

    public List<String> solve() {
       Collections.shuffle(photos);

       Set<String> used = new HashSet<>();

       List<String> slideshow = new LinkedList<>();

        for (int i = 0, photosSize = photos.size(); i < photosSize; i++) {
            Photo photo = photos.get(i);
            if (photo.orientation.equals(HORIZONTAL)) {
                slideshow.add(photo.id);
            } else {
                Optional<Photo> first = photos.subList(i + 1, photosSize).stream().filter(it -> it.orientation.equals(VERTICAL)).findFirst();

                if (first.isPresent() && !used.contains(first.get().id)) {
                    Photo photo1 = first.get();
                    slideshow.add(photo.id + " " + photo1.id);
                    used.add(photo.id);
                    used.add(photo1.id);
                }
            }
        }

        slideshow.add(0, String.valueOf(slideshow.size()));

        return slideshow;
    }
}
