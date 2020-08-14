package project.parser.bookye.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.parser.bookye.client.ESClient;
import project.parser.bookye.config.ESConfig;
import project.parser.bookye.model.Category;

import java.io.IOException;
import java.util.*;

@Service
public class CategoryService {

    public static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    ESConfig esConfig;

    @Autowired
    ESClient esClient;

    public void saveCategories(Category categoryParent) throws IOException {
        log.info("Saving categories");
        Queue<Category> categoryQueue = new LinkedList<>();
        categoryQueue.addAll(categoryParent.getChildren());
        while (!categoryQueue.isEmpty()) {
            Category category = categoryQueue.poll();
            if (category.getChildren() != null) {
                categoryQueue.addAll(category.getChildren());
            }
            setCatLevels(category);
            esClient.saveCategory(category);
        }
    }

    public String getTransliteration(Category category) {
        String link = category.getLink();
        int begin = link.indexOf('/', 1) + 1;
        return link.substring(begin, link.length() - 1);
    }

    public void setCatLevels(Category category) {
        Category parent = category.getParent();
        category.setCatLevel1(parent.getCatLevel1());
        category.setCatLevel2(parent.getCatLevel2());
        if (category.getLevel() == 1) {
            category.setCatLevel1(getTransliteration(category));
            category.setId(category.getCatLevel1());
        } else if (category.getLevel() == 2) {
            String catLevel2 = category.getCatLevel1() + "::" + getTransliteration(category);
            category.setCatLevel2(catLevel2);
            category.setId(catLevel2);
        } else {
            String catLevel3 = category.getCatLevel2() + "::" + getTransliteration(category);
            category.setCatLevel3(catLevel3);
            category.setId(catLevel3);
        }
    }
}
