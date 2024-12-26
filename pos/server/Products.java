package pos.src.com.comp4680.pos.server;

import java.util.Date;

public class Products {
    private int id;
    private String name;
    private float currentBid;
    private float targetPrice;
    private Date lastUpdateDtm;

    public Products(int id, String name, float currentBid, float targetPrice) {
        this.id = id;
        this.name = name;
        this.currentBid = currentBid;
        this.targetPrice = targetPrice;
        this.lastUpdateDtm = new Date();
    }

    public String toString() {
        return String.format("id: %d, name: %s, currentBid: %f, targetPrice: %f, lastUpdateDtm: %s", id, name,
                currentBid, targetPrice, lastUpdateDtm.toString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getcurrentBid() {
        return currentBid;
    }

    public void setcurrentBid(float currentBid) {
        this.currentBid = currentBid;
    }

    public float getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(float targetPrice) {
        this.targetPrice = targetPrice;
    }

    public Date getLastUpdateDtm() {
        return lastUpdateDtm;
    }

    public void setLastUpdateDtm(Date lastUpdateDtm) {
        this.lastUpdateDtm = lastUpdateDtm;
    }
}