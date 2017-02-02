package info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge.service;

import info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge.ListProcedures;
import info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge.dto.OwnedProduct;
import rx.Observable;

/**
 * Created by Juan Mendez @juanmendezinfo on 10/3/2015.
 */
public class ProduceService {
    private final ListProcedures procedures = new ListProcedures();

    public Observable<OwnedProduct> fetchOwnedProducts(long customerId) {
        return procedures.toSelectOwnedProductObservable(customerId);
    }
}
