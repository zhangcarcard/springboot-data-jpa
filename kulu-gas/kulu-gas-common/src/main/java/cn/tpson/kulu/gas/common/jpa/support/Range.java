
package cn.tpson.kulu.gas.common.jpa.support;

import java.io.Serializable;


public class Range<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String field;
    private Comparable left;
    private Comparable right;

    public Range(String field) {
        this.field = field;
    }

    public Range(String field, Comparable left, Comparable right) {
        this.field = field;
        this.left = left;
        this.right = right;
    }

    public Range(Range<T> other) {
        this.field = other.getField();
        this.left = other.getLeft();
        this.right = other.getRight();
    }

    public String getField() {
        return field;
    }

    public Comparable getLeft() {
        return left;
    }

    public void setLeft(Comparable left) {
        this.left = left;
    }

    public Comparable getRight() {
        return right;
    }

    public void setRight(Comparable right) {
        this.right = right;
    }

    public boolean isLeftSet() {
        return getLeft() != null;
    }

    public boolean isRightSet() {
        return getRight() != null;
    }

    public boolean isBetween() {
        return isLeftSet() && isRightSet();
    }

    public boolean isValid() {
        return isBetween() ? getLeft().compareTo(getRight()) <= 0 : true;
    }
}