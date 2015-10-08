package edgeservice.service;

import edgeservice.ListProcedures;
import edgeservice.dto.Address;
import edgeservice.dto.Customer;
import edgeservice.dto.CustomerRelatedData;
import edgeservice.dto.CustomerZipEmulator;
import edgeservice.dto.OwnedProduct;
import rx.Observable;

/**
 * Created by Juan on 10/3/2015.
 */
public class CustomerService {
    private final ListProcedures procedures = new ListProcedures();
    private final AddressService addressService = new AddressService();
    private final ProduceService productService = new ProduceService();

    public CustomerService() {

    }

    public Observable<Customer> fetchCustomer(long customerId) {
        return procedures.toSelectCustomersObservable(customerId);
    }

    public Observable<Customer> fetchCustomerWithAddressesAndOwnedProducts(long customerId) {
        Observable<Customer> selectedCustomerObservable = fetchCustomer(customerId);
        Observable<Address> selectedAddressesObservable = addressService.fetchCustomerAddresses(customerId);
        Observable<OwnedProduct> selectedProductsObservable = productService.fetchOwnedProducts(customerId);

        // Combine the three stream of events
        Observable<CustomerRelatedData> dataStream = Observable.concat(
                selectedCustomerObservable,
                selectedAddressesObservable,
                selectedProductsObservable);

        // Wrap the concat'ed dataStream in another observable.  This makes it an
        // Observable< Observable< CustomerRelatedData> > which is the shape we need
        // for the zip function that follows.
        Observable<Observable<CustomerRelatedData>> wrappedDataStream = Observable.just( dataStream );

        // Create an accumulation object so that we can use "zip" to collapse items into a single unified Customer instance.
        CustomerZipEmulator accum = new CustomerZipEmulator();

        // Collapse the customer related data (Customer and Addresses) into a single customer with combined data.
        Observable<Customer> finalObservable = Observable
                .zip(wrappedDataStream, accum::collapseCustomerEvents)
                .last();

        return finalObservable;
    }

}
