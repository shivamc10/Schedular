import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Map;

public class Crawler {
    String confluenceLink;

    public Crawler(String confluenceLink) {
        this.confluenceLink = confluenceLink;
    }

    public static Document loginVerification(String loginUrl,String validation_url, String required_url) throws IOException {

        String username = "shivam.choubey";
        String password = "XXXXXXXXXXXXXX";
        Connection.Response loginForm = Jsoup.connect(loginUrl)
                .method(Connection.Method.GET)
                .execute();

        Connection.Response mainPage = Jsoup.connect(validation_url)
                .data("os_username", username)
                .data("os_password", password)
                .cookies(loginForm.cookies())
                .execute();

        Map<String, String> cookies = mainPage.cookies();

        Document evaluationPage = Jsoup.connect(required_url)
                .cookies(cookies).get();

        return evaluationPage;
    }

    public Document getDocument(){
        Document doc = new Document("");
        try{
            String loginUrl="";
            String loginValidation = "";
            doc = loginVerification(loginUrl,loginValidation,this.confluenceLink);
        }
        catch(Exception e){
            System.out.println("Error in fetching given URL- "+e);
        }
        return doc;
    }


}
