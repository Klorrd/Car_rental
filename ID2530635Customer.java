public class ID2530635Customer {
    private final String nicOrPassport;
    private final String name;
    private final String contact;
    private final String email;

    public ID2530635Customer(String nicOrPassport, String name, String contact, String email) {
        this.nicOrPassport = nicOrPassport;
        this.name = name;
        this.contact = contact;
        this.email = email;
    }

    public String getNicOrPassport() { return nicOrPassport; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s", nicOrPassport, name, contact, email);
    }
}
