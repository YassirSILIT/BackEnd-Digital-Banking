package com.banking.ensak.Digitalbanking.service;

import com.banking.ensak.Digitalbanking.dtos.*;
import com.banking.ensak.Digitalbanking.entities.BankAccount;
import com.banking.ensak.Digitalbanking.entities.CurrentAccount;
import com.banking.ensak.Digitalbanking.entities.Customer;
import com.banking.ensak.Digitalbanking.entities.SavingAccount;
import com.banking.ensak.Digitalbanking.exceptions.BalanceNotSufficientException;

import java.util.List;

public interface BankAccountService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId);
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId);
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId);
    void debit(String accountId, double amount, String description) throws BalanceNotSufficientException;
    void credit(String accountId, double amount, String description);
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BalanceNotSufficientException;
    List<BankAccountDTO> bankAccountList();
    CustomerDTO getCustomer(Long customerId);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long customerId);
    List<AccountOperationDTO> accountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size);
    List<CustomerDTO> searchCustomers(String keyword);
}
