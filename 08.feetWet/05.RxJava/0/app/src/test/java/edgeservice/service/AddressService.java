package edgeservice.service;

import edgeservice.ListProcedures;
import edgeservice.dto.Address;
import rx.Observable;

/**
 * Created by Juan on 10/3/2015.
 */
public class AddressService {

    private final ListProcedures procedures = new ListProcedures();

    public Observable<Address> fetchCustomerAddresses(long customerId) {
        return procedures.toSelectAddressObservable(customerId);
    }

}
