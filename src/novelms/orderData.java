/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package novelms;

import java.util.Date;

/**
 *
 * @author USER
 */
public class orderData {

    private Integer id;
    private String customerName;
    private Integer customerNo;
    private String customerEmail;
    private String customerAddress;
    private String paymentType;
    private String confirmBy;
    private String totalPayment;
    private String staffName;
    private String staffUnit;
    private String salesComment;
    private Date date;

    public orderData(Integer id, String customerName, Integer customerNo, String customerEmail,
            String customerAddress, String paymentType, String confirmBy, String totalPayment,
            String staffName, String staffUnit, String salesComment, Date date) {

        this.id = id;
        this.customerName = customerName;
        this.customerNo = customerNo;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.paymentType = paymentType;
        this.confirmBy = confirmBy;
        this.totalPayment = totalPayment;
        this.staffName = staffName;
        this.staffUnit = staffUnit;
        this.salesComment = salesComment;
        this.date = date;

    }

    public Integer getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Integer getCustomerNo() {
        return customerNo;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getConfirmBy() {
        return confirmBy;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getStaffUnit() {
        return staffUnit;
    }

    public String getSalesComment() {
        return salesComment;
    }

    public Date getDate() {
        return date;
    }
}
