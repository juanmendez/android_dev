package info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge.dto;

/**
 * Created by Juan Mendez @juanmendezinfo on 10/3/2015.
 */
public class OwnedProduct  extends CustomerRelatedData {

    private long productId;

    public OwnedProduct() {
    }

    public OwnedProduct(long customerId, long productId) {
        super(customerId);
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
