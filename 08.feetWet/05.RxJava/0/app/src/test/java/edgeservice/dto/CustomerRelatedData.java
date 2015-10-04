package edgeservice.dto;

/**
 * Created by Juan on 10/3/2015.
 */
public class CustomerRelatedData {

    private long customerId;

    public CustomerRelatedData() {
    }

    public CustomerRelatedData(long customerId) {
        this.customerId = customerId;
    }

    public long getCustomerId() {
        return customerId;
    }
}
