package com.xcf.util.sensitive;



import com.xcf.util.sensitive.dfa.DfaFilter;
import com.xcf.util.sensitive.dfa.SensitiveContext;
import com.xcf.util.sensitive.dfa.WordNode;

import java.util.*;

public class SensitiveFilterUtil {

    public static Set<String> getSensitiveWord(String name, DfaFilter.MatchType type, String content) {
        if (null == content || "".equals(content)) {
            return Collections.emptySet();
        }
        WordNode wordData = SensitiveContext.getWordData(name);
        if (null == wordData) {
            return Collections.emptySet();
        }
        return DfaFilter.filter(wordData, type, content, null);
    }

    public static Set<String> getSensitiveWord(String name, DfaFilter.MatchType type, String content, Set<String> white) {
        if (null == content || "".equals(content)) {
            return Collections.emptySet();
        }
        WordNode wordData = SensitiveContext.getWordData(name);
        if (null == wordData) {
            return Collections.emptySet();
        }
        return DfaFilter.filter(wordData, type, content, white);
    }

    public static String replace(String name, DfaFilter.MatchType type, String content, Character replace) {
        if (null == content || "".equals(content)) {
            return content;
        }
        WordNode wordData = SensitiveContext.getWordData(name);
        if (null == wordData) {
            return content;
        }
        return DfaFilter.replace(wordData, type, content, replace);
    }

    /**
     * 高亮敏感字
     */
    public static String highlight(String name, DfaFilter.MatchType type, String content, String prefix, String suffix) {
        if (null == content || "".equals(content)) {
            return content;
        }
        WordNode wordData = SensitiveContext.getWordData(name);
        if (null == wordData) {
            return content;
        }
        return DfaFilter.highlight(wordData, type, content, prefix, suffix);
    }

    /**
     * 高亮敏感字
     */
    public static String highlight(String name, DfaFilter.MatchType type, String content, String prefix, String suffix, Set<String> white) {
        if (null == content || "".equals(content)) {
            return content;
        }
        WordNode wordData = SensitiveContext.getWordData(name);
        if (null == wordData) {
            return content;
        }
        return DfaFilter.highlight(wordData, type, content, prefix, suffix, white);
    }

    public static boolean contain(String name, String content) {
        if (null == content || "".equals(content)) {
            return false;
        }
        WordNode wordData = SensitiveContext.getWordData(name);
        if (null == wordData) {
            return false;
        }
        return DfaFilter.contain(wordData, content);
    }

    /**
     * TODO 有BUG
     * <p>
     * 带白名单过滤
     *
     * @param name       敏感字列表名
     * @param type       过滤类型
     * @param content    内容
     * @param replace    替换成的内容
     * @param whiteRegex 白名单正则
     */
    @Deprecated
    public static String replace(String name, DfaFilter.MatchType type, String content, Character replace, String whiteRegex) {
        if (null == content || "".equals(content)) {
            return content;
        }
        WordNode wordData = SensitiveContext.getWordData(name);
        return DfaFilter.replace(wordData, type, content, replace);
    }

    /**
     * TODO 有BUG
     * <p>
     * 带白名单，过滤敏感字
     *
     * @param name       敏感字列表名
     * @param content    内容
     * @param whiteRegex 白名单正则
     */
    @Deprecated
    public static boolean contain(String name, String content, String whiteRegex) {
        if (null == content || "".equals(content)) {
            return false;
        }
        content = content.replaceAll(whiteRegex, "");
        WordNode wordData = SensitiveContext.getWordData(name);
        return DfaFilter.contain(wordData, content);
    }

    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        set.add("中国");
        set.add("中国人民");
        set.add("人民");
        set.add("中国民众");
        HashSet<String> white = new HashSet<>();
        white.add("中国人民");
        white.add("中国民众");
        SensitiveContext.newAddWords("test", set);
        String replace = replace("test", DfaFilter.MatchType.LONG, "中国的国旗叫中国，中国人民叫中国民众", '*');
        System.out.println("replace = " + replace);
        String highlight = highlight("test", DfaFilter.MatchType.LONG, "中国的国旗叫中国，中国人民叫中国民众", "<font>", "</font>", white);
        System.out.println("test = " + highlight);
    }
}
