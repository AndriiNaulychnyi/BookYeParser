package project.parser.bookye.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.parser.bookye.config.ESConfig;
import project.parser.bookye.model.Category;
import project.parser.bookye.service.CategoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryParser {

    private static final Logger log = LoggerFactory.getLogger(CategoryParser.class);

    @Autowired
    public ESConfig esConfig;
    @Autowired
    public CategoryService categoryService;

    public Category getCategoryFirstLevel() throws IOException {
        Category parent = new Category();
        List<Category> categoryList = new ArrayList<>();
        Document doc = Jsoup.connect("https://book-ye.com.ua/").get();
        Elements categoryFirstLevel = doc.select(".ctlg-left__link");
        categoryFirstLevel.forEach(e -> {
            Category category = new Category();
            String href = e.attr("href");
            String nameCategory = e.text();
            String catCase = e.attr("data-section");
            category.setName(nameCategory);
            category.setLink(href);
            category.setParent(parent);
            category.setLevel(1);
            category.setChildren(getCategorySecondLevel(doc, category, catCase));
            categoryList.add(category);
        });
        parent.setChildren(categoryList);
        categoryService.saveCategories(parent);
        return parent;
    }

    public List<Category> getCategorySecondLevel(Document doc, Category parentCategory, String catCase) {
        log.info("CategoryFirstLevel: {}", parentCategory.getName());
        List<Category> categoryList = new ArrayList<>();
        String cssQuery = "#section" + catCase + " li[class='ctlg-right__item']";
        Elements categorySecondLevel = doc.select(cssQuery);
        if (!categorySecondLevel.isEmpty()) {
            categorySecondLevel.forEach(e -> {
                Element element = e.selectFirst("a[class$=--with-dropdown]");
                Category category = new Category();
                String href = element.attr("href");
                String nameCategory = element.text();
                category.setName(nameCategory);
                category.setLink(href);
                category.setParent(parentCategory);
                category.setLevel(2);
                category.setChildren(getCategoryThirdLevel(e, category));
                categoryList.add(category);
            });
        }
        return categoryList;
    }

    private List<Category> getCategoryThirdLevel(Element doc, Category parentCategory) {
        log.info("CategorySecondLevel: {}", parentCategory.getName());
        List<Category> categoryList = new ArrayList<>();
        Elements categoryThirdLevel = doc.select(".ctlg-right__third-link");
        if (!categoryThirdLevel.isEmpty()) {
            categoryThirdLevel.forEach(element -> {
                Category category = new Category();
                String href = element.attr("href");
                String nameCategory = element.text();
                category.setName(nameCategory);
                category.setLink(href);
                category.setParent(parentCategory);
                category.setLevel(3);
                category.setChildren(new ArrayList<>());
                categoryList.add(category);
                log.info("CategoryThirdLevel: {}", category.getName());
            });
        }
        return categoryList;
    }
}
