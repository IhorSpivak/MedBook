package mobi.medbook.android.controls;

import java.util.ArrayList;
import java.util.List;

import mobi.medbook.android.App;
import mobi.medbook.android.R;
import mobi.medbook.android.api.ApiRest;
import mobi.medbook.android.events.authorization.EventNearestMedicalInstitutions;
import mobi.medbook.android.events.authorization.EventSpecializationListLoad;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.types.MedicalInstitution;
import mobi.medbook.android.types.MedicalInstitutionTranslate;
import mobi.medbook.android.types.NearestMedicalInstitutionItems;
import mobi.medbook.android.types.SpecializationItems;
import mobi.medbook.android.utils.AppUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataForRegistrationControl {

    private SpecializationItems mListOfSpecialization = null;
    //private ArrayList<MedicalInstitution> mListOfNearestMedicalInstitute = null;

    public SpecializationItems getmListOfSpecialization() {
        return mListOfSpecialization;
    }

    public void getSpecializations() {
        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
            ApiRest.PointAccess().getSpecializations().enqueue(new Callback<SpecializationItems>() {
                @Override
                public void onResponse(Call<SpecializationItems> call, Response<SpecializationItems> response) {
                    if (response.isSuccessful()) {
                        if (response.code() != ApiRest.CODE_401 && response.code() != ApiRest.CODE_521) {
                            mListOfSpecialization = response.body();
                            EventRouter.send(new EventSpecializationListLoad());
                        } else
                            AppUtils.toastError(response.message(), false);
                    } else {
                        AppUtils.toastError("response unsuccessful", false);
                    }
                }

                @Override
                public void onFailure(Call<SpecializationItems> call, Throwable t) {

                    AppUtils.toastError("response onFailure " + t.getLocalizedMessage(), false);
                }
            });
        } else {
            AppUtils.toastError(App.getAppContext().getString(R.string.no_internet_connection), false);
        }
    }

    public void getMedicalInstitutes() {
        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
            ApiRest.PointAccess().getMedicalInstitutes().enqueue(new Callback<List<MedicalInstitutionTranslate>>() {
                @Override
                public void onResponse(Call<List<MedicalInstitutionTranslate>> call, Response<List<MedicalInstitutionTranslate>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() != ApiRest.CODE_401 && response.code() != ApiRest.CODE_521) {
                        } else
                            AppUtils.toastError(response.message(), false);
                    } else {
                        AppUtils.toastError("response unsuccessful", false);
                    }
                }

                @Override
                public void onFailure(Call<List<MedicalInstitutionTranslate>> call, Throwable t) {

                    AppUtils.toastError("response onFailure " + t.getLocalizedMessage(), false);
                }
            });
        } else {
            AppUtils.toastError(App.getAppContext().getString(R.string.no_internet_connection), false);
        }
    }

    public void getMedicalNearestMedicalInstitutes(double lat, double lon, int qtu) {
        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
            ApiRest.PointAccess().getNearestMedicalInstitutes(lat, lon, qtu).enqueue(new Callback<NearestMedicalInstitutionItems>() {
                @Override
                public void onResponse(Call<NearestMedicalInstitutionItems> call, Response<NearestMedicalInstitutionItems> response) {
                    if (response.isSuccessful()) {
                        if (response.code() != ApiRest.CODE_401 && response.code() != ApiRest.CODE_521) {
                            ArrayList<MedicalInstitution> listOfNearestMedicalInstitute = response.body().items;
                            EventRouter.send(new EventNearestMedicalInstitutions(listOfNearestMedicalInstitute));
                        } else
                            AppUtils.toastError(response.message(), false);
                    } else {
                        AppUtils.toastError("response unsuccessful", false);
                    }
                }

                @Override
                public void onFailure(Call<NearestMedicalInstitutionItems> call, Throwable t) {
                    AppUtils.toastError(t.getMessage(), false);
                }
            });
        } else {
            AppUtils.toastError(App.getAppContext().getString(R.string.no_internet_connection), false);
        }
    }
}
