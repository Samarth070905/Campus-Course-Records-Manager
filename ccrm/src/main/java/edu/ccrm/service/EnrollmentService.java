package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;

import java.util.*;

public class EnrollmentService {
    // studentId -> set of course codes
    private final Map<String, Set<String>> enrollmentsByStudent = new HashMap<>();
    // courseCode -> Course
    private final Map<String, Course> courseMap;

    private final int maxCredits;

    public EnrollmentService(Collection<Course> courses, int maxCredits) {
        this.courseMap = new HashMap<>();
        for (Course c : courses) courseMap.put(c.getCode().toString(), c);
        this.maxCredits = maxCredits;
    }

    public void addCourse(Course c) { courseMap.put(c.getCode().toString(), c); }

    public void enroll(String studentId, String courseCode)
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        Objects.requireNonNull(studentId);
        Objects.requireNonNull(courseCode);
        Set<String> courses = enrollmentsByStudent.computeIfAbsent(studentId, k -> new HashSet<>());
        if (!courses.add(courseCode)) {
            throw new DuplicateEnrollmentException("Student already enrolled in " + courseCode);
        }
        int totalCredits = courses.stream()
                .mapToInt(c -> courseMap.getOrDefault(c, zeroCourse()).getCredits())
                .sum();
        if (totalCredits > maxCredits) {
            courses.remove(courseCode); // rollback
            throw new MaxCreditLimitExceededException("Max credits exceeded: " + totalCredits + " > " + maxCredits);
        }
    }

    private Course zeroCourse() {
        return new Course.Builder("ZZ0").title("Unknown").credits(0).build();
    }

    public Set<String> getEnrollments(String studentId) {
        return Collections.unmodifiableSet(enrollmentsByStudent.getOrDefault(studentId, Collections.emptySet()));
    }

    public Map<String, Course> getCourseMap() {
        return Collections.unmodifiableMap(courseMap);
    }
}

