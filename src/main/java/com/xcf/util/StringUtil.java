package com.xcf.util;


import com.vdurmont.emoji.EmojiParser;

public class StringUtil {

    /**
     * 是否为空
     *
     * @param content 字符串
     */
    public static boolean isEmpty(String content) {
        if (null == content) {
            return true;
        }
        String replace = content.replace(" ", "");
        return "".equals(replace);
    }

    /**
     * 是否是链接
     *
     * @param content 内容
     */
    public static boolean isUrl(String content) {
        return content.matches("http[s]://.*");
    }


    /**
     * 替换emoji表情
     *
     * @param content 内容
     */
    public static String replaceEmoji(String content, Character replace) {
        if (isEmpty(content)) {
            return content;
        }
        StringBuilder builder = new StringBuilder();
        content = EmojiParser.replaceAllEmojis(content, replace.toString());
        for (int i = 0, len = content.length(); i < len; i++) {
            char charAt = content.charAt(i);
            if (!isEmojiCharacter(charAt)) {
                builder.append(charAt);
            } else {
                builder.append(replace);
            }
        }
        return builder.toString();
    }

    /**
     * 去掉emoji表情
     *
     * @param content 内容
     */
    public static String removeEmoji(String content) {
        if (isEmpty(content)) {
            return content;
        }
        content = EmojiParser.removeAllEmojis(content);
        StringBuilder builder = new StringBuilder();
        for (int i = 0, len = content.length(); i < len; i++) {
            char charAt = content.charAt(i);
            if (!isEmojiCharacter(charAt)) {
                builder.append(charAt);
            }
        }
        return builder.toString();
    }

    /**
     * 去掉emoji表情
     *
     * @param content 内容
     */
    public static boolean containEmoji(String content) {
        if (isEmpty(content)) {
            return false;
        }
        for (int i = 0, len = content.length(); i < len; i++) {
            char charAt = content.charAt(i);
            if (isEmojiCharacter(charAt)) {
                return true;
            }
        }
        String newContent = EmojiParser.removeAllEmojis(content);
        return !content.equals(newContent);
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    public static void main(String[] args) {
//        String s = removeEmoji("阿喃\uD80C\uDDA1");
        System.out.println("s = " + containEmoji("加载更多"));
//        isEmojiCharacter("阿喃\uD80C\uDDA1");
//        System.out.println("s = " + s);
//        System.out.println("s = " + isUrl("https://img2.tapimg.com/bbcode/images/586f232563a23b30f1658d32072fc9c8.jpeg?imageView2/2/w/1320/q/80/format/jpg/interlace/1/ignore-error/1"));
    }
}
