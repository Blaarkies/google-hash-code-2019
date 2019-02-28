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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

    private static final String HORIZONTAL = "H";
    private static final String VERTICAL = "V";

    static class Slide {
        private final List<Photo> photos;
        private final Set<String> tags;

        Slide(List<Photo> aPhotos) {
            photos = aPhotos;
            tags = photos.stream()
                    .flatMap(it -> it.tags.stream())
                    .collect(Collectors.toSet());
        }

        Set<String> getTags() {
            return tags;
        }

        int score(Slide other) {
            int commonTags = (int) this.getTags().stream().filter(it -> other.getTags().contains(it)).count();
            int s1Unique = this.getTags().size() - commonTags;
            int s2Unique = other.getTags().size() - commonTags;

            return Stream.of(commonTags, s1Unique, s2Unique).min(Integer::compareTo).get();
        }
    }

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
            return Objects.hash(id);
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
            } else if (!used.contains(photo.id)){
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
