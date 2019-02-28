package try2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.min;

public class Solution2 {

    private static final String HORIZONTAL = "H";
    private static final String VERTICAL = "V";
    List<String> slides = new LinkedList<>();
    List<Photo> photos;
    Map<String, Photo> photosMap = new LinkedHashMap<>();

    public Solution2(Scanner input) {
        int picCount = input.nextInt();

        List<Photo> photos = new ArrayList<>(picCount);

        for (int i = 0; i < picCount; i++) {
            String orientation = input.next();
            int tagCount = input.nextInt();

            List<String> tags = Arrays.asList(input.nextLine().trim().split(" "));

            Photo photo = new Photo(String.valueOf(i), orientation, tags);
            photos.add(photo);
            photosMap.put(photo.getId(), photo);
        }

        Collections.shuffle(photos);

        this.photos = photos.stream().filter(it -> it.orientation.equals(HORIZONTAL)).collect(Collectors.toList());


    }

    public List<String> solve() {
        List<String> photoIds = photos.stream().map(it -> it.id).collect(Collectors.toList());
        Photo currentPhoto = photos.iterator().next();
        addSlide(currentPhoto);
        int maxSelectionSize = 250;

        while (photos.size() > maxSelectionSize) {
            int bestScore = Integer.MIN_VALUE;
            int bestIndex = 0;
            String bestId =  photoIds.get(0);

            for (int i = 0; i < maxSelectionSize; ++i) {
                int selectedIndex = ThreadLocalRandom.current().nextInt(photoIds.size());
                String selectedId = photoIds.get(selectedIndex);
                Photo selectedPhoto = photosMap.get(selectedId);
                int selectedScore = currentPhoto.score(selectedPhoto);

                if (selectedScore > bestScore) {
                    bestScore = selectedScore;
                    bestId = selectedId;
                    bestIndex = selectedIndex;
                }
            }

            currentPhoto = photosMap.get(bestId);
            photoIds.remove(bestIndex);
            addSlide(currentPhoto);

            if (photos.size() % 200 == 0) {
                System.out.println(String.format("%d photos left", photos.size()));
            }
        }

        addSlidesSize();
        return slides;
    }

    private void addSlidesSize() {
        slides.add(0, String.valueOf(slides.size()));
    }

    private List<Photo> safeSublist(int size) {
        return this.photos.subList(0, min(photos.size(), size));
    }

    private Optional<Photo> getAny(String orientation) {
        return photos.parallelStream().filter(it -> it.orientation.equals(orientation)).findAny();
    }

    private Slide addSlide(Photo... slidePhotos) {
        Slide slide = new Slide(slidePhotos);

        for (Photo slidePhoto : slidePhotos) {
            photos.remove(slidePhoto);
        }
        slides.add(slide.toString());
        return slide;
    }

    private void addSlide(Photo slidePhoto) {
        photos.remove(slidePhoto);
        slides.add(slidePhoto.getId());
    }

    private Photo middle(List<Photo> aPhotos) {
        return aPhotos.get(aPhotos.size() / 2);
    }

    private Photo middle(SortedSet<Photo> aPhotos) {
        Photo[] photos = aPhotos.toArray(new Photo[aPhotos.size()]);
        return photos[aPhotos.size() / 2];
    }

    private List<Photo> byOrientation(String aHorizontal) {
        return this.photos.parallelStream().filter(it -> it.orientation.equals(aHorizontal)).collect(Collectors.toList());
    }

    static class Slide {

        private final List<Photo> photos;
        private final Set<String> tags;

        Slide(Photo... aPhoto) {
            photos = Arrays.asList(aPhoto);
            tags = tags();
        }

        private Set<String> tags() {
            return photos.stream()
                    .flatMap(it -> it.tags.stream())
                    .collect(Collectors.toSet());
        }

        int score(Slide other) {
            int commonTags = (int) this.getTags().stream().filter(it -> other.getTags().contains(it)).count();
            int s1Unique = this.getTags().size() - commonTags;
            int s2Unique = other.getTags().size() - commonTags;

            return Stream.of(commonTags, s1Unique, s2Unique).min(Integer::compareTo).get();
        }

        Set<String> getTags() {
            return tags;
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

        int score(Photo other) {
            int commonTags = (int) this.getTags().stream().filter(it -> other.getTags().contains(it)).count();
            int s1Unique = this.getTags().size() - commonTags;
            int s2Unique = other.getTags().size() - commonTags;

            return Stream.of(commonTags, s1Unique, s2Unique).min(Integer::compareTo).get();
        }

        public int commonTags(Collection<String> aTags) {
            return (int) this.getTags().stream().filter(aTags::contains).count();
        }

        public String getId() {
            return id;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> aTags) {
            tags = aTags;
        }

        public int tagCount() {
            return tags.size();
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public boolean equals(Object aO) {
            if (this == aO) return true;
            if (!(aO instanceof Photo)) return false;
            Photo photo = (Photo) aO;
            return id.equals(photo.id);
        }
    }
}
