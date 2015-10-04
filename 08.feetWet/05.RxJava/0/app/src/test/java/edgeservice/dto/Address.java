package edgeservice.dto;

/**
 * Created by Juan on 10/3/2015.
 */
public class Address extends CustomerRelatedData{
    private long id;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postalCode;

    public Address() {
    }

    public Address(long id, long customerId, String address1, String address2, String city, String state, String postalCode) {
        super(customerId);
        this.id = id;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public long getId() {
        return id;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
