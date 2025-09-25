package edu.ccrm.domain;

import java.util.Objects;

public final class CourseCode {
    private final String code;

    public CourseCode(String code) {
        this.code = Objects.requireNonNull(code).trim().toUpperCase();
        if (this.code.isEmpty()) throw new IllegalArgumentException("code cannot be empty");
    }

    public String getCode() { return code; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseCode)) return false;
        CourseCode that = (CourseCode) o;
        return code.equals(that.code);
    }

    @Override public int hashCode() { return code.hashCode(); }

    @Override public String toString() { return code; }
}

