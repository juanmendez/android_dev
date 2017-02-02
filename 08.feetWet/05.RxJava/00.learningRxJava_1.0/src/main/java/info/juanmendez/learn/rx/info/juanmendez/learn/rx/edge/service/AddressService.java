package info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge.service;

import info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge.ListProcedures;
import info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge.dto.Address;
import rx.Observable;

/**
 * Created by Juan Mendez @juanmendezinfo on 10/3/2015.
 */
public class AddressService {

    private final ListProcedures procedures = new ListProcedures();

    public Observable<Address> fetchCustomerAddresses(long customerId) {
        return procedures.toSelectAddressObservable(customerId);
    }

}
