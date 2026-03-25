import java.io.*;
import java.util.*;

public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    // _____реализуйте класс Student ниже в этом же файле______

    private double averageScore;
    private Student highestStudent;


    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        // TODO: реализуйте чтение файла здесь
        try(BufferedReader n = new BufferedReader(new FileReader("input/students.txt"))){
            String line;
            while((line =n.readLine()) != null){
                try {
                    String[] parts = line.split(",");
                    if (parts.length < 2)
                        continue;;

                    String name = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());

                    if (score < 0 || score >100){
                        throw new InvalidScoreException("score out of range");
                    }
                    students.add(new Student(name,score));
                } catch (NumberFormatException e){
                    System.out.println("Invalid data (Number format): " +line);
                } catch (InvalidScoreException e) {
                    System.out.println("Invalid data (Score range): " +e.getMessage());
                }
            }


        } catch ( IOException e){
            System.out.println("error: " + e.getMessage());

    }}

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        // TODO: обработка данных и сортировка здесь
        if (students.isEmpty()) return;

        double sum=0;
        highestStudent=students.get(0);

        for(Student s:students){
            sum += s.score;
            if(s.score > highestStudent.score){
                highestStudent =s;
            }
        }
        averageScore =sum/students.size();
        students.sort((s1,s2) ->Integer.compare(s2.score,s1.score));
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        // TODO: запись результата в файл здесь
        try(BufferedWriter bf = new BufferedWriter(new FileWriter("output/report.txt"))){
            bf.write("Average: " + averageScore);
            bf.newLine();
            if(highestStudent != null){
                bf.write("Highes: " + highestStudent.name + " - " + highestStudent.score);
                bf.newLine();
            }
            bf.write("--- Sorted List ---");
            bf.newLine();
            for (Student s : students) {
                bf.write(s.name + ": " + s.score);
                bf.newLine();
            }

        } catch (IOException e ){
            System.out.println("error: "+e.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

// class InvalidScoreException реализуйте меня
class InvalidScoreException extends Exception{
    public InvalidScoreException(String message) {
        super(message);
    }
}
 class Student implements Serializable{
    String name;
    int score;
    public Student(String name,int score){
        this.name = name;
        this.score=score;
    }
}