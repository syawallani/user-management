package axaamfs.usermanagement.dto;

public class WebResponse<T> {

    private T data;

    private String error;

    private PagingResponse paging;

    public WebResponse() {
    }

    public WebResponse(T data, String error, PagingResponse paging) {
        this.data = data;
        this.error = error;
        this.paging = paging;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public PagingResponse getPaging() {
        return paging;
    }

    public void setPaging(PagingResponse paging) {
        this.paging = paging;
    }
}
