import java.util.Scanner;
import java.util.Vector;

import javax.sound.sampled.SourceDataLine;

public class SmartBankingApp3 {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        final String CLEAR = "\033[H\033[2J";
        final String COLOR_BLUE_BOLD = "\033[34;1m";
        final String COLOR_RED_BOLD = "\033[31;1m";
        final String COLOR_GREEN_BOLD = "\033[33;1m";
        final String RESET = "\033[0m";

        final String DASHBOARD = "Welcome to Smart Banking System";
        final String OPEN_ACCOUNT = "Open New Account";
        final String DEPOSIT_MONEY = "Deposit Money";
        final String WITHDRAW_MONEY = "Withdraw Money";
        final String TRANSFER_MONEY = "Transfer Money";
        final String CHECK_ACCOUNT_BALANCE = "Check Accout Balance";
        final String DELETE_ACCOUNT = "Drop Existing Account";
        final String EXIT = "Exit";

        final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
        final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);

        String screen = DASHBOARD;
        Vector<String> accountNumbers = new Vector<>();
        Vector<String> accountNames = new Vector<>();
        Vector<Double> accountBalances = new Vector<>();

        String accountNumber;
        double accountBalance;
        boolean flag;
        int index;
        int count =0;

        do {
            final String APP_TITLE = String.format("%s%s%s", COLOR_BLUE_BOLD, screen, RESET);

            System.out.println(CLEAR);
            System.out.println("\t" + APP_TITLE + "\n");

            
            switch (screen) {
// ==========================================================================================================================================
                case DASHBOARD:
// ==========================================================================================================================================
                    System.out.println("\t[1]. Open New Account: ");
                    System.out.println("\t[2]. Deposit Money: ");
                    System.out.println("\t[3]. Withdraw Money: ");
                    System.out.println("\t[4]. Transfer Money: ");
                    System.out.println("\t[5]. Check Account Balance: ");
                    System.out.println("\t[6]. Drop Existing Account: ");
                    System.out.println("\t[7]. Exit: ");
                    System.out.print("\tEnter the Option Number to Continue: ");
                    int option = scanner.nextInt();
                    scanner.nextLine();
                    // ------------------------------------------------------------------------------------------------------------------------------------------
                    switch (option) {
                        case 1:
                            screen = OPEN_ACCOUNT;
                            break;
                        case 2:
                            screen = DEPOSIT_MONEY;
                            break;
                        case 3:
                            screen = WITHDRAW_MONEY;
                            break;
                        case 4:
                            screen = TRANSFER_MONEY;
                            break;
                        case 5:
                            screen = CHECK_ACCOUNT_BALANCE;
                            break;
                        case 6:
                            screen = DELETE_ACCOUNT;
                            break;
                        case 7:
                            System.out.println(CLEAR);
                            System.exit(0);
                            break;
                        default:
                            continue;
                    }
// ------------------------------------------------------------------------------------------------------------------------------------------
                    break;
// ==========================================================================================================================================
                case OPEN_ACCOUNT:
// ==========================================================================================================================================
                    boolean valid = true;
                    do {
                        accountNumber = String.format("SDB-%05d", (accountNumbers.size() + 1 + count));
                        System.out.printf("\t%s \n", accountNumber);
                        String accountName;
                        // Name Validation
                        do {
                            valid = true;
                            System.out.print("\tEnter the Account Name: ");
                            accountName = scanner.nextLine().strip();
                            if (accountName.isBlank()) {
                                System.out.printf(ERROR_MSG, "Customer name can't be empty");
                                valid = false;
                                continue;
                            }
                            for (int i = 0; i < accountName.length(); i++) {
                                if (!(Character.isLetter(accountName.charAt(i))
                                        || Character.isSpaceChar(accountName.charAt(i)))) {
                                    System.out.printf(ERROR_MSG, "Invalid name");
                                    valid = false;
                                    break;
                                }
                            }
                        } while (!valid);

                        double initialDeposit = 0;

                        do {
                            valid = true;
                            System.out.print("\tEnter the Initial Deposit: ");
                            initialDeposit = scanner.nextDouble();
                            scanner.nextLine();

                            if (initialDeposit < 500) {
                                valid = false;
                                System.out.printf(ERROR_MSG, "Minimum Deposit Should be more than Rs. 500/=");
                                continue;
                            } else {
                                System.out.printf("\tUpdated Account Balance is %.2f", initialDeposit);

                            }

                        } while (!valid);

                        accountNumbers.add(accountNumber);
                        accountNames.add(accountName);
                        accountBalances.add(initialDeposit);

                        System.out.println();
                        System.out.printf(SUCCESS_MSG,
                                String.format("%s:%s has been saved successfully", accountNumber, accountName));
                        System.out.print("\tDo you want to continue adding (Y/n)? ");
                        if (scanner.nextLine().strip().toUpperCase().equals("Y"))
                            continue;
                        screen = DASHBOARD;
                        break;

                    } while (valid);
                    break;
// ==========================================================================================================================================
                case DEPOSIT_MONEY:
// ==========================================================================================================================================
                    valid = true;
                    label2:
                    do {
                        System.out.print("\tEnter Account Number: ");
                        accountNumber = scanner.nextLine().strip();
                        flag = false;

                            label1:
                            do {
                            if(accountNumber.isEmpty()){
                            System.out.printf(ERROR_MSG, "Account Number can't be empty");
                            break;
                            }

                            if(accountNumber.length() !=9 || !accountNumber.startsWith("SDB-")){
                            System.out.printf(ERROR_MSG, "Invalid Format-1");
                            break;
                            }
                            for (int i = 4; i <= 8; i++) {
                                if(!Character.isDigit(accountNumber.charAt(i))){
                                System.out.printf(ERROR_MSG, "Invalid Format-2");
                                break label1;
                                }
                                                               
                            }
                            if(!accountNumbers.contains(accountNumber)){
                            System.out.printf(ERROR_MSG, "Not Found");
                            break;
                            }else {
                                flag = true;
                                break;
                            }

                            } while (true);

                        if(!flag){

                        System.out.print("\tDo you want to try again..?");
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            else {
                                screen = DASHBOARD;
                                break label2;
                            }
                        }

                        index = accountNumbers.indexOf(accountNumber);

                        System.out.printf("\tCurrent Balance is Rs. %,.2f\n", accountBalances.get(index));
                        double newDeposit;
                        do{
                            System.out.print("\tEnter Deposit Amount :");
                            newDeposit = scanner.nextDouble();
                            scanner.nextLine();

                            if (newDeposit>500) break;
                            else{
                                System.out.printf(ERROR_MSG, "Minimum Deposit should be Rs.500/=");                 
                            }
                        }while (true);

                        accountBalance = accountBalances.get(index) + newDeposit;

                        accountBalances.set(index,accountBalance);

                        System.out.printf("\tNew Balance is Rs. %,.2f\n",accountBalances.get(index));

                        System.out.print("\tDo You Want to deposit again: ");
                        if(scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                        else{
                            screen = DASHBOARD;
                            break;
                        }

                    } while (valid);
                    break;
// ==========================================================================================================================================
                case WITHDRAW_MONEY:
// ==========================================================================================================================================

                   valid = true;
                    label2:
                    do {
                        System.out.print("\tEnter Account Number: ");
                        accountNumber = scanner.nextLine().strip();
                        flag = false;

                            label1:
                            do {
                            if(accountNumber.isEmpty()){
                            System.out.printf(ERROR_MSG, "Account Number can't be empty");
                            break;
                            }

                            if(accountNumber.length() !=9 || !accountNumber.startsWith("SDB-")){
                            System.out.printf(ERROR_MSG, "Invalid Format-1");
                            break;
                            }
                            for (int i = 4; i <= 8; i++) {
                                if(!Character.isDigit(accountNumber.charAt(i))){
                                System.out.printf(ERROR_MSG, "Invalid Format-2");
                                break label1;
                                }
                                                               
                            }
                            if(!accountNumbers.contains(accountNumber)){
                            System.out.printf(ERROR_MSG, "Not Found");
                            break;
                            }else {
                                flag = true;
                                break;
                            }

                            } while (true);

                        if(!flag){

                        System.out.print("\tDo you want to try again..?");
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            else {
                                screen = DASHBOARD;
                                break label2;
                            }
                        }

                        index = accountNumbers.indexOf(accountNumber);

                        System.out.printf("\tCurrent Balance is Rs. %,.2f\n", accountBalances.get(index));
                        double newWithdrawal;
                        do{
                            System.out.print("\tEnter the withdrawal Amount :");
                            newWithdrawal = scanner.nextDouble();
                            scanner.nextLine();

                            if (newWithdrawal>100 && accountBalances.get(index) - newWithdrawal>=500) break;
                            else if (newWithdrawal <100){
                                System.out.printf(ERROR_MSG, "Minimum Deposit should be Rs.100/=");                 
                            }else{
                                System.out.printf(ERROR_MSG, "Insufficient Account Balance. Account Balance should not be less than Rs.500.");                                 
                            }
                        }while (true);

                        accountBalance = accountBalances.get(index) - newWithdrawal;

                        accountBalances.set(index,accountBalance);

                        System.out.printf("\tNew Balance is Rs. %,.2f\n",accountBalances.get(index));

                        System.out.print("\tDo You Want to Withdrawal again: ");
                        if(scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                        else{
                            screen = DASHBOARD;
                            break;
                        }

                    } while (valid);
                    break;
// ==========================================================================================================================================
                case TRANSFER_MONEY:
// ==========================================================================================================================================
                    valid = false;
                    String accountNumberFrom;
                    String accountNumberTo;
                    int indexFrom;
                    int indexTo;
                    label2:
                    do{
                        System.out.print("\tEnter from Account Number: ");
                        accountNumberFrom = scanner.nextLine().strip();
                        flag = false;

                            label1:
                            do {
                                if(accountNumberFrom.isEmpty()){
                                System.out.printf(ERROR_MSG, "Account Number can't be empty");
                                break;
                                }

                                if(accountNumberFrom.length() !=9 || !accountNumberFrom.startsWith("SDB-")){
                                System.out.printf(ERROR_MSG, "Invalid Format-1");
                                break;
                                }
                                for (int i = 4; i <= 8; i++) {
                                    if(!Character.isDigit(accountNumberFrom.charAt(i))){
                                    System.out.printf(ERROR_MSG, "Invalid Format-2");
                                    break label1;
                                    }                               
                                }
                                if(!accountNumbers.contains(accountNumberFrom)){
                                System.out.printf(ERROR_MSG, "Not Found");
                                break;

                                }else {
                                    flag = true;
                                    break label2;
                                }

                            } while (true);
                            System.out.print("\tDo you want to try again..?");
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            else {
                                screen = DASHBOARD;
                                break label2;
                            }
                    }while(true);
                    label3:
                    do{
                        System.out.print("\tEnter To Account Number: ");
                        accountNumberTo = scanner.nextLine().strip();
                        flag = false;

                            label1:
                            do {
                                if(accountNumberTo.isEmpty()){
                                System.out.printf(ERROR_MSG, "Account Number can't be empty");
                                break;
                                }

                                if(accountNumberTo.length() !=9 || !accountNumberTo.startsWith("SDB-")){
                                System.out.printf(ERROR_MSG, "Invalid Format-1");
                                break;
                                }
                                for (int i = 4; i <= 8; i++) {
                                    if(!Character.isDigit(accountNumberTo.charAt(i))){
                                    System.out.printf(ERROR_MSG, "Invalid Format-2");
                                    break label1;
                                    }                               
                                }
                                if(!accountNumbers.contains(accountNumberTo)){
                                System.out.printf(ERROR_MSG, "Not Found");
                                break;

                                }else {
                                    flag = true;
                                    break label3;
                                }

                            } while (true);
                            System.out.print("\tDo you want to try again..?");
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            else {
                                screen = DASHBOARD;
                                break label3;
                            }

                    }while(true);

                    indexFrom = accountNumbers.indexOf(accountNumberFrom);
                    indexTo = accountNumbers.indexOf(accountNumberTo);
                    double accountBalanceFrom;
                    double accountBalanceTo;

                    System.out.printf("\tCurrent Balance of From Account is Rs. %,.2f\n", accountBalances.get(indexFrom));
                    System.out.printf("\tCurrent Balance of Two Account is Rs. %,.2f\n", accountBalances.get(indexTo));
                    double transferAmount;
 
                    do{
                        System.out.print("\tEnter the Transfer Amount :");
                        transferAmount = scanner.nextDouble();
                        scanner.nextLine();

                        if (transferAmount>100 && accountBalances.get(indexFrom) - transferAmount>=500) break;
                        else if (transferAmount <100){
                            System.out.printf(ERROR_MSG, "Minimum Transfer Amount should be Rs.100/=");                 
                        }else{
                            System.out.printf(ERROR_MSG, "Insufficient Account Balance. Account Balance should not be less than Rs.500."); 
                            System.out.print("\tDo you Want to transfer again :");  
                            if(scanner.nextLine().strip().toUpperCase().equals("Y")) continue;  
                            else{
                                screen = DASHBOARD;
                                break;
                            }                                                                   
                        }
                    }while (true);

                    accountBalanceFrom = accountBalances.get(indexFrom) -transferAmount;
                    accountBalanceTo = accountBalances.get(indexTo) + transferAmount;

                    accountBalances.set(indexTo,accountBalanceTo);
                    accountBalances.set(indexFrom,accountBalanceFrom);                       

                    System.out.printf("\tNew Balance of To Account is Rs. %,.2f\n",accountBalances.get(indexTo));
                    System.out.printf("\tNew Balance of From Account is Rs. %,.2f\n",accountBalances.get(indexFrom));

                    System.out.print("\tDo You Want to transfer again: ");
                    if(scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                    else{
                        screen = DASHBOARD;
                        break;
                    }
                    
// ==========================================================================================================================================
                case CHECK_ACCOUNT_BALANCE:
// ==========================================================================================================================================
     
                    do{
                        System.out.print("\tEnter Account Number: ");
                        accountNumber = scanner.nextLine().strip();

                            label4:
                            {
                                if(accountNumber.isEmpty()){
                                System.out.printf(ERROR_MSG, "Account Number can't be empty");
                                break label4;
                                }

                                if(accountNumber.length() !=9 || !accountNumber.startsWith("SDB-")){
                                System.out.printf(ERROR_MSG, "Invalid Format-1");
                                break label4;
                                }

                                for (int i = 4; i <= 8; i++) {
                                    if(!Character.isDigit(accountNumber.charAt(i))){
                                    System.out.printf(ERROR_MSG, "Invalid Format-2");
                                    break label4;
                                    }                               
                                }
                                if(!accountNumbers.contains(accountNumber)){
                                System.out.printf(ERROR_MSG, "Not Found");
                                break label4;

                                }else {
                                    index = accountNumbers.indexOf(accountNumber);
                                    System.out.printf("\tAccount Balance is Rs. %,.2f\n",accountBalances.get(index));
                                    break label4;
                                }

                            };
                            System.out.print("\tDo you want to check again or check another..? (Y/N) ");
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            else {
                                screen = DASHBOARD;
                                break;
                            }
                               
                    }while(true);
                    break;
//===========================================================================================================================================
                case DELETE_ACCOUNT:
//===========================================================================================================================================
                    count =0;
                    do{
                        System.out.print("\tEnter Account Number: ");
                        accountNumber = scanner.nextLine().strip();

                            label4:
                            {
                                if(accountNumber.isEmpty()){
                                System.out.printf(ERROR_MSG, "Account Number can't be empty");
                                break label4;
                                }

                                if(accountNumber.length() !=9 || !accountNumber.startsWith("SDB-")){
                                System.out.printf(ERROR_MSG, "Invalid Format-1");
                                break label4;
                                }

                                for (int i = 4; i <= 8; i++) {
                                    if(!Character.isDigit(accountNumber.charAt(i))){
                                    System.out.printf(ERROR_MSG, "Invalid Format-2");
                                    break label4;
                                    }                               
                                }
                                if(!accountNumbers.contains(accountNumber)){
                                System.out.printf(ERROR_MSG, "Not Found");
                                break label4;

                                }else {
                                    index = accountNumbers.indexOf(accountNumber);
                                    accountNumbers.remove(index);
                                    accountNames.remove(index);
                                    accountBalances.remove(index);
                                    System.out.printf("\tAccount %s is deleted\n",accountNumber);
                                    count++;
                                    break label4;
                                }

                            };
                            System.out.print("\tDo you want to delete again or delete another..? (Y/N) ");
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            else {
                                screen = DASHBOARD;
                                break;
                            }
                               
                    }while(true);
                    break;

                case EXIT:
                System.exit(0);
            }

        } while (true);

    }

    // Account Number Validation Method.
    public static void accountNumberValidationMethod(String accountNumber) {

    }

}