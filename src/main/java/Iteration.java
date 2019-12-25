import java.time.LocalDate;
import java.util.*;

public class Iteration {
    private List<Story> stories;
    private int iteration_number;
    private int velocity;
    private LocalDate start;
    private LocalDate end;

    public Iteration(){}

    public Iteration(int iteration_number, int velocity,LocalDate start,LocalDate end) {
        this.iteration_number = iteration_number;
        this.velocity = velocity;
        this.start=start;
        this.end=end;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public int getIteration_number() {
        return iteration_number;
    }

    public void setIteration_number(int iteration_number) {
        this.iteration_number = iteration_number;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public int remainingVelocity(){
        int pointCount =0;
        for(Story story:this.stories)
            pointCount+=story.getPoint();
        return this.velocity-pointCount;
    }
}
