package edu.ccrm.domain;

import java.util.Objects;

public final class Course {
    private final CourseCode code;
    private final String title;
    private final int credits;
    private final String instructor;
    private final Semester semester;
    private final String department;

    private Course(Builder b) {
        this.code = b.code;
        this.title = b.title;
        this.credits = b.credits;
        this.instructor = b.instructor;
        this.semester = b.semester;
        this.department = b.department;
    }

    public static class Builder {
        private final CourseCode code;
        private String title = "";
        private int credits = 3;
        private String instructor = "TBD";
        private Semester semester = Semester.FALL;
        private String department = "General";

        public Builder(String code) {
            this.code = new CourseCode(code);
        }

        public Builder title(String t) { this.title = t; return this; }
        public Builder credits(int c) { this.credits = c; return this; }
        public Builder instructor(String i) { this.instructor = i; return this; }
        public Builder semester(Semester s) { this.semester = s; return this; }
        public Builder department(String d) { this.department = d; return this; }
        public Course build() { return new Course(this); }
    }

    @Override public String toString() {
        return String.format("%s - %s (%dcr) [%s] %s", code, title, credits, semester, instructor);
    }

    public CourseCode getCode() { return code; }
    public int getCredits() { return credits; }
    public String getTitle() { return title; }
}

