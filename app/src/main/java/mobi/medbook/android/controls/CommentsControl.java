package mobi.medbook.android.controls;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.api.ApiRest;
import mobi.medbook.android.api.MCall;
import mobi.medbook.android.events.EventLogout;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.news.EventLoadComments;
import mobi.medbook.android.types.requests.NewCommentRequest;
import mobi.medbook.android.types.responses.DeleteCommentResponse;
import mobi.medbook.android.types.responses.NewCommentResponse;
import mobi.medbook.android.types.responses.UserNewsResponse;
import mobi.medbook.android.utils.AppUtils;
import retrofit2.Response;

public class CommentsControl {

    public void getComments(int news_article_id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().getComments(news_article_id,
                        "newsArticle,newsArticle.comments,newsArticle.comments.created_at,newsArticle.comments.owner_firstname,newsArticle.comments.owner_middlename")
                        .enqueue(new MCall<UserNewsResponse>() {
                            @Override
                            public void onSuccess(Response<UserNewsResponse> response) {
                                if (response != null && response.body() != null && response.body().items != null
                                        && response.body().items.length > 0 && response.body().items[0] != null)
                                    EventRouter.send(new EventLoadComments(response.body().items[0].newsArticle.comments));
                            }
                        });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void newComment(NewCommentRequest body) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().newComment(body)
                        .enqueue(new MCall<NewCommentResponse>() {
                            @Override
                            public void onSuccess(Response<NewCommentResponse> response) {
                                Core.get().CommentsControl().getComments(body.entityId);
                            }
                        });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());

    }

    public void deleteComments(int id, int news_article_id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().deleteComment(id).enqueue(new MCall<DeleteCommentResponse>() {
                    @Override
                    public void onSuccess(Response<DeleteCommentResponse> response) {
                        getComments(news_article_id);
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

}
