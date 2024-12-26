/* Total 20 marks */
package pos.src.com.comp4680.pos.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AuctionEvents {

    private List<Products> products = new ArrayList<>();
    private int currentProductId = 0;
    private int maxProductId = 0;
    private ReentrantLock lock;
    private Condition isCurrentBidCompleted;

    public AuctionEvents() {
        this.initProducts();
        this.currentProductId = products.stream().mapToInt(Products::getId).min().orElseThrow();
        this.maxProductId = products.stream().mapToInt(Products::getId).max().orElseThrow();
        this.lock = ; /* 1. How to initialize the ReentrantLock (1 marks) */
        this.isCurrentBidCompleted = ; /* 2. How to initialize the Condition (1 marks) */
    }

    /*
     * A method to get product with id equals to this.currentProductId. If there is
     * no product with id equals to the this.currentProductId, that's mean the
     * auction is over, return null
     */
    public /* 3. How to thread safe this method? (1 marks) */ Products getCurrentProduct() {
        return products.stream().filter(p -> p.getId() == this.currentProductId).findFirst().orElse(null);
    }

    /* 4. Add thread safe in correct place inside this method (4 marks) */
    /* 5. Add thread control in correct place inside this method (2 marks) */
    /*
     * 6. Add distributed concurreny control in correct place inside this
     * method, similar to OCC distributed control. (4 marks)
     * Hints: any fields in the Products can make use of?
     * Hints: !!!!!! Check befor update the bid!!!!!!!!!!!!!!!!!
     */
    /*
     * A method to place bid on a product, firstly check whether the target price is
     * reached. If the target price is reached, return false. Otherwise, check
     * whether the bid outdated or not by using checkTimestamp(). If the bid is
     * outdated, return false. Otherwise, update the product current bid and return
     * true
     */
    public boolean placeBid(int id, float bid, Date originalTimestamp) {
        try {
            // Check if the target price is reached before client placing the bid
            if (this.isTargetPriceReached(id)) {
                return false;
            }

            // Update the current bid
            products.stream().filter(p -> p.getId() == id).forEach(p -> {
                p.setcurrentBid(bid);
                p.setLastUpdateDtm(new Date());
            });

            // Check if the target price is reached, if so, set the next product
            if (this.isTargetPriceReached(id)) {
                this.currentProductId++;
            }

            // return true for successful bid
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
        }
    }// end of placeBid()

    /* 7. Add thread safe in correct place inside this method (2 marks) */
    /*
     * 8. Add thread control in correct place inside this method (5 marks)
     * Hints: What is the condition? Check the condition name again
     */
    /**
     * Waits for the bidding process to complete up to the specified target product
     * ID. If the current product ID is less than the target product ID, it waits
     * for the current bid to complete. Once the condition is met, returns the
     * current product. If an exception occurs, it logs the error and returns null.
     *
     * @param targetProductId the desired product ID to wait for
     * @return the current product if successful, or null if an exception occurs
     */
    public Products abortBidAndWaitForProducts(int targetProductId) {
        // Validates the target product ID before proceeding
        targetProductId = this.validateProductId(targetProductId);

        try {

            return this.getCurrentProduct();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    /****************** !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ******************/
    /****************** DON'T CHANGE ANYTHING BEYOND THIS LINE ******************/
    /****************** !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ******************/
    /*
     * A method to check whether the timestamp in argument is earlier than last
     * update date time of the product with id equals to the id in arguments
     * 
     * @param id the id of the product
     * 
     * @param timestamp the original timestamp from the client
     * 
     * @return true if the timestamp in argument is earlier than last update date
     * time of the product with id equals to the id in arguments, false otherwise
     */
    private boolean isOutDated(int id, Date timestamp) {
        return this.products.stream().anyMatch(p -> p.getId() == id && p.getLastUpdateDtm().after(timestamp));
    }

    private boolean isTargetPriceReached(int id) {
        return this.products.stream().anyMatch(p -> p.getId() == id && p.getcurrentBid() >= p.getTargetPrice());
    }

    public List<Products> getProducts() {
        return products;
    }

    private int validateProductId(int targetProductId) {
        int validProductId = targetProductId;
        // place safe to set the targetProductId to the maxProductId
        if (maxProductId < targetProductId) {
            validProductId = maxProductId;
        } else {
            // place safe to set the targetProductId to the currentProductId if
            // targetProductId is not in the list
            final int _targetProductId = targetProductId;
            if (products.stream().noneMatch(p -> p.getId() == _targetProductId)) {
                validProductId = currentProductId + 1;
            }
        }
        return validProductId;
    }

    public boolean isAllCompleted() {
        return currentProductId > maxProductId;
    }

    private void initProducts() {
        products.add(new Products(1, "Rolex Daytona", 0.0f, 100000.0f));
        products.add(new Products(2, "Audemars Piguet Royal Oak", 0.0f, 150000.0f));
        products.add(new Products(3, "Vacheron Constantin Overseas", 0.0f, 200000.0f));
        products.add(new Products(4, "Patek Philippe Nautilus", 0.0f, 500000.0f));
        products.add(new Products(5, "Richard Mille RM88", 0.0f, 1000000.0f));
    }// end of initProducts
}