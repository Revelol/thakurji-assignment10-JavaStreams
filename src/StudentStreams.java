import java.lang.String;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;
import java.util.*;
import java.nio.file.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
public class StudentStreams {

    private StringBuffer result;

    public static void main(String[] args) throws IOException {
        StudentStreams stud = new StudentStreams();
        stud.analyzeStudGrade();
    }

    private void analyzeStudGrade() throws IOException {
        result = new StringBuffer();
        determineHighGPA();
        perfectGPA();
        list4GPAStudentsMoreClasses();
        calculateAverage();
        lowestGPA();
        generateSummaryStat();
        System.out.print(result.toString());
    }

    private void determineHighGPA() {
        try {
            Stream<String> grades = Files.lines(Paths.get("StudentsGrades.csv"));
            int count = (int) grades
                    .map(x -> x.split(","))
                    .filter(x -> x.length == 4)
                    .skip(1)
                    .filter(x -> Double.parseDouble(x[2]) >= 3.7)
                    .count();
            grades.close();
            result.append(" * Number of Students with GPA 3.7 and above: " + count + "\n");
        } catch (IOException e) {
            System.out.println("IOException in determineHighGPACount " + e.getMessage());
        }
    }

    private void perfectGPA() {
        try {
            Stream<String> grades = Files.lines(Paths.get("StudentsGrades.csv"));
            result.append("* Students with the perfect GPA: \n");

            grades
                    .map(x -> x.split(","))
                    .filter(x -> x.length == 4)
                    .skip(1)
                    .filter(x -> Double.parseDouble(x[2]) == 4.0)
                    .forEach(x -> result.append("   -" + x[1] + "\n"));
            grades.close();
        } catch (IOException e) {
            System.out.println("IOException in determineHighGPACount " + e.getMessage());
        }
    }
    private void list4GPAStudentsMoreClasses() {
        try {
            Stream<String> grades = Files.lines(Paths.get("StudentsGrades.csv"));

            grades
                    .map(x -> x.split(","))
                    .filter(x -> x.length == 4)
                    .skip(1)
                    .filter(x -> Double.parseDouble(x[2]) == 4)
                    .sorted((x, x1) -> Integer.parseInt(x[3]) < Integer.parseInt(x1[3]) ? 1: -1)
                    .limit(1)
                    .forEach(x -> result.append("*Student with the most CR : "+ x[1] + "with "+ x[2] +"credits\n"));
            grades.close();


        } catch (IOException e) {
            System.out.println("IOException in determineHighGPACount " + e.getMessage());
        }
    }
    private void lowestGPA() {
        try {
            Stream<String> grades = Files.lines(Paths.get("StudentsGrades.csv"));

            grades
                    .map(x -> x.split(","))
                    .filter(x -> x.length == 4)
                    .skip(1)
                    .sorted((x, x1) -> Double.parseDouble(x[2]) > Double.parseDouble(x1[2]) ? 1: -1)
                    .limit(1)
                    .forEach(x -> result.append("*Lowest GPA : "+ x[2] + "\n"));
            grades.close();

        } catch (IOException e) {
            System.out.println("IOException in determineHighGPACount " + e.getMessage());
        }
    }
    private void calculateAverage() {
        try {
            Stream<String> grades = Files.lines(Paths.get("StudentsGrades.csv"));
            Map<String, Double> map = new HashMap<>();
            map  = grades
                    .map(x -> x.split(","))
                    .filter(x -> x.length == 4)
                    .skip(1)
                    .collect(Collectors.toMap(
                            x -> x[0],
                            x -> Double.parseDouble(x[2])));
            grades.close();

            List<Double> gpa = new ArrayList(map.values());
            OptionalDouble average = gpa.stream()
                    .mapToDouble(a -> a)
                    .average();


            result.append("*Average: " + average + " \n");

        } catch (IOException e) {
            System.out.println("IOException in determineHighGPACount " + e.getMessage());
        }
    }
    private void generateSummaryStat() {
        try {
            Stream<String> grades = Files.lines(Paths.get("StudentsGrades.csv"));
            Map<String, Integer> map = new HashMap<>();
            map  = grades
                    .map(x -> x.split(","))
                    .filter(x -> x.length == 4)
                    .skip(1)
                    .collect(Collectors.toMap(
                            x -> x[0],
                            x -> Integer.parseInt(x[3])));

            grades.close();
            List<Integer> credits = new ArrayList(map.values());

            IntSummaryStatistics summary = credits.stream()
                    .mapToInt(a ->a)
                    .summaryStatistics();


            result.append("* " + summary + " \n");

        } catch (IOException e) {
            System.out.println("IOException in determineHighGPACount " + e.getMessage());
        }
    }

}









