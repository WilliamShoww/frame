package com.xcf.util.sensitive.dfa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SensitiveContext {

    private static final Map<String, WordNode> context = new HashMap<>();

    public static void newAddWords(String name, String fileName, Charset charset) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                ClassLoader.getSystemResourceAsStream(fileName)
                , charset));
        WordNode head = context.get(name);
        initWordData(head, name, reader);
    }

    public static void newAddWords(String name, Set<String> words) {
        WordNode head = context.get(name);
        if (null == head) {
            head = new WordNode();
        }
        DfaFilter.put(head, words);
        addWordData(name, head);
    }

    public static void initWordData(String name, String fileName, Charset charset) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                ClassLoader.getSystemResourceAsStream(fileName)
                , charset));
        WordNode head = new WordNode();
        initWordData(head, name, reader);
    }

    public static void initWordData(String name, Set<String> words) {
        WordNode head = new WordNode();
        DfaFilter.put(head, words);
        addWordData(name, head);
    }

    private static void initWordData(WordNode head, String name, BufferedReader reader) {
        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                DfaFilter.put(head, line);
            }
            addWordData(name, head);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static synchronized void addWordData(String name, WordNode head) {
        context.put(name, head);
    }

    public static synchronized void removeWordData(String name) {
        context.remove(name);
    }

    public static synchronized WordNode getWordData(String name) {
        return context.get(name);
    }
}
