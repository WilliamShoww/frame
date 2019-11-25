package com.xcf.util.sensitive.dfa;


import com.xcf.util.json.JsonUtil;
import com.xcf.util.lang.CollectionUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DfaFilter {

    /**
     * 入库
     *
     * @param head     节点
     * @param keyWords 敏感词库
     */
    public static boolean put(WordNode head, Set<String> keyWords) {
        for (String key : keyWords) {
            put(head, key);
        }
        return true;
    }

    public static boolean put(WordNode head, String word) {
        word = word.trim();
        if (word.length() <= 0) {
            return false;
        }
        WordNode nowNode = head;
        for (int i = 0, len = word.length(); i < len; i++) {
            char charAt = word.charAt(i);
            WordNode tNode = nowNode.nextNode(charAt);
            if (tNode == null) {
                // 默认不是最后的
                tNode = new WordNode(false, charAt);
                nowNode.add(tNode);
            }
            if (i == len - 1 && !tNode.isEnd()) {
                tNode.setEnd(true);
            }
            nowNode = tNode;
        }
        return true;
    }

    /**
     * 获取敏感字列表
     *
     * @param head    字库上下文
     * @param type    匹配类型
     * @param content 内容
     * @param white
     */
    public static Set<String> filter(WordNode head, MatchType type, String content, Set<String> white) {
        Set<String> matchWords = new HashSet<>();

        for (int i = 0, len = content.length(); i < len; i++) {
            int end = matchWord(head, type, i, content);
            if (end > 0) {
                String word = content.substring(i, end + 1);
                if (CollectionUtil.isEmpty(white) || !white.contains(word)) {
                    // 在白名单中不做处理
                    matchWords.add(word);
                }
            }
        }
        return matchWords;
    }

    /**
     * 获取敏感字列表
     *
     * @param head    字库上下文
     * @param type    匹配类型
     * @param content 内容
     */
    public static List<String> getAllKeys(WordNode head, MatchType type, String content) {
        List<String> matchWords = new ArrayList<>();
        for (int i = 0, len = content.length(); i < len; i++) {
            int end = matchWord(head, type, i, content);
            if (end > 0) {
                matchWords.add(content.substring(i, end + 1));
            }
        }
        return matchWords;
    }

    public static String replace(WordNode head, MatchType type, String content, Character replace) {
        Set<String> set = filter(head, type, content, null);
        List<String> list = set.stream().sorted((o1, o2) -> o2.length() - o1.length()).collect(Collectors.toList());
        char[] chars = content.toCharArray();
        for (String word : list) {
            //TODO 比较耗时 可以再优化匹配的时候替换
            int index = content.indexOf(word);
            int wLen = word.length();
            boolean flag = false;
            while (index >= 0) {
                flag = true;
                for (int i = index, len = index + wLen; i < len; i++) {
                    chars[i] = replace;
                }
                index = content.indexOf(word, index + wLen);
            }
            if (flag) {
                content = new String(chars);
            }
        }
        return content;
    }

    /**
     * 高亮
     *
     * @param head    敏感字库
     * @param type    匹配类型
     * @param content 内容
     * @param prefix  前缀内容
     * @param suffix  后缀内容
     */
    public static String highlight(WordNode head, MatchType type, String content, String prefix, String suffix) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, len = content.length(); i < len; i++) {
            int end = matchWord(head, type, i, content);
            if (end > 0) {
                builder.append(prefix).append(content, i, end + 1).append(suffix);
                i = end;
            } else {
                builder.append(content.charAt(i));
            }
        }
        return builder.toString();
    }

    /**
     * 高亮
     *
     * @param head    敏感字库
     * @param type    匹配类型
     * @param content 内容
     * @param prefix  前缀内容
     * @param suffix  后缀内容
     */
    public static String highlight(WordNode head, MatchType type, String content, String prefix, String suffix, Set<String> white) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, len = content.length(); i < len; i++) {
            int end = matchWord(head, type, i, content);
            if (end > 0) {
                String word = content.substring(i, end + 1);
                if (white.contains(word)) {
                    // 在白名单中不做处理
                    builder.append(content.charAt(i));
                } else {
                    builder.append(prefix).append(content, i, end + 1).append(suffix);
                    i = end;
                }
            } else {
                builder.append(content.charAt(i));
            }
        }
        return builder.toString();
    }

    private static int matchWord(WordNode head, MatchType type, int start, String content) {
        int end = 0;
        WordNode currNode = head;
        for (int j = start, len = content.length(); j < len; j++) {
            char charAt = content.charAt(j);
            currNode = currNode.nextNode(charAt);
            if (null == currNode) {
                break;
            }
            if (currNode.isEnd()) {
                end = j;
                if (type == MatchType.SHORT) {
                    break;
                }
            }
        }
        return end;
    }

    public static boolean contain(WordNode head, String content) {
        for (int i = 0, len = content.length(); i < len; i++) {
            WordNode currNode = head;
            for (int j = i; j < len; j++) {
                char charAt = content.charAt(j);
                currNode = currNode.nextNode(charAt);
                if (null == currNode) {
                    break;
                }
                if (currNode.isEnd()) {
                    return true;
                }
            }
        }
        return false;
    }

    public enum MatchType {
        // 最短匹配
        SHORT,
        // 最长匹配
        LONG,
    }

    public static void main(String[] args) {
        WordNode head = new WordNode();
        put(head, "ML花革命");
        put(head, "ML花运方法动");
        put(head, "ML花");
        System.out.println("head = " + JsonUtil.toJson(head));
        String s = replace(head, MatchType.LONG, "我们ML花哈哈ML花革命fff", '*');
        System.out.println("s = " + s);
    }
}
