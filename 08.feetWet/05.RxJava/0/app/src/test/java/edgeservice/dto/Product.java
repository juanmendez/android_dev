package edgeservice.dto;

/**
 * Created by Juan on 10/3/2015.
 */
public class Product {
    private long productId;
    private String productName;

    public Product(){

    }

    public Product( long productId, String productName ){
        this.productId = productId;
        this.productName = productName;
    }

    public long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
}
