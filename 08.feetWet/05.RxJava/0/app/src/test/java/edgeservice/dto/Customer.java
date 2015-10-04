package edgeservice.dto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edgeservice.DBStorage;
import rx.Observable;

/**
 * Created by Juan on 10/3/2015.
 */
public class Customer extends CustomerRelatedData {

    private String username;

    private final List<Address> addresses = new ArrayList<>();
    private final List<OwnedProduct> ownedProducts = new ArrayList<>();

    public Customer() {
    }

    public Customer(long id, String username) {
        super(id);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void addAddress(Address a) {
        addresses.add(a);
    }

    public List<Address> getAddressList() {
        return Collections.unmodifiableList(addresses);
    }

    public void addOwnedProduct(OwnedProduct p) {
        ownedProducts.add(p);
    }

    public List<OwnedProduct> getOwnedProductList() {
        return Collections.unmodifiableList(ownedProducts);
    }

    public Observable<Product> getProducts(){
        final List<OwnedProduct> ownedProducts = getOwnedProductList();

        return Observable.from(DBStorage.getDb().getProducts() ).
                filter( (product) -> {

                    for( OwnedProduct ownedProduct: ownedProducts ){

                        if( ownedProduct.getProductId() == product.getProductId())
                            return true;
                    }

                    return false;
                });
    }
}