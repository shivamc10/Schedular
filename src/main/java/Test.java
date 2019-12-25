import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Test {
    public static Document loginVerification(String loginUrl,String validation_url, String required_url) throws IOException{
        String USER_AGENT = "\"Mozilla/5.0 (Windows NT\" +\n" +
                "          \" 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2\"";
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
    public static void main(String[] args) throws Exception {




        File file = new File("/Users/shivamchoubey/Desktop/Personal/Schedular/src/main/resources/MEISHL FY20.Q3 Planning Notes - Technology -.html");
        Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
        Element content = doc.getElementById("main-content");
        Elements links = content.getElementsByTag("p");

        for (Element link : links) {
            String linkText = link.text();
            System.out.println(linkText);
        }

    }

}
