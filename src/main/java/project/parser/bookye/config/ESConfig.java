package project.parser.bookye.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ConfigurationProperties("es")
public class ESConfig {
    private String host;
    private String port;
    private String books;
    private String categories;
    private String version;
    private boolean deleteIndexes;
    private String categoryIndexName;
    private String bookIndexName;

    @PostConstruct
    public void initIndexesName() {
        categoryIndexName = categories + "-v" + version;
        bookIndexName = books + "-v" + version;
    }

    public String getCategoryIndexName() {
        return categoryIndexName;
    }

    public String getBookIndexName() {
        return bookIndexName;
    }

    public boolean isDeleteIndexes() {
        return deleteIndexes;
    }

    public void setDeleteIndexes(boolean deleteIndexes) {
        this.deleteIndexes = deleteIndexes;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setBooks(String books) {
        this.books = books;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getBooks() {
        return books;
    }

    public String getCategories() {
        return categories;
    }

    public String getVersion() {
        return version;
    }
}

