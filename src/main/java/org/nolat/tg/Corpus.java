package org.nolat.tg;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Corpus {

    private final Random random = new Random();

    public static final String EOL = "{{$}}";

    private final String filename;
    private final List<String> lines;
    private Map<String, List<String>> bigrams;
    private List<String> firsts;


    public Corpus(String filename) throws IOException {
        this.filename = filename;
        System.out.println("Loading corpus in to memory...");
        lines = FileUtils.readLines(new File(filename));
        bigrams = new HashMap<>();
        firsts = new ArrayList<>();

        generateBigrams();
    }

    private void generateBigrams() {
        System.out.println("Generating bigrams...");
        for (String line : lines) {
            String[] words = line.trim().split(" ");
            if (words.length > 1) {
                for (int i = 1; i < words.length; i++) {
                    String previous = words[i - 1];
                    String current = words[i];
                    addBigram(previous, current);
                }
                addBigram(words[words.length - 1], EOL);
            } else {
                addBigram(words[0], EOL);
            }

        }
        System.out.println("Bigrams generated...");
    }

    private void addBigram(String first, String follow) {
        first = first.toLowerCase();
        follow.toLowerCase();
        if (!firsts.contains(first)) {
            firsts.add(first);
        }
        if (bigrams.containsKey(first)) {
            List<String> follows = bigrams.get(first);
            if (!follows.contains(follow)) {
                follows.add(follow);
            }
        } else {
            List<String> follows = new ArrayList<>();
            follows.add(follow);
            bigrams.put(first, follows);
        }
    }

    public void dumpBigrams() {
        for (Map.Entry<String, List<String>> bigram : bigrams.entrySet()) {
            System.out.printf("%s: %s%n", bigram.getKey(), Arrays.toString(bigram.getValue().toArray()));
        }
    }

    public String getRandomFirstWord() {
        return firsts.get(random.nextInt(firsts.size()));
    }

    public String getFilename() {
        return filename;
    }

    public List<String> getFollowers(String first) {
        if (bigrams.get(first) == null) {
            List<String> empty = new ArrayList<>();
            empty.add("");
            return empty;
        }
        return bigrams.get(first);
    }
}
