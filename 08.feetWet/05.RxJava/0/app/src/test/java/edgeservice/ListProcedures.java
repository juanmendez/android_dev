package edgeservice;

import org.robolectric.util.Function;

import edgeservice.dto.Address;
import edgeservice.dto.Customer;
import edgeservice.dto.OwnedProduct;
import edgeservice.dto.Product;
import rx.Observable;

/**
 * Created by Juan on 10/3/2015.
 */
public class ListProcedures {
    public Observable<Customer> toSelectCustomerObservable(){

        return Observable.from( DBStorage.getDb().getCustomers() );
    }

    public Observable<Customer> toSelectCustomersObservable( long customerId ){
        return Observable.from( DBStorage.getDb().getCustomers() ).first(customer -> {
            return customer.getCustomerId() == customerId;
        });
    }

    public Observable<OwnedProduct> toSelectOwnedProductObservable(long customerId){

        Observable<OwnedProduct> ownerProductObservable = Observable.from(DBStorage.getDb().getOwnedProducts()).filter(ownedProduct -> {

            return ownedProduct.getCustomerId() == customerId;
        });

        return ownerProductObservable;
    }




    public Observable<Address> toSelectAddressObservable(long customerId){

        return Observable.from( DBStorage.getDb().getAddresses() ).first( address -> {
            return address.getCustomerId() == customerId;
        });
    }
}
