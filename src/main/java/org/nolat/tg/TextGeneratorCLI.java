package org.nolat.tg;


import javax.swing.*;
import java.io.IOException;

public class TextGeneratorCLI extends JFrame {

    private static final String DEFAULT_INPUT = "input.txt";
    private static final int DEFAULT_NUM_SENTENCES = 100;

    private Corpus corpus;

    public TextGeneratorCLI(String path, int numSentences, int wordsPerSentence) {
        try {
            System.out.printf("Processing corpus from '%s' (this may take awhile)...%n", path);
            corpus = new Corpus(path);
            //corpus.dumpBigrams();

            TextGenerator generator = new TextGenerator(corpus);
            String sentence = generator.generate();
            for (int i = 0; i < numSentences; i++) {

                System.out.println(generator.generate(wordsPerSentence));

            }

        } catch (IOException e) {
            System.out.println("Could not find file " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Generating sentences from default file: 'input.txt'");
            new TextGeneratorCLI(DEFAULT_INPUT, DEFAULT_NUM_SENTENCES, TextGenerator.DEFAULT_MAX_SENTENCE_LENGTH);
        } else if(args.length == 1) {
            new TextGeneratorCLI(args[0], DEFAULT_NUM_SENTENCES, TextGenerator.DEFAULT_MAX_SENTENCE_LENGTH);
        } else if(args.length == 2) {
            new TextGeneratorCLI(args[0], Integer.parseInt(args[1]), TextGenerator.DEFAULT_MAX_SENTENCE_LENGTH);
        } else if(args.length == 3) {
            new TextGeneratorCLI(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        } else {
            System.out.println("Usage: java -jar TextGenerator.jar <input_file> <num_sentences> <sentence_length>");
        }
    }
}
