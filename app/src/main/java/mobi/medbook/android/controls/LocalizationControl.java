package mobi.medbook.android.controls;

import android.util.SparseArray;

import mobi.medbook.android.App;
import mobi.medbook.android.R;
import mobi.medbook.android.localization.RussianLang;
import mobi.medbook.android.localization.UkrainianLang;


public class LocalizationControl {
    private SparseArray<String> rusLocale = new SparseArray<>();
    private SparseArray<String> uaLocale = new SparseArray<>();

    public LocalizationControl() {

        //CalendarFragment
        rusLocale.put(R.id.calendar_fragment_no_exist_unaccepted_visits, RussianLang.CalendarFragment.MESSAGE_NO_EXIST_UNACCEPTED_VISITS);

        uaLocale.put(R.id.calendar_fragment_no_exist_unaccepted_visits, UkrainianLang.CalendarFragment.MESSAGE_NO_EXIST_UNACCEPTED_VISITS);


        //CALENDAR
        rusLocale.put(R.id.calendar_january, RussianLang.Calendar.JANUARY);
        rusLocale.put(R.id.calendar_february, RussianLang.Calendar.FEBRUARY);
        rusLocale.put(R.id.calendar_march, RussianLang.Calendar.MARCH);
        rusLocale.put(R.id.calendar_april, RussianLang.Calendar.APRIL);
        rusLocale.put(R.id.calendar_may, RussianLang.Calendar.MAY);
        rusLocale.put(R.id.calendar_june, RussianLang.Calendar.JUNE);
        rusLocale.put(R.id.calendar_july, RussianLang.Calendar.JULY);
        rusLocale.put(R.id.calendar_august, RussianLang.Calendar.AUGUST);
        rusLocale.put(R.id.calendar_september, RussianLang.Calendar.SEPTEMBER);
        rusLocale.put(R.id.calendar_october, RussianLang.Calendar.OCTOBER);
        rusLocale.put(R.id.calendar_november, RussianLang.Calendar.NOVEMBER);
        rusLocale.put(R.id.calendar_december, RussianLang.Calendar.DECEMBER);
        rusLocale.put(R.id.calendar_january_s, RussianLang.Calendar.JANUARY_S);
        rusLocale.put(R.id.calendar_february_s, RussianLang.Calendar.FEBRUARY_S);
        rusLocale.put(R.id.calendar_march_s, RussianLang.Calendar.MARCH_S);
        rusLocale.put(R.id.calendar_april_s, RussianLang.Calendar.APRIL_S);
        rusLocale.put(R.id.calendar_may_s, RussianLang.Calendar.MAY_S);
        rusLocale.put(R.id.calendar_june_s, RussianLang.Calendar.JUNE_S);
        rusLocale.put(R.id.calendar_july_s, RussianLang.Calendar.JULY_S);
        rusLocale.put(R.id.calendar_august_s, RussianLang.Calendar.AUGUST_S);
        rusLocale.put(R.id.calendar_september_s, RussianLang.Calendar.SEPTEMBER_S);
        rusLocale.put(R.id.calendar_october_s, RussianLang.Calendar.OCTOBER_S);
        rusLocale.put(R.id.calendar_november_s, RussianLang.Calendar.NOVEMBER_S);
        rusLocale.put(R.id.calendar_december_s, RussianLang.Calendar.DECEMBER_S);
        rusLocale.put(R.id.calendar_local, RussianLang.Calendar.LOCAL);
        rusLocale.put(R.id.main_visits_calendar_mon, RussianLang.Calendar.MON);
        rusLocale.put(R.id.main_visits_calendar_tue, RussianLang.Calendar.TUE);
        rusLocale.put(R.id.main_visits_calendar_wed, RussianLang.Calendar.WED);
        rusLocale.put(R.id.main_visits_calendar_thu, RussianLang.Calendar.THU);
        rusLocale.put(R.id.main_visits_calendar_fri, RussianLang.Calendar.FRI);
        rusLocale.put(R.id.main_visits_calendar_sat, RussianLang.Calendar.SAT);
        rusLocale.put(R.id.main_visits_calendar_sun, RussianLang.Calendar.SUN);
        rusLocale.put(R.id.calendar_monday, RussianLang.Calendar.MONDAY);
        rusLocale.put(R.id.calendar_tuesday, RussianLang.Calendar.TUESDAY);
        rusLocale.put(R.id.calendar_wednesday, RussianLang.Calendar.WEDNESDAY);
        rusLocale.put(R.id.calendar_thursday, RussianLang.Calendar.THURSDAY);
        rusLocale.put(R.id.calendar_friday, RussianLang.Calendar.FRIDAY);
        rusLocale.put(R.id.calendar_saturday, RussianLang.Calendar.SATURDAY);
        rusLocale.put(R.id.calendar_sunday, RussianLang.Calendar.SUNDAY);


        uaLocale.put(R.id.calendar_january, UkrainianLang.Calendar.JANUARY);
        uaLocale.put(R.id.calendar_february, UkrainianLang.Calendar.FEBRUARY);
        uaLocale.put(R.id.calendar_march, UkrainianLang.Calendar.MARCH);
        uaLocale.put(R.id.calendar_april, UkrainianLang.Calendar.APRIL);
        uaLocale.put(R.id.calendar_may, UkrainianLang.Calendar.MAY);
        uaLocale.put(R.id.calendar_june, UkrainianLang.Calendar.JUNE);
        uaLocale.put(R.id.calendar_july, UkrainianLang.Calendar.JULY);
        uaLocale.put(R.id.calendar_august, UkrainianLang.Calendar.AUGUST);
        uaLocale.put(R.id.calendar_september, UkrainianLang.Calendar.SEPTEMBER);
        uaLocale.put(R.id.calendar_october, UkrainianLang.Calendar.OCTOBER);
        uaLocale.put(R.id.calendar_november, UkrainianLang.Calendar.NOVEMBER);
        uaLocale.put(R.id.calendar_december, UkrainianLang.Calendar.DECEMBER);
        uaLocale.put(R.id.calendar_january_s, UkrainianLang.Calendar.JANUARY_S);
        uaLocale.put(R.id.calendar_february_s, UkrainianLang.Calendar.FEBRUARY_S);
        uaLocale.put(R.id.calendar_march_s, UkrainianLang.Calendar.MARCH_S);
        uaLocale.put(R.id.calendar_april_s, UkrainianLang.Calendar.APRIL_S);
        uaLocale.put(R.id.calendar_may_s, UkrainianLang.Calendar.MAY_S);
        uaLocale.put(R.id.calendar_june_s, UkrainianLang.Calendar.JUNE_S);
        uaLocale.put(R.id.calendar_july_s, UkrainianLang.Calendar.JULY_S);
        uaLocale.put(R.id.calendar_august_s, UkrainianLang.Calendar.AUGUST_S);
        uaLocale.put(R.id.calendar_september_s, UkrainianLang.Calendar.SEPTEMBER_S);
        uaLocale.put(R.id.calendar_october_s, UkrainianLang.Calendar.OCTOBER_S);
        uaLocale.put(R.id.calendar_november_s, UkrainianLang.Calendar.NOVEMBER_S);
        uaLocale.put(R.id.calendar_december_s, UkrainianLang.Calendar.DECEMBER_S);
        uaLocale.put(R.id.calendar_local, UkrainianLang.Calendar.LOCAL);
        uaLocale.put(R.id.main_visits_calendar_mon, UkrainianLang.Calendar.MON);
        uaLocale.put(R.id.main_visits_calendar_tue, UkrainianLang.Calendar.TUE);
        uaLocale.put(R.id.main_visits_calendar_wed, UkrainianLang.Calendar.WED);
        uaLocale.put(R.id.main_visits_calendar_thu, UkrainianLang.Calendar.THU);
        uaLocale.put(R.id.main_visits_calendar_fri, UkrainianLang.Calendar.FRI);
        uaLocale.put(R.id.main_visits_calendar_sat, UkrainianLang.Calendar.SAT);
        uaLocale.put(R.id.main_visits_calendar_sun, UkrainianLang.Calendar.SUN);
        uaLocale.put(R.id.calendar_monday, UkrainianLang.Calendar.MONDAY);
        uaLocale.put(R.id.calendar_tuesday, UkrainianLang.Calendar.TUESDAY);
        uaLocale.put(R.id.calendar_wednesday, UkrainianLang.Calendar.WEDNESDAY);
        uaLocale.put(R.id.calendar_thursday, UkrainianLang.Calendar.THURSDAY);
        uaLocale.put(R.id.calendar_friday, UkrainianLang.Calendar.FRIDAY);
        uaLocale.put(R.id.calendar_saturday, UkrainianLang.Calendar.SATURDAY);
        uaLocale.put(R.id.calendar_sunday, UkrainianLang.Calendar.SUNDAY);


        //GENERAL
        rusLocale.put(R.id.no_internet_connection, RussianLang.General.NO_INTERNET_CONNECTION);
        rusLocale.put(R.id.error_401, RussianLang.General.ERROR_401);
        rusLocale.put(R.id.general_message, RussianLang.General.MESSAGE);
        rusLocale.put(R.id.general_error, RussianLang.General.ERROR);
        rusLocale.put(R.id.general_unknown_error, RussianLang.General.UNKNOWN_ERROR);

        uaLocale.put(R.id.no_internet_connection, UkrainianLang.General.NO_INTERNET_CONNECTION);
        uaLocale.put(R.id.error_401, UkrainianLang.General.ERROR_401);
        uaLocale.put(R.id.general_message, UkrainianLang.General.MESSAGE);
        uaLocale.put(R.id.general_error, UkrainianLang.General.ERROR);
        uaLocale.put(R.id.general_unknown_error, UkrainianLang.General.UNKNOWN_ERROR);

        //LOGIN FRAGMENT
        rusLocale.put(R.id.fragment_login_tiet_email, RussianLang.Login.HINT_LOGIN);
        rusLocale.put(R.id.fragment_login_tiet_password, RussianLang.Login.HINT_PASSWORD);
        rusLocale.put(R.id.fragment_login_btn_login, RussianLang.Login.TEXT_ENTER);
        rusLocale.put(R.id.fragment_login_tv_registration, RussianLang.Login.TEXT_REGISTRATION);
        rusLocale.put(R.id.fragment_login_tv_restore_password, RussianLang.Login.TEXT_RESTORE_PASSWORD);
        rusLocale.put(R.id.app_exit_on_back_hint, RussianLang.Login.TEXT_TOAST_EXIT);

        uaLocale.put(R.id.fragment_login_tiet_email, UkrainianLang.Login.HINT_LOGIN);
        uaLocale.put(R.id.fragment_login_tiet_password, UkrainianLang.Login.HINT_PASSWORD);
        uaLocale.put(R.id.fragment_login_btn_login, UkrainianLang.Login.TEXT_ENTER);
        uaLocale.put(R.id.fragment_login_tv_registration, UkrainianLang.Login.TEXT_REGISTRATION);
        uaLocale.put(R.id.fragment_login_tv_restore_password, UkrainianLang.Login.TEXT_RESTORE_PASSWORD);
        uaLocale.put(R.id.app_exit_on_back_hint, UkrainianLang.Login.TEXT_TOAST_EXIT);

        //MAIN DEPARTMENTS ACTIVITY
        rusLocale.put(R.id.activity_main_departments_title, RussianLang.MainDepartments.TITLE);

        uaLocale.put(R.id.activity_main_departments_title, UkrainianLang.MainDepartments.TITLE);

        //NOTIFICATIONS ACTIVITY
        rusLocale.put(R.id.activity_notifications_title, RussianLang.Notifications.TITLE);

        uaLocale.put(R.id.activity_notifications_title, UkrainianLang.Notifications.TITLE);


        //Today Fragment
        rusLocale.put(R.id.fragment_today_list_tasks_title, RussianLang.TodayFragment.TITLE_TASK);
        rusLocale.put(R.id.fragment_today_notification_0, RussianLang.TodayFragment.TITLE_NOTIF);
        rusLocale.put(R.id.today_tab_1, RussianLang.TodayFragment.TITLE_TAB_1);
        rusLocale.put(R.id.today_tab_2, RussianLang.TodayFragment.TITLE_TAB_2);

        uaLocale.put(R.id.fragment_today_list_tasks_title, UkrainianLang.TodayFragment.TITLE_TASK);
        uaLocale.put(R.id.fragment_today_notification_0, UkrainianLang.TodayFragment.TITLE_NOTIF);
        uaLocale.put(R.id.today_tab_1, UkrainianLang.TodayFragment.TITLE_TAB_1);
        uaLocale.put(R.id.today_tab_2, UkrainianLang.TodayFragment.TITLE_TAB_2);

        //Points Activity
        rusLocale.put(R.id.activity_points_title, RussianLang.PointsActivity.TITLE);
        rusLocale.put(R.id.activity_points_tab1_title, RussianLang.PointsActivity.TITLE_TAB1);
        rusLocale.put(R.id.activity_points_tab2_title, RussianLang.PointsActivity.TITLE_TAB2);
        rusLocale.put(R.id.fragment_page_history_list_empty, RussianLang.PointsActivity.PageHistoryFragment.TEXT_EMPTY_LIST);

        uaLocale.put(R.id.activity_points_title, UkrainianLang.PointsActivity.TITLE);
        uaLocale.put(R.id.activity_points_tab1_title, UkrainianLang.PointsActivity.TITLE_TAB1);
        uaLocale.put(R.id.activity_points_tab2_title, UkrainianLang.PointsActivity.TITLE_TAB2);
        uaLocale.put(R.id.fragment_page_history_list_empty, UkrainianLang.PointsActivity.PageHistoryFragment.TEXT_EMPTY_LIST);

        //FishkaConditions Activity
        rusLocale.put(R.id.activity_fishka_conditions_toolbar_title, RussianLang.FishkaConditionsActivity.TITLE);

        uaLocale.put(R.id.activity_fishka_conditions_toolbar_title, UkrainianLang.FishkaConditionsActivity.TITLE);

        //FishkaCards Activity
        rusLocale.put(R.id.activity_fishka_cards_toolbar_title, RussianLang.FishkaCardsActivity.TITLE);

        uaLocale.put(R.id.activity_fishka_cards_toolbar_title, UkrainianLang.FishkaCardsActivity.TITLE);

        //FishkaRules Activity
        rusLocale.put(R.id.activity_fishka_rules_toolbar_title, RussianLang.FishkaRulesActivity.TITLE);

        uaLocale.put(R.id.activity_fishka_rules_toolbar_title, UkrainianLang.FishkaRulesActivity.TITLE);

        //FishkaCardAdd Activity
        rusLocale.put(R.id.activity_fishka_card_add_toolbar_title, RussianLang.FishkaCardAddActivity.TITLE);
        rusLocale.put(R.id.activity_fishka_card_add_input_layout_card, RussianLang.FishkaCardAddActivity.HINT_INPUT_CARD);
        rusLocale.put(R.id.activity_fishka_card_add_input_layout_phone, RussianLang.FishkaCardAddActivity.HINT_INPUT_PHONE);
        rusLocale.put(R.id.activity_fishka_card_add_btn_save, RussianLang.FishkaCardAddActivity.TEXT_BUTTON_SAVE);
        rusLocale.put(R.id.activity_points_add_card_input_card_error, RussianLang.FishkaCardAddActivity.ERROR_CARD_INPUT);
        rusLocale.put(R.id.activity_points_add_card_input_phone_error, RussianLang.FishkaCardAddActivity.ERROR_PHONE_INPUT);
        rusLocale.put(R.id.card_add_success, RussianLang.FishkaCardAddActivity.CARD_ADD_SUCCESS);

        uaLocale.put(R.id.activity_fishka_card_add_toolbar_title, UkrainianLang.FishkaCardAddActivity.TITLE);
        uaLocale.put(R.id.activity_fishka_card_add_input_layout_card, UkrainianLang.FishkaCardAddActivity.HINT_INPUT_CARD);
        uaLocale.put(R.id.activity_fishka_card_add_input_layout_phone, UkrainianLang.FishkaCardAddActivity.HINT_INPUT_PHONE);
        uaLocale.put(R.id.activity_fishka_card_add_btn_save, UkrainianLang.FishkaCardAddActivity.TEXT_BUTTON_SAVE);
        uaLocale.put(R.id.activity_points_add_card_input_card_error, UkrainianLang.FishkaCardAddActivity.ERROR_CARD_INPUT);
        uaLocale.put(R.id.activity_points_add_card_input_phone_error, UkrainianLang.FishkaCardAddActivity.ERROR_PHONE_INPUT);
        uaLocale.put(R.id.card_add_success, UkrainianLang.FishkaCardAddActivity.CARD_ADD_SUCCESS);


        //ExchangePointsForFishka Activity
        rusLocale.put(R.id.activity_exchange_points_for_fishka_title, RussianLang.ExchangePointsForFishkaActivity.TITLE_1);
        rusLocale.put(R.id.activity_exchange_points_for_fishka_title_2, RussianLang.ExchangePointsForFishkaActivity.TITLE_2);
        rusLocale.put(R.id.activity_exchange_points_for_fishka_number_card, RussianLang.ExchangePointsForFishkaActivity.SUB_TITLE);
        rusLocale.put(R.id.fragment_exchange_points_text_1, RussianLang.ExchangePointsForFishkaActivity.RECEIVE_POINTS);
        rusLocale.put(R.id.fragment_exchange_points_text_3, RussianLang.ExchangePointsForFishkaActivity.EXCHANGE);
        rusLocale.put(R.id.fragment_exchange_points_btn_get_code, RussianLang.ExchangePointsForFishkaActivity.GET_CODE);
        rusLocale.put(R.id.fragment_exchange_points_sms_input, RussianLang.ExchangePointsForFishkaActivity.ENTER_SMS);
        rusLocale.put(R.id.fragment_exchange_points_sms_send_code_again, RussianLang.ExchangePointsForFishkaActivity.SEND_CODE_AGAIN);
        rusLocale.put(R.id.fragment_exchange_points_sms_send_exchange_points, RussianLang.ExchangePointsForFishkaActivity.EXCHANGE_POINTS);
        rusLocale.put(R.id.not_enough_points_to_exchange, RussianLang.ExchangePointsForFishkaActivity.NOT_ENOUGH_POINTS_TO_EXCHANGE);

        uaLocale.put(R.id.activity_exchange_points_for_fishka_title, UkrainianLang.ExchangePointsForFishkaActivity.TITLE_1);
        uaLocale.put(R.id.activity_exchange_points_for_fishka_title_2, UkrainianLang.ExchangePointsForFishkaActivity.TITLE_2);
        uaLocale.put(R.id.activity_exchange_points_for_fishka_number_card, UkrainianLang.ExchangePointsForFishkaActivity.SUB_TITLE);
        uaLocale.put(R.id.fragment_exchange_points_text_1, UkrainianLang.ExchangePointsForFishkaActivity.RECEIVE_POINTS);
        uaLocale.put(R.id.fragment_exchange_points_text_3, UkrainianLang.ExchangePointsForFishkaActivity.EXCHANGE);
        uaLocale.put(R.id.fragment_exchange_points_btn_get_code, UkrainianLang.ExchangePointsForFishkaActivity.GET_CODE);
        uaLocale.put(R.id.fragment_exchange_points_sms_input, UkrainianLang.ExchangePointsForFishkaActivity.ENTER_SMS);
        uaLocale.put(R.id.fragment_exchange_points_sms_send_code_again, UkrainianLang.ExchangePointsForFishkaActivity.SEND_CODE_AGAIN);
        uaLocale.put(R.id.fragment_exchange_points_sms_send_exchange_points, UkrainianLang.ExchangePointsForFishkaActivity.EXCHANGE_POINTS);
        uaLocale.put(R.id.not_enough_points_to_exchange, UkrainianLang.ExchangePointsForFishkaActivity.NOT_ENOUGH_POINTS_TO_EXCHANGE);


        //AddVisit Activity
        rusLocale.put(R.id.activity_add_visit_toolbar_title, RussianLang.AddVisitActivity.TITLE);
        rusLocale.put(R.id.activity_add_visit_title_input_default, RussianLang.AddVisitActivity.INPUT_NAME_VISIT);
        rusLocale.put(R.id.activity_add_visit_title, RussianLang.AddVisitActivity.INPUT_CAPTION_VISIT);
        rusLocale.put(R.id.activity_add_visit_title_input, RussianLang.AddVisitActivity.INPUT_VISIT_HINT);
        rusLocale.put(R.id.activity_add_visit_add_doc, RussianLang.AddVisitActivity.INPUT_CAPTION_ADD_DOCTOR);
        rusLocale.put(R.id.activity_add_visit_add_mp, RussianLang.AddVisitActivity.INPUT_CAPTION_ADD_MP);
        rusLocale.put(R.id.activity_add_visit_value_add_second, RussianLang.AddVisitActivity.TITLE_ADD_SPEC_EMPTY);
        rusLocale.put(R.id.activity_add_visit_layout_date_title, RussianLang.AddVisitActivity.DATE);
        rusLocale.put(R.id.activity_add_visit_layout_time_title, RussianLang.AddVisitActivity.TIME);
        rusLocale.put(R.id.activity_add_visit_layout_duration_title, RussianLang.AddVisitActivity.DURATION);
        rusLocale.put(R.id.activity_add_visit_notes_title, RussianLang.AddVisitActivity.NOTES_TITLE);
        rusLocale.put(R.id.activity_add_visit_notes_title_input, RussianLang.AddVisitActivity.NOTES_HINT);
        rusLocale.put(R.id.activity_add_visit_btn_create, RussianLang.AddVisitActivity.CREATE);
        rusLocale.put(R.id.activity_add_visit_date_picker_title, RussianLang.AddVisitActivity.TITLE_DATE_PICKER);
        rusLocale.put(R.id.activity_add_visit_time_picker_title, RussianLang.AddVisitActivity.TITLE_TIME_PICKER);
        rusLocale.put(R.id.activity_add_visit_picker_btn_cancel, RussianLang.AddVisitActivity.TITLE_PICKER_BTN_CANCEL);
        rusLocale.put(R.id.activity_add_visit_picker_btn_ok, RussianLang.AddVisitActivity.TITLE_PICKER_BTN_OK);
        rusLocale.put(R.id.activity_add_visit_error_date_select, RussianLang.AddVisitActivity.MESSAGE_ERROR_SELECT_DATE);
        rusLocale.put(R.id.activity_add_visit_error_time_select, RussianLang.AddVisitActivity.MESSAGE_ERROR_SELECT_TIME);
        rusLocale.put(R.id.activity_add_visit_popup_menu_0, RussianLang.AddVisitActivity.POPUP_MENU_0);
        rusLocale.put(R.id.activity_add_visit_popup_menu_1, RussianLang.AddVisitActivity.POPUP_MENU_1);
        rusLocale.put(R.id.activity_add_visit_popup_menu_2, RussianLang.AddVisitActivity.POPUP_MENU_2);
        rusLocale.put(R.id.activity_add_visit_popup_menu_3, RussianLang.AddVisitActivity.POPUP_MENU_3);
        rusLocale.put(R.id.activity_add_visit_popup_menu_4, RussianLang.AddVisitActivity.POPUP_MENU_4);
        rusLocale.put(R.id.activity_add_visit_popup_menu_5, RussianLang.AddVisitActivity.POPUP_MENU_5);
        rusLocale.put(R.id.activity_add_visit_popup_menu_6, RussianLang.AddVisitActivity.POPUP_MENU_6);
        rusLocale.put(R.id.activity_add_visit_popup_menu_7, RussianLang.AddVisitActivity.POPUP_MENU_7);
        rusLocale.put(R.id.activity_add_visit_popup_menu_8, RussianLang.AddVisitActivity.POPUP_MENU_8);
        rusLocale.put(R.id.activity_add_visit_popup_menu_9, RussianLang.AddVisitActivity.POPUP_MENU_9);
        rusLocale.put(R.id.activity_add_visit_popup_menu_10, RussianLang.AddVisitActivity.POPUP_MENU_10);
        rusLocale.put(R.id.activity_add_visit_popup_menu_11, RussianLang.AddVisitActivity.POPUP_MENU_11);
        rusLocale.put(R.id.no_select_visit_member, RussianLang.AddVisitActivity.NO_SELECT_VISIT_MEMBER);
        rusLocale.put(R.id.no_select_visit_title, RussianLang.AddVisitActivity.NO_SELECT_VISIT_TITLE);
        rusLocale.put(R.id.visits_create_ok, RussianLang.AddVisitActivity.TOAST_OK);


        uaLocale.put(R.id.activity_add_visit_toolbar_title, UkrainianLang.AddVisitActivity.TITLE);
        uaLocale.put(R.id.activity_add_visit_title_input_default, UkrainianLang.AddVisitActivity.INPUT_NAME_VISIT);
        uaLocale.put(R.id.activity_add_visit_title, UkrainianLang.AddVisitActivity.INPUT_CAPTION_VISIT);
        uaLocale.put(R.id.activity_add_visit_title_input, UkrainianLang.AddVisitActivity.INPUT_VISIT_HINT);
        uaLocale.put(R.id.activity_add_visit_add_doc, UkrainianLang.AddVisitActivity.INPUT_CAPTION_ADD_DOCTOR);
        uaLocale.put(R.id.activity_add_visit_add_mp, UkrainianLang.AddVisitActivity.INPUT_CAPTION_ADD_MP);
        uaLocale.put(R.id.activity_add_visit_value_add_second, UkrainianLang.AddVisitActivity.TITLE_ADD_SPEC_EMPTY);
        uaLocale.put(R.id.activity_add_visit_layout_date_title, UkrainianLang.AddVisitActivity.DATE);
        uaLocale.put(R.id.activity_add_visit_layout_time_title, UkrainianLang.AddVisitActivity.TIME);
        uaLocale.put(R.id.activity_add_visit_layout_duration_title, UkrainianLang.AddVisitActivity.DURATION);
        uaLocale.put(R.id.activity_add_visit_notes_title, UkrainianLang.AddVisitActivity.NOTES_TITLE);
        uaLocale.put(R.id.activity_add_visit_notes_title_input, UkrainianLang.AddVisitActivity.NOTES_HINT);
        uaLocale.put(R.id.activity_add_visit_btn_create, UkrainianLang.AddVisitActivity.CREATE);
        uaLocale.put(R.id.activity_add_visit_date_picker_title, UkrainianLang.AddVisitActivity.TITLE_DATE_PICKER);
        uaLocale.put(R.id.activity_add_visit_time_picker_title, UkrainianLang.AddVisitActivity.TITLE_TIME_PICKER);
        uaLocale.put(R.id.activity_add_visit_picker_btn_cancel, UkrainianLang.AddVisitActivity.TITLE_PICKER_BTN_CANCEL);
        uaLocale.put(R.id.activity_add_visit_picker_btn_ok, UkrainianLang.AddVisitActivity.TITLE_PICKER_BTN_OK);
        uaLocale.put(R.id.activity_add_visit_error_date_select, UkrainianLang.AddVisitActivity.MESSAGE_ERROR_SELECT_DATE);
        uaLocale.put(R.id.activity_add_visit_error_time_select, UkrainianLang.AddVisitActivity.MESSAGE_ERROR_SELECT_TIME);
        uaLocale.put(R.id.activity_add_visit_popup_menu_0, UkrainianLang.AddVisitActivity.POPUP_MENU_0);
        uaLocale.put(R.id.activity_add_visit_popup_menu_1, UkrainianLang.AddVisitActivity.POPUP_MENU_1);
        uaLocale.put(R.id.activity_add_visit_popup_menu_2, UkrainianLang.AddVisitActivity.POPUP_MENU_2);
        uaLocale.put(R.id.activity_add_visit_popup_menu_3, UkrainianLang.AddVisitActivity.POPUP_MENU_3);
        uaLocale.put(R.id.activity_add_visit_popup_menu_4, UkrainianLang.AddVisitActivity.POPUP_MENU_4);
        uaLocale.put(R.id.activity_add_visit_popup_menu_5, UkrainianLang.AddVisitActivity.POPUP_MENU_5);
        uaLocale.put(R.id.activity_add_visit_popup_menu_6, UkrainianLang.AddVisitActivity.POPUP_MENU_6);
        uaLocale.put(R.id.activity_add_visit_popup_menu_7, UkrainianLang.AddVisitActivity.POPUP_MENU_7);
        uaLocale.put(R.id.activity_add_visit_popup_menu_8, UkrainianLang.AddVisitActivity.POPUP_MENU_8);
        uaLocale.put(R.id.activity_add_visit_popup_menu_9, UkrainianLang.AddVisitActivity.POPUP_MENU_9);
        uaLocale.put(R.id.activity_add_visit_popup_menu_10, UkrainianLang.AddVisitActivity.POPUP_MENU_10);
        uaLocale.put(R.id.activity_add_visit_popup_menu_11, UkrainianLang.AddVisitActivity.POPUP_MENU_11);
        uaLocale.put(R.id.no_select_visit_member, UkrainianLang.AddVisitActivity.NO_SELECT_VISIT_MEMBER);
        uaLocale.put(R.id.no_select_visit_title, UkrainianLang.AddVisitActivity.NO_SELECT_VISIT_TITLE);
        uaLocale.put(R.id.visits_create_ok, UkrainianLang.AddVisitActivity.TOAST_OK);


        //VisitStatus
        rusLocale.put(R.id.status_visits_canceled, RussianLang.VisitStatus.CANCELED);
        rusLocale.put(R.id.status_visits_ended, RussianLang.VisitStatus.ENDED);
        rusLocale.put(R.id.status_visits_started, RussianLang.VisitStatus.STARTED);
        rusLocale.put(R.id.status_visits_failed, RussianLang.VisitStatus.FAILED);
        rusLocale.put(R.id.status_visits_accepted, RussianLang.VisitStatus.ACCEPTED);
        rusLocale.put(R.id.status_visits_new, RussianLang.VisitStatus.NEW);
        rusLocale.put(R.id.status_visits_processing, RussianLang.VisitStatus.PROCESSING);


        uaLocale.put(R.id.status_visits_canceled, UkrainianLang.VisitStatus.CANCELED);
        uaLocale.put(R.id.status_visits_ended, UkrainianLang.VisitStatus.ENDED);
        uaLocale.put(R.id.status_visits_started, UkrainianLang.VisitStatus.STARTED);
        uaLocale.put(R.id.status_visits_failed, UkrainianLang.VisitStatus.FAILED);
        uaLocale.put(R.id.status_visits_accepted, UkrainianLang.VisitStatus.ACCEPTED);
        uaLocale.put(R.id.status_visits_new, UkrainianLang.VisitStatus.NEW);
        uaLocale.put(R.id.status_visits_processing, UkrainianLang.VisitStatus.PROCESSING);


        //SelectSecondMember Activity
        rusLocale.put(R.id.activity_select_second_member_toolbar_title, RussianLang.SelectSecondMemberActivity.TITLE);

        uaLocale.put(R.id.activity_select_second_member_toolbar_title, UkrainianLang.SelectSecondMemberActivity.TITLE);

        //VisitViewer Activity
        rusLocale.put(R.id.activity_visit_viewer_toolbar_title, RussianLang.VisitViewerActivity.TITLE);
        rusLocale.put(R.id.activity_visit_viewer_other_time, RussianLang.VisitViewerActivity.CAPTION_CHANGE_TIME_VSIT);
        rusLocale.put(R.id.btn_visits_accept, RussianLang.VisitViewerActivity.BTN_ACCEPT);
        rusLocale.put(R.id.btn_visits_start, RussianLang.VisitViewerActivity.BTN_START);
        rusLocale.put(R.id.btn_visits_end, RussianLang.VisitViewerActivity.BTN_END);
        rusLocale.put(R.id.activity_visit_viewer_btn_cancel, RussianLang.VisitViewerActivity.BTN_CANCEL);
        rusLocale.put(R.id.activity_visit_mp_anceta_btn_send, RussianLang.VisitViewerActivity.BTN_SEND_MP);
        rusLocale.put(R.id.activity_visit_mp_anceta_btn_cancel, RussianLang.VisitViewerActivity.BTN_CANCEL_MP);
        rusLocale.put(R.id.mp_anketa_dont_downloading_anketa, RussianLang.VisitViewerActivity.DONT_DOWNLOAD_ANKETA_MP);

        uaLocale.put(R.id.activity_visit_viewer_toolbar_title, UkrainianLang.VisitViewerActivity.TITLE);
        uaLocale.put(R.id.activity_visit_viewer_other_time, UkrainianLang.VisitViewerActivity.CAPTION_CHANGE_TIME_VSIT);
        uaLocale.put(R.id.btn_visits_accept, UkrainianLang.VisitViewerActivity.BTN_ACCEPT);
        uaLocale.put(R.id.btn_visits_start, UkrainianLang.VisitViewerActivity.BTN_START);
        uaLocale.put(R.id.btn_visits_end, UkrainianLang.VisitViewerActivity.BTN_END);
        uaLocale.put(R.id.activity_visit_viewer_btn_cancel, UkrainianLang.VisitViewerActivity.BTN_CANCEL);
        uaLocale.put(R.id.activity_visit_mp_anceta_btn_send, UkrainianLang.VisitViewerActivity.BTN_SEND_MP);
        uaLocale.put(R.id.activity_visit_mp_anceta_btn_cancel, UkrainianLang.VisitViewerActivity.BTN_CANCEL_MP);
        uaLocale.put(R.id.mp_anketa_dont_downloading_anketa, UkrainianLang.VisitViewerActivity.DONT_DOWNLOAD_ANKETA_MP);

        //DoctorAncetaActivity Activity
        rusLocale.put(R.id.activity_visit_doctor_anceta_toolbar_title, RussianLang.DoctorAncetaActivity.TITLE);
        rusLocale.put(R.id.activity_visit_doctor_anceta_btn_send, RussianLang.DoctorAncetaActivity.BTN_SEND);


        uaLocale.put(R.id.activity_visit_doctor_anceta_toolbar_title, UkrainianLang.DoctorAncetaActivity.TITLE);
        uaLocale.put(R.id.activity_visit_doctor_anceta_btn_send, UkrainianLang.DoctorAncetaActivity.BTN_SEND);


        //ChangeDateVisit Activity
        rusLocale.put(R.id.activity_change_date_visit_toolbar_title, RussianLang.ChangeDateVisitActivity.TITLE);
        rusLocale.put(R.id.activity_change_date_visit_title, RussianLang.ChangeDateVisitActivity.TITLE_1);
        rusLocale.put(R.id.activity_change_date_visit_title_new, RussianLang.ChangeDateVisitActivity.TITLE_2);
        rusLocale.put(R.id.activity_change_date_visit_btn_create, RussianLang.ChangeDateVisitActivity.BTN_SEND);

        uaLocale.put(R.id.activity_change_date_visit_toolbar_title, UkrainianLang.ChangeDateVisitActivity.TITLE);
        uaLocale.put(R.id.activity_change_date_visit_title, UkrainianLang.ChangeDateVisitActivity.TITLE_1);
        uaLocale.put(R.id.activity_change_date_visit_title_new, UkrainianLang.ChangeDateVisitActivity.TITLE_2);
        uaLocale.put(R.id.activity_change_date_visit_btn_create, UkrainianLang.ChangeDateVisitActivity.BTN_SEND);

        //DescriptionMaterial Fragment
        rusLocale.put(R.id.material_test, RussianLang.FragmentDescriptionMaterial.TITLE_T);
        rusLocale.put(R.id.material_presentation, RussianLang.FragmentDescriptionMaterial.TITLE_P);
        rusLocale.put(R.id.material_tests, RussianLang.FragmentDescriptionMaterial.TITLE_Ts);
        rusLocale.put(R.id.material_presentations, RussianLang.FragmentDescriptionMaterial.TITLE_Ps);
        rusLocale.put(R.id.material_video, RussianLang.FragmentDescriptionMaterial.TITLE_V);

        uaLocale.put(R.id.material_test, UkrainianLang.FragmentDescriptionMaterial.TITLE_T);
        uaLocale.put(R.id.material_presentation, UkrainianLang.FragmentDescriptionMaterial.TITLE_P);
        uaLocale.put(R.id.material_tests, UkrainianLang.FragmentDescriptionMaterial.TITLE_Ts);
        uaLocale.put(R.id.material_presentations, UkrainianLang.FragmentDescriptionMaterial.TITLE_Ps);
        uaLocale.put(R.id.material_video, UkrainianLang.FragmentDescriptionMaterial.TITLE_V);

        //PageNews Fragment
        rusLocale.put(R.id.fragment_news_btn_all_news, RussianLang.FragmentNews.BTN_ALL_NEWS);

        uaLocale.put(R.id.fragment_news_btn_all_news, UkrainianLang.FragmentNews.BTN_ALL_NEWS);

        //PageClinicalCases Fragment
        rusLocale.put(R.id.fragment_clinical_cases_btn_all, RussianLang.FragmentClinicalCases.BTN_ALL_CLINICAL_CASES);
        rusLocale.put(R.id.fragment_clinical_cases_btn_my, RussianLang.FragmentClinicalCases.BTN_MY_CLINICAL_CASES);

        uaLocale.put(R.id.fragment_clinical_cases_btn_all, UkrainianLang.FragmentClinicalCases.BTN_ALL_CLINICAL_CASES);
        uaLocale.put(R.id.fragment_clinical_cases_btn_my, UkrainianLang.FragmentClinicalCases.BTN_MY_CLINICAL_CASES);

        //AllNews Activity
        rusLocale.put(R.id.activity_all_news_toolbar_title, RussianLang.AllNewsActivity.TITLE);

        uaLocale.put(R.id.activity_all_news_toolbar_title, UkrainianLang.AllNewsActivity.TITLE);

        //AllClinicalCases Activity
        rusLocale.put(R.id.activity_all_clinical_cases_toolbar_title, RussianLang.AllClinicalCasesActivity.TITLE);

        uaLocale.put(R.id.activity_all_clinical_cases_toolbar_title, UkrainianLang.AllClinicalCasesActivity.TITLE);

        //MyClinicalCases Activity
        rusLocale.put(R.id.activity_my_clinical_cases_toolbar_title, RussianLang.MyClinicalCasesActivity.TITLE);

        uaLocale.put(R.id.activity_my_clinical_cases_toolbar_title, UkrainianLang.MyClinicalCasesActivity.TITLE);


        //AddClinicalCase Activity
        rusLocale.put(R.id.add_md_case_details_min_symbols, RussianLang.AddClinicalCaseActivity.MIN_DETAILS);
        rusLocale.put(R.id.add_md_case_title_min_symbols, RussianLang.AddClinicalCaseActivity.MIN_TITLE);
        rusLocale.put(R.id.add_md_case_icods_empty, RussianLang.AddClinicalCaseActivity.EMPTY_ICOD);
        rusLocale.put(R.id.add_md_case_drugs_empty, RussianLang.AddClinicalCaseActivity.EMPTY_DRUGS);
        rusLocale.put(R.id.activity_clinical_case_viewer_btn_delete, RussianLang.AddClinicalCaseActivity.BTN_DELETE);
        rusLocale.put(R.id.activity_add_clinical_cases_toolbar_title, RussianLang.AddClinicalCaseActivity.TITLE);
        rusLocale.put(R.id.activity_add_clinical_cases_add_title_caption, RussianLang.AddClinicalCaseActivity.ADD_SUBTITLE);
        rusLocale.put(R.id.activity_add_clinical_cases_add_icod_caption, RussianLang.AddClinicalCaseActivity.MKB_SUBTITLE);
        rusLocale.put(R.id.activity_add_clinical_cases_add_icod_btn, RussianLang.AddClinicalCaseActivity.ADD_MKB_SUBTITLE);
        rusLocale.put(R.id.activity_add_clinical_cases_add_drug_caption, RussianLang.AddClinicalCaseActivity.DRUG_SUBTITLE);
        rusLocale.put(R.id.activity_add_clinical_cases_add_drug_btn, RussianLang.AddClinicalCaseActivity.ADD_DRUG_SUBTITLE);
        rusLocale.put(R.id.activity_add_clinical_cases_add_details_caption, RussianLang.AddClinicalCaseActivity.DETAILS_SUBTITLE);
        rusLocale.put(R.id.activity_add_clinical_cases_add_details_input, RussianLang.AddClinicalCaseActivity.DETAILS_HINT);
        rusLocale.put(R.id.activity_add_clinical_cases_add_image_btn, RussianLang.AddClinicalCaseActivity.ADD_IMAGE);
        rusLocale.put(R.id.activity_add_clinical_cases_btn_send, RussianLang.AddClinicalCaseActivity.BTN_SEND);
        rusLocale.put(R.id.activity_add_clinical_cases_btn_save, RussianLang.AddClinicalCaseActivity.BTN_SAVE);
        rusLocale.put(R.id.activity_add_clinical_cases_as_author, RussianLang.AddClinicalCaseActivity.AS_AUTHOR);

        uaLocale.put(R.id.add_md_case_details_min_symbols, UkrainianLang.AddClinicalCaseActivity.MIN_DETAILS);
        uaLocale.put(R.id.add_md_case_title_min_symbols, UkrainianLang.AddClinicalCaseActivity.MIN_TITLE);
        uaLocale.put(R.id.add_md_case_icods_empty, UkrainianLang.AddClinicalCaseActivity.EMPTY_ICOD);
        uaLocale.put(R.id.add_md_case_drugs_empty, UkrainianLang.AddClinicalCaseActivity.EMPTY_DRUGS);
        uaLocale.put(R.id.activity_clinical_case_viewer_btn_delete, UkrainianLang.AddClinicalCaseActivity.BTN_DELETE);
        uaLocale.put(R.id.activity_add_clinical_cases_toolbar_title, UkrainianLang.AddClinicalCaseActivity.TITLE);
        uaLocale.put(R.id.activity_add_clinical_cases_add_title_caption, UkrainianLang.AddClinicalCaseActivity.ADD_SUBTITLE);
        uaLocale.put(R.id.activity_add_clinical_cases_add_icod_caption, UkrainianLang.AddClinicalCaseActivity.MKB_SUBTITLE);
        uaLocale.put(R.id.activity_add_clinical_cases_add_icod_btn, UkrainianLang.AddClinicalCaseActivity.ADD_MKB_SUBTITLE);
        uaLocale.put(R.id.activity_add_clinical_cases_add_drug_caption, UkrainianLang.AddClinicalCaseActivity.DRUG_SUBTITLE);
        uaLocale.put(R.id.activity_add_clinical_cases_add_drug_btn, UkrainianLang.AddClinicalCaseActivity.ADD_DRUG_SUBTITLE);
        uaLocale.put(R.id.activity_add_clinical_cases_add_details_caption, UkrainianLang.AddClinicalCaseActivity.DETAILS_SUBTITLE);
        uaLocale.put(R.id.activity_add_clinical_cases_add_details_input, UkrainianLang.AddClinicalCaseActivity.DETAILS_HINT);
        uaLocale.put(R.id.activity_add_clinical_cases_add_image_btn, UkrainianLang.AddClinicalCaseActivity.ADD_IMAGE);
        uaLocale.put(R.id.activity_add_clinical_cases_btn_send, UkrainianLang.AddClinicalCaseActivity.BTN_SEND);
        uaLocale.put(R.id.activity_add_clinical_cases_btn_save, UkrainianLang.AddClinicalCaseActivity.BTN_SAVE);
        uaLocale.put(R.id.activity_add_clinical_cases_as_author, UkrainianLang.AddClinicalCaseActivity.AS_AUTHOR);

        //ViewClinicalCase Activity
        rusLocale.put(R.id.medical_case_status_1, RussianLang.ViewClinicalCaseActivity.MEDICAL_CASE_STATE_1);
        rusLocale.put(R.id.medical_case_status_2, RussianLang.ViewClinicalCaseActivity.MEDICAL_CASE_STATE_2);
        rusLocale.put(R.id.medical_case_status_3, RussianLang.ViewClinicalCaseActivity.MEDICAL_CASE_STATE_3);
        rusLocale.put(R.id.medical_case_status_4, RussianLang.ViewClinicalCaseActivity.MEDICAL_CASE_STATE_4);

        uaLocale.put(R.id.medical_case_status_1, UkrainianLang.ViewClinicalCaseActivity.MEDICAL_CASE_STATE_1);
        uaLocale.put(R.id.medical_case_status_2, UkrainianLang.ViewClinicalCaseActivity.MEDICAL_CASE_STATE_2);
        uaLocale.put(R.id.medical_case_status_3, UkrainianLang.ViewClinicalCaseActivity.MEDICAL_CASE_STATE_3);
        uaLocale.put(R.id.medical_case_status_4, UkrainianLang.ViewClinicalCaseActivity.MEDICAL_CASE_STATE_4);

        //Comments Activity
        rusLocale.put(R.id.activity_comments_toolbar_subtitle, RussianLang.CommentsActivity.TITLE);
        rusLocale.put(R.id.item_comment_root_reply, RussianLang.CommentsActivity.BTN_REPLY);
        rusLocale.put(R.id.delete_comment_dialog_title, RussianLang.CommentsActivity.DELETE_COMMENT_DIALOG_TITLE);
        rusLocale.put(R.id.delete_comment_dialog_text, RussianLang.CommentsActivity.DELETE_COMMENT_DIALOG_TEXT);


        uaLocale.put(R.id.activity_comments_toolbar_subtitle, UkrainianLang.CommentsActivity.TITLE);
        uaLocale.put(R.id.item_comment_root_reply, UkrainianLang.CommentsActivity.BTN_REPLY);
        uaLocale.put(R.id.delete_comment_dialog_title, UkrainianLang.CommentsActivity.DELETE_COMMENT_DIALOG_TITLE);
        uaLocale.put(R.id.delete_comment_dialog_text, UkrainianLang.CommentsActivity.DELETE_COMMENT_DIALOG_TEXT);

        //Incoming Activity
        rusLocale.put(R.id.activity_incoming_toolbar_title, RussianLang.IncomingActivity.TITLE);

        uaLocale.put(R.id.activity_incoming_toolbar_title, UkrainianLang.IncomingActivity.TITLE);

        //ExchangePointsSuccess Fragment
        rusLocale.put(R.id.fragment_exchange_points_success_counted_text, RussianLang.ExchangePointsSuccessFragment.TITLE_COUNTED);
        rusLocale.put(R.id.fragment_exchange_points_success_written_off_text, RussianLang.ExchangePointsSuccessFragment.TITLE_WRITTEN_OFF);
        rusLocale.put(R.id.fragment_exchange_points_success_close, RussianLang.ExchangePointsSuccessFragment.BTN_CLOSE);

        uaLocale.put(R.id.fragment_exchange_points_success_counted_text, UkrainianLang.ExchangePointsSuccessFragment.TITLE_COUNTED);
        uaLocale.put(R.id.fragment_exchange_points_success_written_off_text, UkrainianLang.ExchangePointsSuccessFragment.TITLE_WRITTEN_OFF);
        uaLocale.put(R.id.fragment_exchange_points_success_close, UkrainianLang.ExchangePointsSuccessFragment.BTN_CLOSE);


        //ExchangeLikiWiki Activity
        rusLocale.put(R.id.activity_exchange_likiwiki_title, RussianLang.ExchangeLikiWikiActivity.TITLE);

        uaLocale.put(R.id.activity_exchange_likiwiki_title, UkrainianLang.ExchangeLikiWikiActivity.TITLE);


        //ExchangeLikiWiki Fragment
        rusLocale.put(R.id.fragment_exchange_likiwiki_changed, RussianLang.ExchangeLikiWikiFragment.TITLE_CHANGED);
        rusLocale.put(R.id.fragment_exchange_likiwiki_goto, RussianLang.ExchangeLikiWikiFragment.TITLE_GOTO_LIKIWIKI);
        rusLocale.put(R.id.fragment_exchange_likiwiki_btn_get_code, RussianLang.ExchangeLikiWikiFragment.BTN_GET_CODE);
        rusLocale.put(R.id.fragment_exchange_likiwiki_btn_add_number, RussianLang.ExchangeLikiWikiFragment.BTN_ADD_NUMBER);
        rusLocale.put(R.id.fragment_exchange_likiwiki_btn_add_number_caption, RussianLang.ExchangeLikiWikiFragment.BTN_ADD_NUMBER_CAPTION);

        uaLocale.put(R.id.fragment_exchange_likiwiki_changed, UkrainianLang.ExchangeLikiWikiFragment.TITLE_CHANGED);
        uaLocale.put(R.id.fragment_exchange_likiwiki_goto, UkrainianLang.ExchangeLikiWikiFragment.TITLE_GOTO_LIKIWIKI);
        uaLocale.put(R.id.fragment_exchange_likiwiki_btn_get_code, UkrainianLang.ExchangeLikiWikiFragment.BTN_GET_CODE);
        uaLocale.put(R.id.fragment_exchange_likiwiki_btn_add_number, UkrainianLang.ExchangeLikiWikiFragment.BTN_ADD_NUMBER);
        uaLocale.put(R.id.fragment_exchange_likiwiki_btn_add_number_caption, UkrainianLang.ExchangeLikiWikiFragment.BTN_ADD_NUMBER_CAPTION);

        //AddPhoneNumber Fragment
        rusLocale.put(R.id.fragment_add_phone_number_input, RussianLang.AddPhoneNumberFragment.HINT);
        rusLocale.put(R.id.fragment_add_phone_number_add_btn, RussianLang.AddPhoneNumberFragment.BUTTON);

        uaLocale.put(R.id.fragment_add_phone_number_input, UkrainianLang.AddPhoneNumberFragment.HINT);
        uaLocale.put(R.id.fragment_add_phone_number_add_btn, UkrainianLang.AddPhoneNumberFragment.BUTTON);

        //AddPhoneNumberCheckSMS Fragment
        rusLocale.put(R.id.fragment_add_phone_number_check_sms_input_layout, RussianLang.AddPhoneNumberCheckSMSFragment.HINT);
        rusLocale.put(R.id.fragment_add_phone_number_check_sms_again_btn, RussianLang.AddPhoneNumberCheckSMSFragment.BUTTON_AGAIN_GET_CODE);
        rusLocale.put(R.id.fragment_add_phone_number_check_sms_check_btn, RussianLang.AddPhoneNumberCheckSMSFragment.BUTTON_CHECK);
        rusLocale.put(R.id.fragment_add_phone_number_check_sms_msg_check_code, RussianLang.AddPhoneNumberCheckSMSFragment.MSG_CHECH_CODE);

        uaLocale.put(R.id.fragment_add_phone_number_check_sms_input_layout, UkrainianLang.AddPhoneNumberCheckSMSFragment.HINT);
        uaLocale.put(R.id.fragment_add_phone_number_check_sms_again_btn, UkrainianLang.AddPhoneNumberCheckSMSFragment.BUTTON_AGAIN_GET_CODE);
        uaLocale.put(R.id.fragment_add_phone_number_check_sms_check_btn, UkrainianLang.AddPhoneNumberCheckSMSFragment.BUTTON_CHECK);
        uaLocale.put(R.id.fragment_add_phone_number_check_sms_msg_check_code, UkrainianLang.AddPhoneNumberCheckSMSFragment.MSG_CHECH_CODE);

        //AddCommentToLoadImage Activity
        rusLocale.put(R.id.activity_add_comment_to_load_image_toolbar_title, RussianLang.AddCommentToLoadImageActivity.TITLE);
        rusLocale.put(R.id.activity_add_comment_to_load_image_comment_layout, RussianLang.AddCommentToLoadImageActivity.HINT);
        rusLocale.put(R.id.activity_add_comment_to_load_image_comment_btn_create, RussianLang.AddCommentToLoadImageActivity.BTN);

        uaLocale.put(R.id.activity_add_comment_to_load_image_toolbar_title, UkrainianLang.AddCommentToLoadImageActivity.TITLE);
        uaLocale.put(R.id.activity_qr_code_generate_visit_btn_gk, UkrainianLang.AddCommentToLoadImageActivity.HINT);
        uaLocale.put(R.id.activity_add_comment_to_load_image_comment_btn_create, UkrainianLang.AddCommentToLoadImageActivity.BTN);

        //QrCodeGenerateVisit Activity
        rusLocale.put(R.id.activity_qr_code_generate_visit_toolbar_title, RussianLang.QrCodeGenerateVisitActivity.TITLE);
        rusLocale.put(R.id.activity_add_comment_to_load_image_comment_layout, RussianLang.QrCodeGenerateVisitActivity.BTN_GK);
        rusLocale.put(R.id.activity_qr_code_generate_visit_btn_qr, RussianLang.QrCodeGenerateVisitActivity.BTN_QR);

        uaLocale.put(R.id.activity_qr_code_generate_visit_toolbar_title, UkrainianLang.QrCodeGenerateVisitActivity.TITLE);
        uaLocale.put(R.id.activity_qr_code_generate_visit_btn_gk, UkrainianLang.QrCodeGenerateVisitActivity.BTN_GK);
        uaLocale.put(R.id.activity_qr_code_generate_visit_btn_qr, UkrainianLang.QrCodeGenerateVisitActivity.BTN_QR);

        //QrCodeGenerateVisit Activity
        rusLocale.put(R.id.activity_qr_visit_toolbar_title, RussianLang.ScanQrVisitActivity.TITLE);
        rusLocale.put(R.id.activity_qr_visit_btn_qr, RussianLang.ScanQrVisitActivity.BTN_QR);

        uaLocale.put(R.id.activity_qr_visit_toolbar_title, UkrainianLang.ScanQrVisitActivity.TITLE);
        uaLocale.put(R.id.activity_qr_visit_btn_qr, UkrainianLang.ScanQrVisitActivity.BTN_QR);


        //RegistrationPage2 Fragment
        rusLocale.put(R.id.dont_select_med_inst, RussianLang.RegistrationPage2Fragment.DONT_SELECT_MED_ZAK);
        rusLocale.put(R.id.dont_select_spes, RussianLang.RegistrationPage2Fragment.DONT_SELECT_SPEC);

        uaLocale.put(R.id.dont_select_med_inst, UkrainianLang.RegistrationPage2Fragment.DONT_SELECT_MED_ZAK);
        uaLocale.put(R.id.dont_select_spes, UkrainianLang.RegistrationPage2Fragment.DONT_SELECT_SPEC);


        //Tests Activity
        rusLocale.put(R.id.need_push_reaction, RussianLang.TestsActivity.NEED_PUSH_REACTION);

        uaLocale.put(R.id.need_push_reaction, UkrainianLang.TestsActivity.NEED_PUSH_REACTION);
    }

    public String getText(int key) {
        return App.getLanguage().equals("ru") ? rusLocale.get(key) : uaLocale.get(key);
    }
}
