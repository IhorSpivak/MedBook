package ua.profarma.medbook.types.user;

public class Agreement {
    public int id;
    public int agreement_type_id;
    public AgreementTranslation[] translations;
    public AgreementType agreementType;
}
