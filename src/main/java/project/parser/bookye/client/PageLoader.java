package project.parser.bookye.client;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PageLoader {

    private static final Logger log = LoggerFactory.getLogger(PageLoader.class);

    public Document getPage(String link) {
        Document doc = null;
        for (int j = 0; j < 3; j++) {
            try {
                doc = Jsoup.connect(link).get();
                break;
            } catch (IOException e) {
                log.error("Error during loading page {}", link);
                if (j == 2) {
                    log.error("Cannot load page: {}", link);
                }
            }
        }
        return doc;
    }
}
