package com.project.billingSoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.billingSoftware.model.Payment;
import com.project.billingSoftware.repository.PaymentRepository;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend requests
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping("/save")
    public Payment savePayment(@RequestBody Payment payment) {
        System.out.println("Received Payment Data: " + payment);
        return paymentRepository.save(payment);
    }


    @GetMapping("/all")
    public List<Payment> getAllPayments() { 
        return paymentRepository.findAll();
    }
}

