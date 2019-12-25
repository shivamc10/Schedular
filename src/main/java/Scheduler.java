import java.util.*;

public class Scheduler {
    PriorityQueue<Epic> epics;
    List<Iteration> iterations;

    public Scheduler(PriorityQueue<Epic> epics, List<Iteration> iterations) {
        this.epics = epics;
        this.iterations = iterations;
    }

    public PriorityQueue<Epic> getEpics() {
        return epics;
    }

    public void setEpics(PriorityQueue<Epic> epics) {
        this.epics = epics;
    }

    public List<Iteration> getIterations() {
        return iterations;
    }

    public void setIterations(List<Iteration> iterations) {
        this.iterations = iterations;
    }
// This is assuming that java pass complex variable as reference and not copying but may be I am wrong.
// If not then need to think of different approach of two way communication so that the instances will be updated
// Also this will schedule one extra story to each iteration as we generally take one more for stretch.
// Also dependency of stories are not considered here for scheduling.

    public List<Iteration> schedule(){
        for(Iteration iter:iterations) {
            List<Story> stories=new ArrayList<>();
            for(Epic epic:epics){
                Story story = epic.getNextStory();
                if(story!=null && iter.remainingVelocity()>0){
                    story.setIteration(iter);
                    stories.add(story);
                    iter.setStories(new ArrayList<>(stories));
                }
                else break;
            }
        }

        return this.iterations;
    }
}
