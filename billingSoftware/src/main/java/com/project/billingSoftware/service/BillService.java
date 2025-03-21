package com.project.billingSoftware.service;

import com.project.billingSoftware.model.Bill;
import com.project.billingSoftware.repository.BillRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    @Transactional
    public Bill saveBill(Bill bill) {
        return billRepository.save(bill); // Save and return Bill object
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }
    
    public Optional<Bill> getBillById(Long id) {
        return billRepository.findById(id);
    }

    public void deleteBillByNumber(String billNumber) {
        Optional<Bill> bill = billRepository.findByBillNumber(billNumber);
        if (bill.isPresent()) {
            billRepository.delete(bill.get());
        } else {
            throw new RuntimeException("Bill not found with bill number: " + billNumber);
        }
    }

}
