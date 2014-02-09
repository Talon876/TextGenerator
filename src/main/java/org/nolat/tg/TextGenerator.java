package org.nolat.tg;

import java.util.List;
import java.util.Random;

public class TextGenerator {
    public final static int DEFAULT_MAX_SENTENCE_LENGTH = 15;
    private final Random random = new Random();

    private final Corpus corpus;

    public TextGenerator(Corpus corpus) {
        this.corpus = corpus;
    }

    public String generate() {
        return generate(DEFAULT_MAX_SENTENCE_LENGTH);
    }

    public String generate(int maxWordCount) {
        String message = "";

        String currentWord = corpus.getRandomFirstWord();

        for (int i = 0; i < maxWordCount || currentWord.equals(Corpus.EOL); i++) {
            message += currentWord + " ";
            currentWord = chooseWord((corpus.getFollowers(currentWord)));
        }

        return message.replace(Corpus.EOL, "");

    }

    private String chooseWord(List<String> choices) {
        return choices.get(random.nextInt(choices.size()));
    }
}
