package integration.model;

public class FileEntry {

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String supplier_id;
    private String credit_card_number;
    private String credit_card_type;
    private String order_id;
    private String product_pid;
    private String shipping_address;
    private String country;
    private String date_created;
    private int quantity;
    private String full_name;
    private String order_status;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getCredit_card_number() {
        return credit_card_number;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCredit_card_type() {
        return credit_card_type;
    }

    public String getCountry() {
        return country;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getProduct_pid() {
        return product_pid;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setCredit_card_type(String credit_card_type) {
        this.credit_card_type = credit_card_type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setProduct_pid(String product_pid) {
        this.product_pid = product_pid;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCredit_card_number(String credit_card_number) {
        this.credit_card_number = credit_card_number;
    }


}
