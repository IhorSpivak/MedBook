package ua.profarma.medbook.controls;

import retrofit2.Response;
import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.api.ApiRest;
import ua.profarma.medbook.api.MCall;
import ua.profarma.medbook.events.EventLogout;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.events.news.EventLoadComments;
import ua.profarma.medbook.types.requests.NewCommentRequest;
import ua.profarma.medbook.types.responses.DeleteCommentResponse;
import ua.profarma.medbook.types.responses.MedicalCaseBodyDeleteResponse;
import ua.profarma.medbook.types.responses.NewCommentResponse;
import ua.profarma.medbook.types.responses.UserNewsResponse;
import ua.profarma.medbook.utils.AppUtils;

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
