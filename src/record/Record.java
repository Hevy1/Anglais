package record;

import java.util.HashSet;

public class Record {

    private final HashSet<String> record;
    private final HashSet<String> fav;

    public Record() {
        this.record = new HashSet<>();
        this.fav = new HashSet<>();
    }

    public boolean contains(String word) {
        return record.contains(word);
    }

    public boolean containsFav(String word) {
        return fav.contains(word);
    }

    public void add(String word) {
        record.add(word);
    }

    public void addFav(String word) {
        fav.add(word);
    }

    public void removeFav(String word) {
        fav.remove(word);
    }

    public HashSet<String> getRecord() {
        return record;
    }

    public HashSet<String> getFav() {
        return fav;
    }
}
