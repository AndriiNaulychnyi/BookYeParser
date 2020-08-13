package project.parser.bookye.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class Book {
    private String id;
    private String name;
    private List<String> photo;
    private String annotation;
    private String catLevel1;
    private String catLevel2;
    private String catLevel3;

    @JsonAlias("Автор")
    private String author;

    @JsonAlias("Видавництво")
    private String publisher;

    @JsonAlias("Рік видання")
    private String imprintDate;

    @JsonAlias("Мова")
    private String language;

    @JsonAlias("Обкладинка")
    private String bookCover;

    @JsonAlias("Кількість сторінок")
    private String pageNumber;

    @JsonAlias("Формат")
    private String format;

    @JsonAlias("ISBN")
    private String isbn;

    @JsonAlias("Вага")
    private String weight;

    @JsonAlias("Наявність ілюстрацій")
    private String availabilityFigure;

    @JsonAlias("Серія книг")
    private String booksSeries;

    @JsonAlias("Перекладна")
    private String translated;

    @JsonAlias("Перекладач")
    private String translator;

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

    public String getTranslated() {
        return translated;
    }

    public void setTranslated(String translated) {
        this.translated = translated;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getPhoto() {
        return photo;
    }

    public void setPhoto(List<String> photo) {
        this.photo = photo;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImprintDate() {
        return imprintDate;
    }

    public void setImprintDate(String imprintDate) {
        this.imprintDate = imprintDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAvailabilityFigure() {
        return availabilityFigure;
    }

    public void setAvailabilityFigure(String availabilityFigure) {
        this.availabilityFigure = availabilityFigure;
    }

    public String getBooksSeries() {
        return booksSeries;
    }

    public void setBooksSeries(String booksSeries) {
        this.booksSeries = booksSeries;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }
}