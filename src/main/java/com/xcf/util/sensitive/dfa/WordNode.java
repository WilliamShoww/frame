package com.xcf.util.sensitive.dfa;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WordNode {

    /**
     * 当前层次的节点列表
     * 每个层次的节点宽度默认 32
     */
    private final Map<Character, WordNode> nextNodes = new HashMap<>(32);


    /**
     * 是否是某个关键词的结束字
     */
    private boolean isEnd = false;

    /**
     * 当前节点的敏感字
     */
    private Character word;

    public WordNode(Character word) {
        this.word = word;
    }

    public WordNode(boolean isEnd, Character word) {
        this.isEnd = isEnd;
        this.word = word;
    }

    public WordNode nextNode(Character word) {
        return nextNodes.get(word);
    }

    public void add(WordNode wordNode) {
        nextNodes.put(wordNode.word, wordNode);
    }
}
