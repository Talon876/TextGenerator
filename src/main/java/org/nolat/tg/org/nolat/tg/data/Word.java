package org.nolat.tg.org.nolat.tg.data;

import java.util.Arrays;
import java.util.List;

public class Word {

    private final String wordId;
    private final List<String> alternatives;

    public Word(String wordId, List<String> alternatives) {

        this.wordId = wordId;
        this.alternatives = alternatives;
    }

    @Override
    public String toString() {
        return String.format("{%s: {%s}}", wordId, Arrays.toString(alternatives.toArray()));
    }
}
