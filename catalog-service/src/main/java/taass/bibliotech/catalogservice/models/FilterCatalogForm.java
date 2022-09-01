package taass.bibliotech.catalogservice.models;

import java.util.List;

public class FilterCatalogForm {
    private List<String> category;
    private String sort;
    private Integer page;
    private String search;

    public List<String> getCategory() {
        return category;
    }


    public String getSort() {
        return sort;
    }

    public Integer getPage() {
        return page;
    }

    public String getSearch() {
        return search;
    }

    @Override
    public String toString() {
        return "FilterCatalogForm{" +
                ", category=" + category +
                ", sort='" + sort + '\'' +
                ", page=" + page +
                ", search='" + search + '\'' +
                '}';
    }
}
