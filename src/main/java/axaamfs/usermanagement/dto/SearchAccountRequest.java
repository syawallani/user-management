package axaamfs.usermanagement.dto;

import jakarta.validation.constraints.NotNull;

public class SearchAccountRequest {

    private String filter;

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

    public SearchAccountRequest() {
    }

    public SearchAccountRequest(String filter, Integer page, Integer size) {
        this.filter = filter;
        this.page = page;
        this.size = size;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
