package mobi.medbook.android;

import mobi.medbook.android.controls.AuthorizationControl;
import mobi.medbook.android.controls.CommentsControl;
import mobi.medbook.android.controls.DataForRegistrationControl;
import mobi.medbook.android.controls.DeviceControl;
import mobi.medbook.android.controls.LocalizationControl;
import mobi.medbook.android.controls.NewsControl;
import mobi.medbook.android.controls.NotificationControl;
import mobi.medbook.android.controls.PharmaApiControl;
import mobi.medbook.android.controls.PointsControl;
import mobi.medbook.android.controls.UserContentControl;
import mobi.medbook.android.controls.UserControl;
import mobi.medbook.android.controls.VisitsControl;





public class Core {
    private static Core core = null;

    private DataForRegistrationControl  dataForRegistrationControl;
    private AuthorizationControl authorizationControl;
    private UserControl                 userControl;
    private PharmaApiControl            api2Control;
    private UserContentControl userContentControl;
    private DeviceControl deviceControl;
    private LocalizationControl localizationControl;
    private NotificationControl         notificationControl;
    private PointsControl pointControl;
    private VisitsControl visitsControl;
    private NewsControl                 newsControl;
    private CommentsControl             commentsControl;

    public static Core get() {
        if (core == null) {
            core = new Core();
        }
        return core;
    }


    private Core() {
        dataForRegistrationControl  = new DataForRegistrationControl();
        authorizationControl        = new AuthorizationControl();
        userControl                 = new UserControl();
        api2Control                 = new PharmaApiControl();
        userContentControl          = new UserContentControl();
        deviceControl               = new DeviceControl();
        localizationControl         = new LocalizationControl();
        notificationControl         = new NotificationControl();
        pointControl                = new PointsControl();
        visitsControl               = new VisitsControl();
        newsControl                 = new NewsControl();
        commentsControl             = new CommentsControl();
    }

    public DataForRegistrationControl getDataForRegistrationControl() {
        return dataForRegistrationControl;
    }

    public AuthorizationControl AuthorizationControl() {
        return authorizationControl;
    }

    public UserControl UserControl() {
        return userControl;
    }

    public PharmaApiControl Api2Control() {
        return api2Control;
    }

    public UserContentControl UserContentControl() {
        return userContentControl;
    }

    public DeviceControl DeviceControl() {
        return deviceControl;
    }

    public LocalizationControl LocalizationControl() {
        return localizationControl;
    }

    public NotificationControl NotificationControl() {
        return notificationControl;
    }

    public PointsControl PointControl() {
        return pointControl;
    }

    public VisitsControl VisitsControl() {
        return visitsControl;
    }

    public NewsControl NewsControl() {
        return newsControl;
    }

    public CommentsControl CommentsControl() {
        return commentsControl;
    }
}
