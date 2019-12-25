import java.util.*;

public class Epic {
    private List<Story> stories;
    private int priority;
    private String name;
    private String description;
    private String feature_link;

    public Epic(){}

    public Epic(List<Story> stories, int priority, String name, String description, String feature_link) {
        this.stories = stories;
        this.priority = priority;
        this.name = name;
        this.description = description;
        this.feature_link = feature_link;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeature_link() {
        return feature_link;
    }

    public void setFeature_link(String feature_link) {
        this.feature_link = feature_link;
    }

    public Story getNextStory(){
        for(Story story:stories)
            if(story.getIteration()!=null) return story;
        return null;
    }
}
