package project.parser.bookye.service;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.parser.bookye.client.ESClient;
import project.parser.bookye.config.ESConfig;
import project.parser.bookye.model.Book;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private ESClient esClient;
    @Autowired
    private ESConfig esConfig;

    public void saveBooks(List<Book> bookList) throws IOException {
        Queue<Book> bookQueue = new LinkedList<>();
        bookQueue.addAll(bookList);
        Queue<IndexRequest> indexRequestList = new LinkedList<>();
        int i = 0;
        while (!bookQueue.isEmpty()) {
            Book book = bookQueue.poll();
            IndexRequest request = new IndexRequest(esConfig.getBookIndexName());
            request.id(book.getId());
            String json = Utils.objectMapper().writeValueAsString(book);
            request.source(json, XContentType.JSON);
            indexRequestList.add(request);
            i++;
            if (i == 500) {
                esClient.saveBooks(indexRequestList);
                indexRequestList.clear();
                i = 0;
            }
        }
        if (!indexRequestList.isEmpty()) {
            esClient.saveBooks(indexRequestList);
        }
    }
}
