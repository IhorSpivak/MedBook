package mobi.medbook.android.api;

import mobi.medbook.android.utils.LogUtils;
import retrofit2.Response;


public class APIHelper {
    public static PaginationData getPaginationData(Response response) {
        int currentPage = 1;
        int totalPages = 1;
        try {
        if(response != null && response.headers() != null && response.headers().size() > 0
                && response.headers().get("X-Pagination-Page-Count") != null
                && Integer.valueOf(response.headers().get("X-Pagination-Page-Count")) > 1){
            if (response.headers().get("X-Pagination-Current-Page") != null){
                currentPage = Integer.valueOf(response.headers().get("X-Pagination-Current-Page"));
            }

            }
            if (response.headers().get("X-Pagination-Page-Count") != null){
                totalPages = Integer.valueOf(response.headers().get("X-Pagination-Page-Count"));
        }
        } catch (NullPointerException e) {
            // We don't care about header items
            // with empty names, so just skip over
            // them.
            LogUtils.logW("APIHelper -> getPaginationData", "Skipped over header: " + e.getLocalizedMessage());
        }
        return new PaginationData(currentPage, totalPages);
    }

    public static class PaginationData {
        public final int page;
        public final int total;

        public PaginationData(int currentPage, int totalPages) {
            this.page = currentPage;
            this.total = totalPages;
        }
    }
}
