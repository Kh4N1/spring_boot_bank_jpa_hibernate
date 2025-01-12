package org.bank;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bank.model.AccountType;
import org.bank.model.User;
import org.bank.model.Account;
import org.bank.model.enums.Currency;
import org.bank.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class BankApplication {

  private final UserService userService;
  private final AccountService accountService;
  private final TransactionService transactionService;
  private final CardService cardService;
  private final BranchService branchService;
  private final AccountTypeService accountTypeService;

  public static void main(String[] args) {
    SpringApplication.run(BankApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner() {
    return args -> {
      Scanner scanner = new Scanner(System.in);
      boolean running = true;

      while (running) {
        printMainMenu();
        String choice = scanner.nextLine();

        switch (choice) {
          case "1" -> registerUser(scanner);
          case "2" -> createAccount(scanner);
          case "3" -> transfer(scanner);
          case "4" -> checkBalance(scanner);
          case "5" -> listTransactions(scanner);
          case "0" -> {
            running = false;
            System.out.println("Thank you for using our banking system. Goodbye!");
            System.exit(0);
          }
          default -> System.out.println("Invalid option, please try again.");
        }
      }
    };
  }

  private void printMainMenu() {
    System.out.println("\n=== Bank System Menu ===");
    System.out.println("1. Register New User");
    System.out.println("2. Create New Account");
    System.out.println("3. Transfer Money");
    System.out.println("4. Check Balance");
    System.out.println("5. List Transactions");
    System.out.println("0. Exit");
    System.out.print("Choose an option: ");
  }

  private void registerUser(Scanner scanner) {
    try {
      System.out.print("Enter first name: ");
      String firstName = scanner.nextLine();

      System.out.print("Enter last name: ");
      String lastName = scanner.nextLine();

      System.out.print("Enter email: ");
      String email = scanner.nextLine();

      System.out.print("Enter password: ");
      String password = scanner.nextLine();

      User user =
          User.builder()
              .firstName(firstName)
              .lastName(lastName)
              .email(email)
              .password(password)
              .build();

      userService.save(user);
      System.out.println("User registered successfully!");

    } catch (Exception e) {
      System.out.println("Error registering user: " + e.getMessage());
    }
  }

  private void createAccount(Scanner scanner) {
    try {
      System.out.print("Enter user email: ");
      String email = scanner.nextLine();

      User user = userService.findByEmail(email);

      System.out.println("\nSelect account type:");
      System.out.println("1. Savings Account");
      System.out.println("2. Checking Account");

      String accountTypeChoice = scanner.nextLine();
      AccountType accountType;

      switch (accountTypeChoice) {
        case "1" -> accountType = getOrCreateAccountType("SAVINGS", "Savings Account", 100.0, 2.5);
        case "2" -> accountType = getOrCreateAccountType("CHECKING", "Checking Account", 0.0, 0.0);
        default -> throw new IllegalArgumentException("Invalid account type choice");
      }

      System.out.println("\nSelect currency:");
      System.out.println("1. USD");
      System.out.println("2. EUR");
      System.out.println("3. IRR");

      String currencyChoice = scanner.nextLine();
      Currency currency =
          switch (currencyChoice) {
            case "1" -> Currency.USD;
            case "2" -> Currency.EUR;
            case "3" -> Currency.IRR;
            default -> throw new IllegalArgumentException("Invalid currency choice");
          };

      Account account =
          Account.builder()
              .user(user)
              .balance(new BigDecimal("1000000.00"))
              .currency(currency)
              .accountNumber(generateAccountNumber())
              .isActive(true)
              .accountType(accountType)
              .build();

      accountService.save(account);
      System.out.println("Account created successfully with number: " + account.getAccountNumber());

    } catch (Exception e) {
      System.out.println("Error creating account: " + e.getMessage());
    }
  }

  private void transfer(Scanner scanner) {
    try {
      System.out.print("Enter source account number: ");
      String fromAccount = scanner.nextLine();

      System.out.print("Enter target account number: ");
      String toAccount = scanner.nextLine();

      System.out.print("Enter amount: ");
      BigDecimal amount = new BigDecimal(scanner.nextLine());

      accountService.transfer(fromAccount, toAccount, amount);
      System.out.println("Transfer completed successfully!");

    } catch (Exception e) {
      System.out.println("Error during transfer: " + e.getMessage());
    }
  }

  private void checkBalance(Scanner scanner) {
    try {
      System.out.print("Enter account number: ");
      String accountNumber = scanner.nextLine();

      Account account = accountService.findByAccountNumber(accountNumber);
      System.out.println("Current balance: " + account.getBalance() + " " + account.getCurrency());

    } catch (Exception e) {
      System.out.println("Error checking balance: " + e.getMessage());
    }
  }

  private void listTransactions(Scanner scanner) {
    try {
      System.out.print("Enter account number: ");
      String accountNumber = scanner.nextLine();

      Account account = accountService.findByAccountNumber(accountNumber);
      var transactions = transactionService.getAccountTransactions(account.getId());

      System.out.println("\nTransaction History:");
      transactions.forEach(
          transaction -> {
            String type =
                transaction.getSourceAccount().getId().equals(account.getId()) ? "DEBIT" : "CREDIT";
            System.out.printf(
                "%s: %s %s - %s%n",
                type,
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getCreatedAt());
          });

    } catch (Exception e) {
      System.out.println("Error listing transactions: " + e.getMessage());
    }
  }

  private String generateAccountNumber() {
    return String.format("%016d", System.nanoTime());
  }

  private AccountType getOrCreateAccountType(
      String name, String description, Double minBalance, Double interestRate) {
    try {
      return accountTypeService.findByName(name);
    } catch (EntityNotFoundException e) {
      AccountType accountType =
          AccountType.builder()
              .name(name)
              .description(description)
              .minimumBalance(minBalance)
              .interestRate(interestRate)
              .build();
      return accountTypeService.save(accountType);
    }
  }
}
