package edu.ccrm;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentServiceTest {

    @Test
    void enrollAndDuplicate() throws Exception {
        Course c1 = new Course.Builder("CS101").credits(3).semester(Semester.SPRING).build();
        Course c2 = new Course.Builder("MA101").credits(4).semester(Semester.SPRING).build();
        EnrollmentService s = new EnrollmentService(List.of(c1, c2), 10);

        s.enroll("s1", "CS101");
        assertTrue(s.getEnrollments("s1").contains("CS101"));

        assertThrows(DuplicateEnrollmentException.class, () -> s.enroll("s1", "CS101"));
    }

    @Test
    void maxCreditLimit() throws Exception {
        Course c1 = new Course.Builder("C1").credits(10).semester(Semester.FALL).build();
        Course c2 = new Course.Builder("C2").credits(20).semester(Semester.FALL).build();
        EnrollmentService s = new EnrollmentService(List.of(c1, c2), 15);

        s.enroll("sX", "C1");
        assertThrows(MaxCreditLimitExceededException.class, () -> s.enroll("sX", "C2"));
    }
}
