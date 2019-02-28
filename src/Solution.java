import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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

        Slide(Photo aPhoto) {
            photos = Collections.singletonList(aPhoto);
            tags = tags();
        }

        Slide(Photo aPhoto1, Photo aPhoto2) {
            photos = Arrays.asList(aPhoto1, aPhoto2);
            tags = tags();
        }

        private Set<String> tags() {
            return photos.stream()
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

        @Override
        public String toString() {
            return photos.stream().map(it -> it.id).collect(Collectors.joining(" "));
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

        public int commonTags(Collection<String> aTags) {
            return (int) this.getTags().stream().filter(aTags::contains).count();
        }

        public int tagCount() {
            return tags.size();
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> aTags) {
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

        for (int i = 0; i < picCount; i++) {
            String orientation = input.next();
            int tagCount = input.nextInt();

            List<String> tags = Arrays.asList(input.nextLine().trim().split(" "));

            photos.add(new Photo(String.valueOf(i), orientation, tags));
        }
    }

    public List<String> solve() {
        List<String> slides = new LinkedList<>();
        photos.sort(Comparator.comparingInt(Photo::tagCount));

        Photo photo = middle(byOrientation(HORIZONTAL));
        this.photos.remove(photo);
        Slide slide = new Slide(photo);
        slides.add(slide.toString());

        while (!this.photos.isEmpty()) {
            final Set<String> tags = slide.getTags();
            Optional<Photo> photo1 = this.photos.stream().filter(it -> it.commonTags(tags) > 0).findAny();

            if (photo1.isPresent()) {
                Photo next = photo1.get();
                photos.remove(next);
                if (next.orientation.equals(HORIZONTAL)) {
                    slide = new Slide(next);
                    slides.add(slide.toString());
                } else {
                    List<Photo> photos = byOrientation(VERTICAL);
                    if (photos.size() > 0) {
                        Photo photo2 = middle(photos);
                        photos.remove(photo2);
                        slide = new Slide(next, photo2);
                        slides.add(slide.toString());
                    }
                }
            } else {
                List<Photo> hPhotos = byOrientation(HORIZONTAL);

                if (hPhotos.isEmpty()) {
                    break;
                } else {
                    photo = middle(hPhotos);
                    hPhotos.remove(photo);
                    slide = new Slide(photo);
                    slides.add(slide.toString());
                }
            }
        }

        slides.add(0, String.valueOf(slides.size()));

        return slides;
    }

    private Photo middle(List<Photo> aPhotos) {
        return aPhotos.get(aPhotos.size() / 2);
    }

    private List<Photo> byOrientation(String aHorizontal) {
        return this.photos.stream().filter(it -> it.orientation.equals(aHorizontal)).collect(Collectors.toList());
    }
}
