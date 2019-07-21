package ua.profarma.medbook.types.points;

public class HistoryExchange {
    public int id;
    public int user_id;
    public int content_target_id;
    public Integer test_id;
    public Integer video_id;
    public Integer presentation_id;
    public Double points;
    public int author_id;
    public int created_at;
    public long updated_at;
    public int do_not_send_push;
    public HistoryExchangeTranslations[] translations;

}
