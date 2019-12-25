import java.io.IOException;
import java.util.*;
public class Runner {
    public static void main(String args[]) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter link to the confluence page - ");
        String confluenceLink = scan.nextLine();
        DataGenerator dataGenerator = new DataGenerator(confluenceLink);
        try {
            dataGenerator.createData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scheduler scheduler = new Scheduler(dataGenerator.epics,dataGenerator.iterations);
        List<Iteration> iterations = scheduler.schedule();

    }
}
