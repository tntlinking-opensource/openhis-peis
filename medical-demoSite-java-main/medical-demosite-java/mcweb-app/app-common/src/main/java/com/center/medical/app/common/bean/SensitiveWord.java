package com.center.medical.app.common.bean;

/**
 * @author Citrus
 * @date 2021/8/6 10:25
 */
public class SensitiveWord {
    /**
     * 敏感词
     */
    private String sensitiveWord;

    public String getSensitiveWord() {
        return sensitiveWord;
    }

    public void setSensitiveWord(String sensitiveWord) {
        this.sensitiveWord = sensitiveWord;
    }

    @Override
    public String toString() {
        return "SensitiveWord{" +
                "sensitiveWord='" + sensitiveWord + '\'' +
                '}';
    }
}
