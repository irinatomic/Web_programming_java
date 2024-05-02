import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Student implements Runnable {

    private long arrivalTime;        // millis
    private long startTime;          // millis
    private int defenseDuration;     // millis
    private int thesisSupervisor;    // 0 - professor, 1 - assistant
    private int grade;

    private long programStartTime;
    private long studentThreadId;

    public Student(long startTime) {
        Random random = new Random();

        this.arrivalTime = startTime + random.nextInt(1001) + 1;       // (0, 1000]
        this.defenseDuration = random.nextInt(501) + 500;              // [500, 1000]
        this.thesisSupervisor = random.nextInt(2);

        this.programStartTime = startTime;
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() < arrivalTime) {
            // Wait until arrival time
        }

        this.studentThreadId = Thread.currentThread().getId();
        if (thesisSupervisor == 0) {
            defendProfessor();
        } else {
            defendAssistant();
        }
    }

    private void defendProfessor() {
        try {

            Main.getProfSemaphore().acquire();              // so we block access to the prof thread for other awaiting students
            Main.getProfBarrier().await();                  // so we block access to the prof thread for other awaiting students
            startTime = System.currentTimeMillis();

            Thread.sleep(this.defenseDuration);             // grading
            grade();

            Main.getProfSemaphore().release();

        } catch (InterruptedException | BrokenBarrierException e) {

            System.out.println("Student thread " + studentThreadId + " was interrupted in grading");
        }
    }

    private void defendAssistant() {
        try {
            Main.getAssistantLock().lockInterruptibly();
            startTime = System.currentTimeMillis();
            System.out.println("Assistant grading");

            Thread.sleep(this.defenseDuration);
            grade();

            Main.getAssistantLock().unlock();

        } catch (InterruptedException e) {
            System.out.println("Student thread " + studentThreadId + " was interrupted in grading");
        }
    }

    private void grade() {
        Random random = new Random();
        this.grade = random.nextInt(6) + 5;      // [5, 10]
    }

    @Override
    public String toString() {

        return "Thread: " + studentThreadId +
                " Arrival: " + (arrivalTime - programStartTime) +
                " Prof: " + (thesisSupervisor == 0 ? "professor" : "assistant") +
                " TTC: " + defenseDuration + ":" + (startTime > 0 ? (startTime - programStartTime) : "-") +
                " Score: " + grade;
    }

    public int getGrade() {
        return grade;
    }
}
