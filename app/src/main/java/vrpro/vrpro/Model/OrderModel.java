package vrpro.vrpro.Model;

/**
 * Created by Plooer on 6/25/2017 AD.
 */

public class OrderModel {

    private Integer ID;
    private String quatationNo;
    private String quatationDate;
    private String projectName;
    private String customerName;
    private String customerAdress;
    private String customerPhone;
    private String customerEmail;
    private Double totalPricel;
    private String remarks;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getQuatationDate() {
        return quatationDate;
    }

    public void setQuatationDate(String quatationDate) {
        this.quatationDate = quatationDate;
    }

    public String getQuatationNo() {
        return quatationNo;
    }

    public void setQuatationNo(String quatationNo) {
        this.quatationNo = quatationNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAdress() {
        return customerAdress;
    }

    public void setCustomerAdress(String customerAdress) {
        this.customerAdress = customerAdress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Double getTotalPricel() {
        return totalPricel;
    }

    public void setTotalPricel(Double totalPricel) {
        this.totalPricel = totalPricel;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
