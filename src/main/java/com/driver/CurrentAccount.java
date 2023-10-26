package com.driver;

import com.driver.Exceptions.InsufficientBalanceException;
import com.driver.Exceptions.ValidLicenseException;

import java.util.*;

public class CurrentAccount extends BankAccount{
    String tradeLicenseId; //consists of Uppercase English characters only

    public CurrentAccount(String name, double balance, String tradeLicenseId) throws Exception {
        // minimum balance is 5000 by default. If balance is less than 5000, throw "Insufficient Balance" exception
        super(name, balance, 5000);

        if(balance < 5000){
            throw new InsufficientBalanceException("Insufficient Balance");
        }

        this.tradeLicenseId = tradeLicenseId;
    }

    public String getTradeLicenseId() {
        return tradeLicenseId;
    }

    public void setTradeLicenseId(String tradeLicenseId) {
        this.tradeLicenseId = tradeLicenseId;
    }

    public boolean isValid(String s){
        for(int i=1; i<s.length(); i++){
            if(s.charAt(i) == s.charAt(i-1)){
                return false;
            }
        }
        return true;
    }

    static class Pair{
        char ch;
        int fr;
        Pair(char ch, int fr){
            this.ch = ch;
            this.fr = fr;
        }
    }
    public String reorganizeString(String s) {
        int n = s.length();
        // create freq map
        HashMap<Character, Integer> map = new HashMap();
        // now fill the map by traversing on the given string
        for(char ch: s.toCharArray()){
            map.put(ch, map.getOrDefault(ch, 0)+1);
        }

        // now make a max heap and store all these chars with freq to get sorted order
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>((a, b)->{
            return a.fr > b.fr ? -1 : 1;
        });

        // add all char with freq to pq
        for(char c: map.keySet()){
            pq.add(new Pair(c, map.get(c)));
        }

        // now make output string by adding max freq charcters first then next freq and so on
        StringBuilder sb = new StringBuilder();
        Pair block = pq.remove(); // removed initial max freq chatracter
        sb.append(block.ch);
        block.fr--;

        while(pq.size() > 0){
            // remove from pq
            Pair curr = pq.remove();
            char ch = curr.ch;

            sb.append(ch);
            curr.fr--;

            if(block.fr > 0){
                pq.add(block);
            }

            block = curr;
        }

        if(sb.length() != n){
            return "";
        }
        return sb.toString();
    }

    public void validateLicenseId() throws Exception {
        // A trade license Id is said to be valid if no two consecutive characters are same
        // If the license Id is valid, do nothing
        // If the characters of the license Id can be rearranged to create any valid license Id
        // If it is not possible, throw "Valid License can not be generated" Exception

        if(!isValid(this.tradeLicenseId)){
            String validLicense = reorganizeString(this.tradeLicenseId);

            if(validLicense.length() == 0){
                throw new ValidLicenseException("Valid License can not be generated");
            }
            else{
                this.tradeLicenseId = validLicense;
            }
        }
    }

}
