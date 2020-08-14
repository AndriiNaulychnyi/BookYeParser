package project.parser.bookye.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.parser.bookye.client.ESClient;
import project.parser.bookye.config.ESConfig;
import project.parser.bookye.model.Category;
import project.parser.bookye.parser.BookParser;
import project.parser.bookye.parser.CategoryParser;

import java.io.IOException;

@Component
public class MainService {
    @Autowired
    CategoryParser categoryParser;
    @Autowired
    ESClient printToDB;
    @Autowired
    BookParser bookParser;
    @Autowired
    ESClient esClient;
    @Autowired
    ESConfig esConfig;

    public void parser() throws IOException {
       if (esConfig.isDeleteIndexes()) {
            esClient.deleteIndexes(esConfig.getCategoryIndexName());
            esClient.deleteIndexes(esConfig.getBookIndexName());
        }
        esClient.createIndex(esConfig.getCategoryIndexName());
        esClient.createIndex(esConfig.getBookIndexName());
        Category category = categoryParser.getCategoryFirstLevel();
        bookParser.bookParse(category);
    }
}
