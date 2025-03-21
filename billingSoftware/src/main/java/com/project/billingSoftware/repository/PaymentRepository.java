package com.project.billingSoftware.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.billingSoftware.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
