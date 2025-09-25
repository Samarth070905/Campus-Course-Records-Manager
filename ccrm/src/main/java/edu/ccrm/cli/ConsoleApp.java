package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.io.BackupService;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;

import java.nio.file.Path;
import java.util.*;

public class ConsoleApp {
    private final Scanner sc = new Scanner(System.in);
    private final AppConfig cfg = AppConfig.getInstance();
    private final List<Course> courses = new ArrayList<>();
    private final EnrollmentService enrollmentService;

    public ConsoleApp() {
        // seed some courses
        courses.add(new Course.Builder("CS101").title("Intro to CS").credits(3).instructor("Dr. Sharma").semester(Semester.SPRING).build());
        courses.add(new Course.Builder("MA101").title("Calculus I").credits(4).instructor("Dr. Reddy").semester(Semester.SPRING).build());
        enrollmentService = new EnrollmentService(courses, cfg.getMaxCreditsPerSemester());
    }

    public static void main(String[] args) {
        new ConsoleApp().run();
    }

    private void printMenu() {
        System.out.println("\n=== CCRM Menu ===");
        System.out.println("1) List courses");
        System.out.println("2) Enroll student");
        System.out.println("3) Show enrollments");
        System.out.println("4) Backup exported folder (demo)");
        System.out.println("0) Exit");
        System.out.print("Choice: ");
    }

    private void run() {
        MAIN_LOOP:
        while (true) {
            printMenu();
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1" -> listCourses();
                case "2" -> enrollStudent();
                case "3" -> showEnrollments();
                case "4" -> demoBackup();
                case "0" -> { System.out.println("Bye."); break MAIN_LOOP; }
                default -> System.out.println("Unknown option");
            }
        }
    }

    private void listCourses() {
        System.out.println("\nAvailable courses:");
        courses.forEach(c -> System.out.println(" - " + c));
    }

    private void enrollStudent() {
        System.out.print("Student id: ");
        String sid = sc.nextLine().trim();
        System.out.print("Course code: ");
        String cc = sc.nextLine().trim().toUpperCase();
        try {
            enrollmentService.enroll(sid, cc);
            System.out.println("Enrolled " + sid + " into " + cc);
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }

    private void showEnrollments() {
        System.out.print("Student id: ");
        String sid = sc.nextLine().trim();
        var set = enrollmentService.getEnrollments(sid);
        if (set.isEmpty()) System.out.println("No enrollments for " + sid);
        else {
            System.out.println("Enrollments for " + sid + ":");
            set.forEach(System.out::println);
        }
    }

    private void demoBackup() {
        Path p = cfg.getDataDir();
        System.out.println("Data dir: " + p);
        try {
            // ensure an exports folder exists (demo)
            Path exports = p.resolve("exports");
            java.nio.file.Files.createDirectories(exports);
            // create a tiny demo file
            java.nio.file.Files.writeString(exports.resolve("demo.txt"), "exported at " + java.time.Instant.now());
            BackupService b = new BackupService();
            var backup = b.createTimestampedBackup(exports);
            long size = b.computeSizeRecursive(backup);
            System.out.println("Backup created at " + backup + " sizeBytes=" + size);
        } catch (Exception e) {
            System.out.println("Backup failed: " + e.getMessage());
        }
    }
}

