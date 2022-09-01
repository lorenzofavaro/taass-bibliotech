package taass.bibliotech.catalogservice.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductForm {

    private long id;
    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String description;
    @NotBlank
    private String picture;

    private int stock;
    private List<String> categories;

    public String getTitle() {
        return title;
    }

    public String getAuthor() { return author; }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public int getStock() {
        return stock;
    }

    public List<String> getCategories() {
        return categories;
    }

    public long getId() { return id; }
}

