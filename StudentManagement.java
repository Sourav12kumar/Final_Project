import java.io.*;
import java.util.*;

class Student {
    private String id;
    private String name;
    private int age;
    private double grade;

    public Student(String id, String name, int age, double grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getGrade() { return grade; }

    @Override
    public String toString() {
        return id + " - " + name + " | Age: " + age + " | Grade: " + grade;
    }
}

public class StudentManagement {
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        loadFromFile();

        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Delete Student by ID");
            System.out.println("5. Save to File");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> searchStudent();
                case 4 -> deleteStudent();
                case 5 -> saveToFile();
                case 6 -> {
                    saveToFile();
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addStudent() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = scanner.nextInt();

        System.out.print("Enter Grade: ");
        double grade = scanner.nextDouble();
        scanner.nextLine();

        students.add(new Student(id, name, age, grade));
        System.out.println("✅ Student added successfully!");
    }

    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    static void searchStudent() {
        System.out.print("Enter Student ID to search: ");
        String id = scanner.nextLine();
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                System.out.println("Found: " + s);
                return;
            }
        }
        System.out.println("❌ Student not found.");
    }

    static void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine();
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equalsIgnoreCase(id)) {
                it.remove();
                System.out.println("✅ Student deleted.");
                return;
            }
        }
        System.out.println("❌ Student not found.");
    }

    static void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                pw.println(s.getId() + "," + s.getName() + "," + s.getAge() + "," + s.getGrade());
            }
            System.out.println("📁 Saved to file.");
        } catch (IOException e) {
            System.out.println("❌ Failed to save file.");
        }
    }

    static void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                students.add(new Student(data[0], data[1], Integer.parseInt(data[2]), Double.parseDouble(data[3])));
            }
            System.out.println("📁 Loaded students from file.");
        } catch (IOException e) {
            System.out.println("⚠️ No saved data found. Starting fresh.");
        }
    }
}
