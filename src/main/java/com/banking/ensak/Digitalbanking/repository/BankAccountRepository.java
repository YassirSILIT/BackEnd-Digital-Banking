package com.banking.ensak.Digitalbanking.repository;

import com.banking.ensak.Digitalbanking.entities.BankAccount;
import com.banking.ensak.Digitalbanking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
