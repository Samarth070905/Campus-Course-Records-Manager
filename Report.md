# Project Documentation

## Test data
CSV files with dummy data are provided under `test-data/`:

- `students.csv`
- `courses.csv`

## Syllabus mapping
| Concept                 | Where used |
|--------------------------|------------|
| Singleton                | `AppConfig` |
| Builder Pattern          | `Course` |
| Enum                     | `Semester`, `Grade` |
| Immutable class          | `CourseCode` |
| Custom exceptions        | `DuplicateEnrollmentException`, `MaxCreditLimitExceededException` |
| Streams & lambdas        | `EnrollmentService` |
| NIO.2 API                | `BackupService` |
| Control flow structures  | `ConsoleApp` menu |

## Evolution of Java (short notes)
- **Java 1.0 (1995):** portable, applets  
- **Java 5 (2004):** generics, enums, annotations  
- **Java 8 (2014):** lambdas, Streams, new Date/Time API  
- **Java 17 (2021 LTS):** pattern matching, sealed classes, records  
- **Java 21+ (2023 LTS):** virtual threads (Project Loom), string templates  

## JDK, JRE, JVM
- **JDK:** Development Kit (compiler + tools + JRE)  
- **JRE:** Runtime Environment (JVM + libraries)  
- **JVM:** Executes Java bytecode  

## Java SE vs EE vs ME
- **SE:** Standard Edition (desktop/console apps like this project)  
- **EE (Jakarta):** Enterprise Edition (web/enterprise apps, servers)  
- **ME:** Micro Edition (embedded/mobile)
