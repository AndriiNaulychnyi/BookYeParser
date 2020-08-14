package project.parser.bookye.client;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.parser.bookye.config.ESConfig;
import project.parser.bookye.model.Category;
import project.parser.bookye.service.Utils;

import java.io.IOException;
import java.util.Queue;

@Component
public class ESClient {

    @Autowired
    private ESConfig esConfig;

    @Autowired
    private RestHighLevelClient client;

    public void createIndex(String name) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(name);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    public void deleteIndexes(String name) throws IOException {
        DeleteIndexRequest deleteIndex = new DeleteIndexRequest(name);
        client.indices().delete(deleteIndex, RequestOptions.DEFAULT);
    }

    public void saveCategory(Category category) throws IOException {
        String index = esConfig.getCategoryIndexName();
        IndexRequest request = new IndexRequest(index);
        request.id(category.getId());
        String json = Utils.objectMapper().writeValueAsString(category);
        request.source(json, XContentType.JSON);
        client.index(request,RequestOptions.DEFAULT);
    }

    public void saveBooks(Queue<IndexRequest> indexRequestQueue) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        while (!indexRequestQueue.isEmpty()) {
            IndexRequest indexRequest = indexRequestQueue.poll();
            bulkRequest.add(indexRequest);
        }
        client.bulk(bulkRequest, RequestOptions.DEFAULT);
    }
}
