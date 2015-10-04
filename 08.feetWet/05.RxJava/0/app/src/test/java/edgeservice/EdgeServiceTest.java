package edgeservice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import edgeservice.dto.Address;
import edgeservice.dto.Customer;
import edgeservice.dto.OwnedProduct;
import edgeservice.service.CustomerService;
import info.juanmendez.android.hellorx.BuildConfig;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Juan on 10/3/2015.
 */
@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21)
public class EdgeServiceTest {
    DBStorage db;

    @Before
    public void doBefore(){
        db = DBStorage.getDb();
    }

    @Test
    public void testDB(){
        db.open();

        try {
            CustomerService customerService = new CustomerService();

            // Create a monitor so that we don't exit the application too soon.
            Object waitMonitor = new Object();
            synchronized (waitMonitor) {

                // Ask the CustomerService to fetch customer data,
                // but also get addresses and owned products
                // from the other services.
                Observable<Customer> customerData = customerService.fetchCustomerWithAddressesAndOwnedProducts(1);

                customerData
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(
                                // For each returned customer (there should be only one), emit information
                                // about the customer.
                                (customer) -> { // onNext
                                    System.out.println("------------------------------------------------------------");
                                    System.out.println(customer.getCustomerId() + " " + customer.getUsername());
                                    System.out.println();

                                    for (Address address : customer.getAddressList()) {
                                        System.out.println("    " + address.getAddress1());
                                        System.out.println("    " + address.getCity() + ", " + address.getState() + "  " + address.getPostalCode());
                                        System.out.println();
                                    }

                                    customer.getProducts().subscribe(
                                            product -> {
                                                System.out.println( " Product: "  + product.getProductId() + ", " + product.getProductName() );
                                            }
                                    );


                                    System.out.println();
                                    System.out.println("------------------------------------------------------------");
                                },
                                (t) -> { // onError
                                    t.printStackTrace();
                                },
                                () -> { // onCompleted
                                    synchronized (waitMonitor) {
                                        waitMonitor.notify();
                                    }
                                });

                // This effectively waits for the onCompleted call to signal
                waitMonitor.wait();
            }

        }catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

}
