package info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge;

import java.util.ArrayList;

import info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge.dto.Address;
import info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge.dto.Customer;
import info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge.dto.OwnedProduct;
import info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge.dto.Product;

/**
 * Created by Juan Mendez @juanmendezinfo on 10/3/2015.
 */
public class DBStorage
{
    private static DBStorage db;
    Boolean opened = false;

    public static DBStorage getDb(){
        if( db == null ){
            db = new DBStorage();
        }

        return db;
    }

    public static ConnectionSubscription createSubscription(){
        return new ConnectionSubscription(db);
    }

    private ArrayList<Customer>  customers = new ArrayList<Customer>();
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<OwnedProduct> ownedProducts = new ArrayList<OwnedProduct>();
    private ArrayList<Address> addresses = new ArrayList<Address>();

    public void open(){
      opened = true;
      createCustomers();
       createProducts();
        createAddresses();
        createOwnedProducts();
      System.out.println( "dbstorage open" );
    }

    public void close(){
        opened = false;
        clearOwnedProducts();
        clearAddresses();
        clearOwnedProducts();
        clearCustomers();
        clearProducts();
        System.out.println( "dbstorage close" );
    }

    private void createCustomers(){
        customers.add( new Customer(1, "reledge"));
        customers.add( new Customer(2, "dvader"));
        customers.add(new Customer(3, "lskyskimmer"));
    }

    private void clearCustomers(){
        customers.clear();
    }

    private void createProducts(){
        products.add(new Product(1, "Rubber Baseball Bat"));
        products.add(new Product(2, "+1 Fish Sword of Withering"));
        products.add(new Product(3, "Armband Floaties of Dispair"));
        products.add(new Product(4, "Flowpot of Juiciness"));
    }

    private void clearProducts(){
        products.clear();
    }

    private void createOwnedProducts(){
        ownedProducts.add(new OwnedProduct(1 , 2));
        ownedProducts.add(new OwnedProduct(1, 3));
        ownedProducts.add(new OwnedProduct(2, 1));
        ownedProducts.add(new OwnedProduct(2, 3));
        ownedProducts.add(new OwnedProduct( 2, 4));
        ownedProducts.add(new OwnedProduct( 3, 4));
        ownedProducts.add(new OwnedProduct( 3, 1));
    }

    private void clearOwnedProducts()
    {
        ownedProducts.clear();
    }

    private void createAddresses(){

        addresses.add( new Address(1 , 1 , "123 South North Street" , "" , "Fort Wayne" , "TX" , "76222"));
        addresses.add( new Address(2 , 1 , "7788 Fourth Blvd" , "" , "Carpentry" , "TX" , "76122"));
        addresses.add( new Address(3 , 2 , "1313 Planet of Doom Rd" , "Apt #33" , "Aldeberon" , "OH" , "54332"));
        addresses.add( new Address(4 , 2 , "4433 Sandy Hills Road" , "" , "Tatto" , "VA" , "94231"));
        addresses.add( new Address(5 , 3 , "91122 Dirt Pile Way" , "" , "Tatto" , "VA" , "94231"));
    }

    private void clearAddresses(){
        addresses.clear();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<OwnedProduct> getOwnedProducts() {
        return ownedProducts;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

}
