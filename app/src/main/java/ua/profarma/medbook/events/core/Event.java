package ua.profarma.medbook.events.core;

public class Event {

    private int eventId;

    public static final int SET_PHONE_NUMBER_FAILED_STEP_1              = 102;
    public static final int EVENT_GET_USER_SUCCESS                      = 100;
    public static final int EVENT_LOCALIZATION_UPDATE                   = 101;

    public static final int EVENT_LOAD_REGISTRATION_LIST                = 200;
    public static final int EVENT_NEAREST_MEDICAL_INSTITUTIONS          = 201;
    public static final int EVENT_SELECTED_MEDICAL_INSTITUTION          = 202;
    public static final int EVENT_REGISTRATION_SUCCESS                  = 203;
    public static final int EVENT_REGISTRATION_UNSUCCESS                = 204;
    public static final int EVENT_LOGIN_SUCCESS                         = 205;
    public static final int EVENT_LOGIN_FAILURE                         = 206;
    public static final int EVENT_RESTORE                               = 207;
    public static final int EVENT_ERROR_GET_DEVICE                      = 208;

    public static final int EVENT_LOGOUT                                    = 300;
    public static final int EVENT_TERMS_AND_AGREEMENTS_OK                   = 301;
    public static final int EVENT_START_FEEDBACK_FRAGMENT                   = 303;
    public static final int EVENT_CLOSE_FEEDBACK_FRAGMENT                   = 304;
    public static final int EVENT_START_VERIFICATION_PHONE_FRAGMENT         = 305;
    public static final int EVENT_SET_PHONE_STEP_1                          = 306;
    public static final int EVENT_SET_PHONE_STEP_2                          = 307;
    public static final int EVENT_CLOSE_VERIFICATION_PHONE_STEPS            = 308;
    public static final int EVENT_END_QR_CODE                               = 309;
    public static final int EVENT_POINTS_AGREEMENTS_OK                      = 310;
    public static final int EVENT_AGREEMENTS_LOAD                           = 311;
    public static final int EVENT_AGREEMENTS_DONT_LOAD                      = 312;

    public static final int EVENT_MATERIALS_LOAD                            = 400;
    public static final int EVENT_MATERIAL_SELECTED_UPDATE                  = 401;
    public static final int EVENT_MATERIAL_TRANSLATE_LOAD                   = 402;
    public static final int EVENT_MATERIAL_DETAILS_FRAGMENT_START           = 403;
    public static final int EVENT_MATERIAL_FRAGMENT_CLOSE                   = 404;
    public static final int EVENT_MATERIAL_DESCRIPTION_FRAGMENT_START       = 405;
    public static final int EVENT_MATERIAL_DESCRIPTION_FRAGMENT_CLOSE       = 406;
    public static final int EVENT_MATERIAL_TEST_SINGLE_CHOICE               = 407;
    public static final int EVENT_MATERIAL_TEST_ACTIVITY_CLOSE              = 408;
    public static final int EVENT_MATERIAL_INFO_FRAGMENT_START              = 409;
    public static final int EVENT_MATERIAL_TEST_RESULTS_CHECK               = 410;
    public static final int EVENT_MATERIAL_PRESENTATION_RESULT_SUCCESS      = 411;
    public static final int EVENT_MATERIAL_PRESENTATION_RESULT_FAILURE      = 412;
    public static final int EVENT_USER_NOTIFICATION_REACTION                = 413;


    public static final int EVENT_LOAD_5_NOTIFICATIONS                      = 500;
    public static final int EVENT_LOAD_NOTIFICATIONS                        = 501;
    public static final int EVENT_DELETE_NOTIFICATION                       = 502;

    public static final int EVENT_LOAD_TRANSACTION_HISTORY                  = 600;
    public static final int EVENT_LOAD_FISHKA_CARDS                         = 601;
    public static final int EVENT_ADD_FISHKA_CARD                           = 602;
    public static final int EVENT_GET_SMS_EXCHANGE_POINTS                   = 603;
    public static final int EVENT_EXECUTE_TRANSACTION                       = 604;
    public static final int EVENT_UPDATE_KEY_LIKI_WIKI                      = 605;

    public static final int EVENT_MEMBERS_VISIT_LOAD                        = 700;
    public static final int EVENT_VISIT_LOAD                                = 701;
    public static final int EVENT_START_QR_CODE                             = 702;
    public static final int EVENT_UPDATE_QR_CODE                            = 703;
    public static final int EVENT_SCAN_QR_CODE                              = 704;
    public static final int EVENT_VISIT_STARTING                            = 705;
    public static final int EVENT_VISIT_STARTING_FAILED                     = 706;
    public static final int EVENT_USER_VISIT_QUESTIONNAIRE_MP_LOAD          = 707;
    public static final int EVENT_USER_VISIT_QUESTIONNAIRE_MP_UPDATE        = 708;
    public static final int EVENT_USER_VISIT_QUESTIONNAIRE_DOCTOR_LOAD      = 709;
    public static final int EVENT_LOAD_DASHBOARD_VISITS                     = 710;
    public static final int EVENT_DONT_DOWNLOAD_MP_ANKETA                   = 711;


    public static final int EVENT_NEWS_LOAD                                 = 800;
    public static final int EVENT_ICOD_LOAD                                 = 801;
    public static final int EVENT_UPDATE_STATE_EXPANDABLE_LIST              = 802;
    public static final int EVENT_ADD_ICOD                                  = 803;
    public static final int EVENT_REMOVE_ICOD                               = 804;
    public static final int EVENT_START_PROGRESS                            = 805;
    public static final int EVENT_DRUGS_LOAD                                = 806;
    public static final int EVENT_LOAD_IMAGE_CLINIC_CASE                    = 807;
    public static final int EVENT_LOAD_IMAGE_CLINIC_CASE_STOP               = 808;
    public static final int EVENT_LOAD_MY_CLINIC_CASES                      = 809;
    public static final int EVENT_LOAD_CLINIC_CASE                          = 810;
    public static final int EVENT_LIKE_UNLIKE                               = 811;
    public static final int EVENT_LOAD_COMMENTS                             = 812;



    public Event(int eventId) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return eventId;
    }
}
