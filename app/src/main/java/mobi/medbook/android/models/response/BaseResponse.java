package mobi.medbook.android.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BaseResponse<T>{

    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("errors")
    @Expose
    private ErrorsItem errors;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ErrorsItem getErrors() {
        return errors;
    }

    public void setErrors(ErrorsItem errors) {
        this.errors = errors;
    }

    @SerializedName("items")
    @Expose
    private T data = null;



    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private class ErrorsItem {
        @SerializedName("code")
        @Expose
        private Integer code;

        @SerializedName("message")
        @Expose
        private String message;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
