package edgeservice.service;

import edgeservice.ListProcedures;
import edgeservice.dto.OwnedProduct;
import rx.Observable;

/**
 * Created by Juan on 10/3/2015.
 */
public class ProduceService {
    private final ListProcedures procedures = new ListProcedures();

    public Observable<OwnedProduct> fetchOwnedProducts(long customerId) {
        return procedures.toSelectOwnedProductObservable(customerId);
    }
}
