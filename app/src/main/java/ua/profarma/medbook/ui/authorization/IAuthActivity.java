package ua.profarma.medbook.ui.authorization;

public interface IAuthActivity {
    void onRegistrationStep1();
    void onRegistrationStep2();
    void onRegistrationStep3();
    void onRestore();
    void onRestoreSuccess();
    void onMedicalInstituteNearestSearch();
}
