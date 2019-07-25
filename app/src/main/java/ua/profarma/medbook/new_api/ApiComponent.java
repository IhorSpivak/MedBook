package ua.profarma.medbook.new_api;

public class ApiComponent {

    private static ApiImpl api;

    public static ApiImpl provideApi(){
        if (api == null){
            api = new ApiImpl(new ApiClientNew().getClient());
        }

        return api;
    }
}
