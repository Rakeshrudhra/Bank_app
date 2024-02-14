
//package atm.view;
//
//import atm.controller.BankAccountController;
//import atm.model.BankAccountModel;
//
//import java.util.Scanner;
//
//public class BankAccountView {
//
//	
//	public static final String ANSI_RESET = "\u001B[0m";
//	public static final String ANSI_BLACK = "\u001B[30m";
//	public static final String ANSI_RED = "\u001B[31m";
//	public static final String ANSI_GREEN = "\u001B[32m";
//	public static final String ANSI_YELLOW = "\u001B[33m";
//	public static final String ANSI_BLUE = "\u001B[34m";
//	public static final String ANSI_PURPLE = "\u001B[35m";
//	public static final String ANSI_CYAN = "\u001B[36m";
//	public static final String ANSI_WHITE = "\u001B[37m";
//
//	public void display() {
//        BankAccountModel account1 = new BankAccountModel();
//        BankAccountController controller1 = new BankAccountController(account1, this);
//
//        while (true) {
//            System.out.println(ANSI_CYAN + "==========================================");
//            System.out.println("|" + ANSI_GREEN + "Welcome to the Bharath Bank Console App" + ANSI_CYAN + " |");
//            System.out.println("==========================================");
//            System.out.println("|" + ANSI_YELLOW + " Options:                              " + ANSI_CYAN + " |");
//            System.out.println("| 1. " + ANSI_PURPLE + "Sign Up                             " + ANSI_CYAN + "|");
//            System.out.println("| 2. " + ANSI_PURPLE + "Create Account                      " + ANSI_CYAN + "|");
//            System.out.println("| 3. " + ANSI_PURPLE + "Deposit                             " + ANSI_CYAN + "|");
//            System.out.println("| 4. " + ANSI_PURPLE + "Withdraw                            " + ANSI_CYAN + "|");
//            System.out.println("| 5. " + ANSI_PURPLE + "Check Balance                       " + ANSI_CYAN + "|");
//            System.out.println("| 6. " + ANSI_PURPLE + "Send Money                          " + ANSI_CYAN + "|");
//            System.out.println("==========================================");
//
//            Scanner sc = new Scanner(System.in);
//            try {
//                System.out.print(ANSI_GREEN + "Enter your choice (1-6): " + ANSI_RESET);
//                int n = sc.nextInt();
//
//                switch (n) {
//                    case 1:
//                        // New case for user signup
//                        System.out.println(ANSI_GREEN + "\nSigning Up..." + ANSI_RESET);
//                        System.out.print(ANSI_BLUE + "Enter a username: ");
//                        String username = sc.next();
//                        System.out.print(ANSI_BLUE + "Enter an email: ");
//                        String email = sc.next();
//                        System.out.print(ANSI_BLUE + "Enter a password: ");
//                        String password = sc.next();
//                        controller1.signUp(username, email, password);
//                        break;
//                    case 2:
//                        // Existing case for creating an account...
//                        System.out.println(ANSI_GREEN + "\nCreating an Account..." + ANSI_RESET);
//                        System.out.print(ANSI_BLUE + "Enter your name: ");
//                        String name = sc.next();
//                        System.out.print(ANSI_BLUE + "Enter the Adhar_Number: ");
//                        String adhar_Number = sc.next();
//                        System.out.print(ANSI_BLUE + "Enter the phone_number: ");
//                        String phone_Number = sc.next();
//                        System.out.print(ANSI_BLUE + "Enter initial deposit amount: $");
//                        double initialDeposit = sc.nextDouble();
//                        controller1.createAccount(name, adhar_Number, phone_Number, initialDeposit);
//                        break;
//                    case 3:
//                        // Existing case for depositing money...
//                        System.out.println(ANSI_GREEN + "\nDepositing Money..." + ANSI_RESET);
//                        System.out.print(ANSI_BLUE + "Enter account number: ");
//                        int depositAccountNumber = sc.nextInt();
//                        System.out.print(ANSI_BLUE + "Enter amount to deposit: $");
//                        double depositAmount = sc.nextDouble();
//                        controller1.deposit(depositAccountNumber, depositAmount);
//                        break;
//                    case 4:
//                        // Existing case for withdrawing money...
//                        System.out.println(ANSI_GREEN + "\nWithdrawing Money..." + ANSI_RESET);
//                        System.out.print(ANSI_BLUE + "Enter account number: ");
//                        int withdrawAccountNumber = sc.nextInt();
//                        System.out.print(ANSI_BLUE + "Enter amount to withdraw: $");
//                        double withdrawAmount = sc.nextDouble();
//                        controller1.withdraw(withdrawAccountNumber, withdrawAmount);
//                        break;
//                    case 5:
//                        // Existing case for checking balance...
//                        System.out.println(ANSI_GREEN + "\nChecking Balance..." + ANSI_RESET);
//                        System.out.print(ANSI_BLUE + "Enter account number: ");
//                        controller1.checkBalance(sc.nextInt());
//                        break;
//                    case 6:
//                        // Existing case for sending money...
//                        System.out.println(ANSI_GREEN + "\nSend Money..." + ANSI_RESET);
//                        System.out.print(ANSI_BLUE + "Enter your account number: ");
//                        int accountNumber1 = sc.nextInt();
//                        System.out.print(ANSI_BLUE + "Enter the sender account number: ");
//                        int senderAccNumber = sc.nextInt();
//                        System.out.print(ANSI_BLUE + "Enter the amount you want to send: $");
//                        double sendAmount = sc.nextDouble();
//                        controller1.sendMoney(accountNumber1, senderAccNumber, sendAmount);
//                        break;
//                    default:
//                        System.out.println(ANSI_RED + "Invalid choice. Please choose a number between 1 and 6." + ANSI_RESET);
//                }
//            } catch (java.util.InputMismatchException e) {
//                System.out.println(ANSI_RED + "Invalid input. Please enter a valid number." + ANSI_RESET);
//                sc.nextLine(); // Consume the invalid input to avoid an infinite loop
//            }
//        }
//    }
//
//	public void displayBalance(double balance) {
//		System.out.println(ANSI_GREEN + "\nCurrent Balance: $" + balance + ANSI_RESET);
//	}
//
//	public void displayMessage(String message) {
//		System.out.println(ANSI_YELLOW + "\n" + message + ANSI_RESET);
//	}
//}
