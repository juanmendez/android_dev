package info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge;

import info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge.dto.Address;
import info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge.dto.Customer;
import info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge.dto.OwnedProduct;
import rx.Observable;

/**
 * Created by Juan Mendez @juanmendezinfo on 10/3/2015.
 */
public class ListProcedures {
    public Observable<Customer> toSelectCustomerObservable() {

        return Observable.from(DBStorage.getDb().getCustomers());
    }

    public Observable<Customer> toSelectCustomersObservable(long customerId) {
        return Observable.from(DBStorage.getDb().getCustomers()).first(customer -> {
            return customer.getCustomerId() == customerId;
        });
    }

    public Observable<OwnedProduct> toSelectOwnedProductObservable(long customerId) {

        Observable<OwnedProduct> ownerProductObservable = Observable.from(DBStorage.getDb().getOwnedProducts()).filter(ownedProduct -> {

            return ownedProduct.getCustomerId() == customerId;
        });

        return ownerProductObservable;
    }


    public Observable<Address> toSelectAddressObservable(long customerId) {

        return Observable.from(DBStorage.getDb().getAddresses()).first(address -> {
            return address.getCustomerId() == customerId;
        });
    }
}
