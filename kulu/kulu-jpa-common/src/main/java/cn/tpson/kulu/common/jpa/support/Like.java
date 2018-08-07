
package cn.tpson.kulu.common.jpa.support;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Like implements Serializable {
    private List<Like.Pair> pairs;

    public Like(List<Like.Pair> pairs) {
        Assert.notNull(pairs, "pairs must not be null!");
        this.pairs = Collections.unmodifiableList(pairs);
    }

    public Like(Like.Pair... pairs) {
        this(Arrays.asList(pairs));
    }

    public static Like by(Like.Pair... pairs) {
        Assert.notNull(pairs, "pairs must not be null!");
        return new Like(pairs);
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }

    public static class Pair {
        /**
         * field name.
         */
        private String field;

        /**
         * For example: %val%
         */
        private String pattern;

        public Pair(String field, String pattern) {
            this.field = field;
            this.pattern = pattern;
        }

        public static Like.Pair by(String field, String pattern) {
            return new Like.Pair(field, pattern);
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }
    }
}