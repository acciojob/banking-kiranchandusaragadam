package com.driver;

import com.driver.Exceptions.AccountNumberException;
import com.driver.Exceptions.InsufficientBalanceException;

public class BankAccount {

    private String name;
    private double balance;
    private double minBalance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(double minBalance) {
        this.minBalance = minBalance;
    }

    public BankAccount(String name, double balance, double minBalance) {
        this.name = name;
        this.balance = balance;
        this.minBalance = minBalance;
    }

    public String generateAccountNumber(int digits, int sum) throws Exception{
        //Each digit of an account number can lie between 0 and 9 (both inclusive)
        //Generate account number having given number of 'digits' such that the sum of digits is equal to 'sum'
        //If it is not possible, throw "Account Number can not be generated" exception

        if(9*digits < sum){
            throw new AccountNumberException("Account Number can not be generated");
        }

        StringBuilder sb = new StringBuilder();
        int cnt = 0;

        for(int i=9; i>=0; i--){
            while(i <= sum){
                cnt++;
                sb.append(i);
                sum -= i;
            }
            if(sum == 0) break;
        }

        while(cnt < digits){
            sb.append(0);
            cnt++;
        }

        return sb.toString();
    }

    public void deposit(double amount) {
        //add amount to balance
        this.balance += amount;
    }

    public void withdraw(double amount) throws Exception {
        // Remember to throw "Insufficient Balance" exception, if the remaining amount would be less than minimum balance
        if(this.balance - amount < minBalance){
            throw new InsufficientBalanceException("Insufficient Balance");
        }
        else{
            this.balance -= amount;
        }
    }

}