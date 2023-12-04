package com.banking.ensak.Digitalbanking;

import com.banking.ensak.Digitalbanking.dtos.BankAccountDTO;
import com.banking.ensak.Digitalbanking.dtos.CurrentBankAccountDTO;
import com.banking.ensak.Digitalbanking.dtos.CustomerDTO;
import com.banking.ensak.Digitalbanking.dtos.SavingBankAccountDTO;
import com.banking.ensak.Digitalbanking.entities.*;
import com.banking.ensak.Digitalbanking.enums.AccountStatus;
import com.banking.ensak.Digitalbanking.enums.OperationType;
import com.banking.ensak.Digitalbanking.exceptions.BalanceNotSufficientException;
import com.banking.ensak.Digitalbanking.repository.AccountOperationRepository;
import com.banking.ensak.Digitalbanking.repository.BankAccountRepository;
import com.banking.ensak.Digitalbanking.repository.CustomerRepository;
import com.banking.ensak.Digitalbanking.service.BankAccountService;
import com.banking.ensak.Digitalbanking.service.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankingApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
		return args -> {
			Stream.of("Adam", "Ahmed", "Farah").forEach(name -> {
				CustomerDTO customerDTO = new CustomerDTO();
				customerDTO.setName(name);
				customerDTO.setEmail(name + "@outlook.com");
				bankAccountService.saveCustomer(customerDTO);
			});
			bankAccountService.listCustomers().forEach(customer -> {
				bankAccountService.saveCurrentBankAccount(Math.random() * 60000, 7000, customer.getId());
				bankAccountService.saveSavingBankAccount(Math.random() * 50000, 3.4, customer.getId());
			});
			List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
			for (BankAccountDTO bankAccount : bankAccounts) {
				for (int i = 0; i < 10; i++) {
					String accountId;
					if (bankAccount instanceof SavingBankAccountDTO) {
						accountId = ((SavingBankAccountDTO) bankAccount).getId();
					} else {
						accountId = ((CurrentBankAccountDTO) bankAccount).getId();
					}
					bankAccountService.credit(accountId, 1000 + Math.random() * 20000, "Credit");
					bankAccountService.debit(accountId, 1000 + Math.random() * 8000, "Debit");
				}
			}
		};
	}

	//@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository,
							AccountOperationRepository accountOperationRepository){
		return args -> {
			Stream.of("yassir","ilias","hicham").forEach(name->{
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				customerRepository.save(customer);
			});

			customerRepository.findAll().forEach(cust ->{
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()*9000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setOverDraft(9000.0);
				currentAccount.setCustomer(cust);
				bankAccountRepository.save(currentAccount);

				SavingAccount savingAccount= new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()*9000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setInterestRate(5.5);
				savingAccount.setCustomer(cust);
				bankAccountRepository.save(savingAccount);

				});

			bankAccountRepository.findAll().forEach(acc->{
				for (int i = 0; i<10; i++){
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random()*10000);
					accountOperation.setType(Math.random()>0.5? OperationType.DEBIT : OperationType.CREDIT);
					accountOperation.setBankAccount(acc);
					accountOperationRepository.save(accountOperation);
				}
			});
		};
	}

}
