package mobi.medbook.android.types.requests;

public class AddFishkaRequest {
    public int user_id;
    public String fishka_card_number;
    public String phone;

    public AddFishkaRequest(int user_id, String fishka_card_number, String phone) {
        this.user_id = user_id;
        this.fishka_card_number = fishka_card_number;
        this.phone = phone;
    }

}
