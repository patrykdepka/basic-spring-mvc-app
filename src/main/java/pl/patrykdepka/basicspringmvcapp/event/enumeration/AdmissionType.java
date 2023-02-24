package pl.patrykdepka.basicspringmvcapp.event.enumeration;

public enum AdmissionType {
    PAID("paid", "Płatny"),
    FREE("free", "Bezpłatny");

    public final String name;
    public final String displayName;

    AdmissionType(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }
}
