package project.parser.bookye.parser;

import org.slf4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.parser.bookye.client.PageLoader;
import project.parser.bookye.model.Book;
import project.parser.bookye.model.Category;
import project.parser.bookye.service.BookService;
import project.parser.bookye.service.Utils;

import java.io.IOException;
import java.util.*;

@Component
public class BookParser {

    private static final Logger log = LoggerFactory.getLogger(BookParser.class);

    @Autowired
    private PageLoader pageLoader;

    @Autowired
    private BookService bookService;

    public void bookParse(Category categoryParent) throws IOException {
        Queue<Category> categoryQueue = new LinkedList<>();
        categoryQueue.addAll(categoryParent.getChildren());
        while (!categoryQueue.isEmpty()) {
            Category category = categoryQueue.poll();
            if (!category.getChildren().isEmpty()) {
                categoryQueue.addAll(category.getChildren());
            } else {
                parsePageBooks(category);
            }
        }
    }

    public void parsePageBooks(Category category) throws IOException {
        int pageMax = getPagesQuantity(category);
        System.out.println(pageMax);
        Queue<String> booksHref = new LinkedList<>();
        for (int i = 1; i <= pageMax; i++) {
            String link = "https://book-ye.com.ua" + category.getLink() + "?PAGEN_1=" + i;
            List<String> booksLink = getBooksOnPage(link);
            if (!booksLink.isEmpty()) {
                booksHref.addAll(booksLink);
            }
        }
        List<Book> bookList = getBooks(booksHref, category);
        bookService.saveBooks(bookList);
    }

    public List<String> getBooksOnPage(String link) {
        Document doc = pageLoader.getPage(link);
        List<String> booksHref = new LinkedList<>();
        if (doc != null) {
            Elements booksHTML = doc.select("a.product__name");
            booksHTML.forEach(element -> {
                StringBuilder href = new StringBuilder("https://book-ye.com.ua");
                href.append(element.attr("href"));
                booksHref.add(href.toString());
            });
        } else {
            return null;
        }
        return booksHref;
    }

    public int getPagesQuantity(Category category){
        String link = "https://book-ye.com.ua" + category.getLink();
        Document doc = pageLoader.getPage(link);
        Elements pagination = doc.select(".pagination");
        Elements elements = pagination.first().select("[class='pagination__item']");
        String pageMax = "1";
        if (!elements.isEmpty()) {
            pageMax = elements.last().text();
        }
        int page = Integer.parseInt(pageMax);
        return page;
    }

    public List<Book> getBooks(Queue<String> booksHref, Category category){
        log.info("Processing books from category: {}", category.getId());
        List<Book> books = new LinkedList<>();
        Document doc;
        int size = booksHref.size();
        int i = 1;
        while (!booksHref.isEmpty()) {
            String bookLink = booksHref.poll();
            log.debug("Getting book ({} / {}) from {}", i, size, bookLink);
            doc = pageLoader.getPage(bookLink);
            Book book = parsBook(doc, category);
            if (book != null) {
                books.add(book);
                i++;
            }
        }
        return books;
    }

    public Book parsBook(Document doc, Category category) {
        Book book = new Book();
        Elements el = doc.select("[class='card__content card__content--primary']");
        String name = el.select(".card__title").text();
        String id = getBookId(el);
        if (id == null) {
            return null;
        }
        Map<String, String> bookInfo = getBookMap(el);
        book = Utils.objectMapper().convertValue(bookInfo, book.getClass());
        List<String> photo = getListPhotos(doc);
        String annotation = doc.select("[class='article__description content__txt article__annotation']").text();
        book.setName(name);
        book.setId(id);
        book.setPhoto(photo);
        book.setAnnotation(annotation);
        book.setCatLevel1(category.getCatLevel1());
        book.setCatLevel2(category.getCatLevel2());
        book.setCatLevel3(category.getCatLevel3());
        return book;
    }

    public String getBookId(Elements el) {
        String cardCode = el.select(".card__code").text();
        if (cardCode.isEmpty()) {
            log.error("Cannot get book id");
            return null;
        }
        int begin = cardCode.indexOf(':', 1) + 2;
        String id;
        if (begin < cardCode.length()) {
            id = cardCode.substring(begin);
        } else {
            return null;
        }
        return id;
    }

    public Map<String, String> getBookMap(Elements el) {
        Map<String, String> bookInfo = new HashMap<>();
        Elements elements = el.select(".card__info");
        elements.forEach(e -> {
            String s = e.text();
            int center = s.indexOf(':', 1);
            String key = s.substring(0, center);
            String value = null;
            if (center + 2 < s.length()) {
                value = s.substring(center + 2);
            }
            bookInfo.put(key, value);
        });
        return bookInfo;
    }

    public List<String> getListPhotos(Document doc) {
        List<String> photoUrl = new ArrayList<>();
        Elements elements = doc.select(".card__preview-wrap img");
        elements.forEach(e -> {
            String url = "https://book-ye.com.ua" + e.attr("src");
            photoUrl.add(url);
        });
        return photoUrl;
    }
}
