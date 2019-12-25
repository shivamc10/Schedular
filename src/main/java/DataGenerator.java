import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.*;
class CompareEpics implements Comparator<Epic>{
    public int compare(Epic e1, Epic e2){
        if(e1.getPriority()>e2.getPriority()) return 1;
        else if(e1.getPriority()<e2.getPriority()) return -1;
        return 0;
    }
}
public class DataGenerator {
    File file;
    String confluenceLink;
    PriorityQueue<Epic> epics= new PriorityQueue<>(new CompareEpics());
    List<Iteration> iterations;
    public DataGenerator(String confluenceLink) {
        this.confluenceLink=confluenceLink;
    }

    public String getConfluenceLink() {
        return confluenceLink;
    }

    public void setConfluenceLink(String confluenceLink) {
        this.confluenceLink = confluenceLink;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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
//    Crawling is tough but this will make the overall process more effective and easy to use as the program
//    will take care of getting data from confluence page where user make notes of stories and epics.
//    To make use of program effectively, user has to follow certain guidelines while making those pages.

    public void createData() throws IOException {
//        Document doc = new Crawler(this.confluenceLink).getDocument();
        File file = new File("/Users/shivamchoubey/Desktop/Personal/Schedular/src/main/resources/MEISHL FY20.Q3 Planning Notes - Technology -.html");
        Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
        Element content = doc.getElementById("main-content");
        Elements links = content .getElementsByTag("p");
        for (Element link : links){
            String linkText = link.text();

        }

    }
}
