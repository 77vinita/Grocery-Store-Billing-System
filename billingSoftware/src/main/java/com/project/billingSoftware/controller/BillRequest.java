package com.project.billingSoftware.controller;

import java.util.List;

public class BillRequest {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private Double totalAmount;
    private String paymentMethod;
    private List<Long> productIds;
    private List<Integer> quantities;

    // ✅ Constructor (No-Arg)
    public BillRequest() {}

    // ✅ Constructor (All-Args)
    public BillRequest(String customerName, String customerEmail,String customerPhone ,Double totalAmount, String paymentMethod, List<Long> productIds, List<Integer> quantities) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.productIds = productIds;
        this.quantities = quantities;
    }

    // ✅ Getters and Setters
    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }
}
