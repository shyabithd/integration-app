package integration.model;

public class OutputEntry {

    private String userPid;
    private String orderPid;
    private String supplierPid;

    public String getOrderPid() {
        return orderPid;
    }

    public String getSupplierPid() {
        return supplierPid;
    }

    public String getUserPid() {
        return userPid;
    }

    public void setOrderPid(String orderPid) {
        this.orderPid = orderPid;
    }

    public void setSupplierPid(String supplierPid) {
        this.supplierPid = supplierPid;
    }

    public void setUserPid(String userPid) {
        this.userPid = userPid;
    }
}
