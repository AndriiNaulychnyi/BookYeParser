package project.parser.bookye.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public class Category {
    private String id;
    private String name;
    private int level;
    private String link;
    @JsonIgnore
    private Category parent;
    private String catLevel1;
    private String catLevel2;
    private String catLevel3;
    @JsonIgnore
    private List<Category> children;

    public String getCatLevel1() {
        return catLevel1;
    }

    public void setCatLevel1(String catLevel1) {
        this.catLevel1 = catLevel1;
    }

    public String getCatLevel2() {
        return catLevel2;
    }

    public void setCatLevel2(String catLevel2) {
        this.catLevel2 = catLevel2;
    }

    public String getCatLevel3() {
        return catLevel3;
    }

    public void setCatLevel3(String catLevel3) {
        this.catLevel3 = catLevel3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }
}