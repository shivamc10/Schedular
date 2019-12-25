import java.util.*;
public class Story {
    private String name;
    private int point;
    private String epic_link;
    private String description;
    private Iteration iteration=null;
    private List<Story> dependency=null;

//  List of dependency is nice to have as it will make the story scheduling more realistic, but for now we are
//  scheduling one story of each epic priority wise in one iteration so that the dependency will be not that
//  important.

    public Story(){}

    public Story(String name, int point, String epic_link, String description, List<Story> dependency) {
        this.name = name;
        this.point = point;
        this.epic_link = epic_link;
        this.description = description;
        this.dependency = dependency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getEpic_link() {
        return epic_link;
    }

    public void setEpic_link(String epic_link) {
        this.epic_link = epic_link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Iteration getIteration() {
        return iteration;
    }

    public void setIteration(Iteration iteration) {
        this.iteration = iteration;
    }

    public List<Story> getDependency() {
        return dependency;
    }

    public void setDependency(List<Story> dependency) {
        this.dependency = dependency;
    }

    public boolean dependencyFulfilled(){
        for(Story story:this.dependency)
            if(story.iteration==null)return false;
        return true;
    }
}
