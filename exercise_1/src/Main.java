import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Main {

    private static long startTime;
    private static int n = 20;

    private static Lock assistantLock = new ReentrantLock();
    private static Semaphore profSemaphore = new Semaphore(2);
    private static CyclicBarrier profBarrier = new CyclicBarrier(2, () -> System.out.println("Professor grading"));

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(n);

        startTime = System.currentTimeMillis();

        List<Student> students = new ArrayList<>(n);
        IntStream.range(0, n).forEach(i -> students.add(new Student(startTime)));

        // execute all students
        students.forEach(executor::execute);

        end(executor);

        // print average grade
        int gradeSum = students.stream().mapToInt(Student::getGrade).sum();
        int gradedNo = (int) students.stream().filter(s -> s.getGrade() != 0).count();
        System.out.println("\nAverage grade: " + (double) gradeSum / gradedNo);

        // print all students
        students.forEach(System.out::println);
    }

    private static void end(ExecutorService executor) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.schedule(() -> {
            executor.shutdownNow();
            scheduler.shutdownNow();
        }, 5, TimeUnit.SECONDS);


        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            executor.shutdownNow();
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static Semaphore getProfSemaphore() {
        return profSemaphore;
    }

    public static CyclicBarrier getProfBarrier() {
        return profBarrier;
    }

    public static Lock getAssistantLock() {
        return assistantLock;
    }

}
