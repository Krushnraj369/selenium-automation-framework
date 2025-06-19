package com.jk.data;

public class BeneficiaryData {
    public String nickname;
    public String accountNumber;
    public String accountName;
    public String mobileNumber;
    public String bank;
    public String region;
    public String branch;

    // Full constructor (Other Bank)
    public BeneficiaryData(String nickname, String accountNumber, String accountName,
                           String mobileNumber, String bank, String region, String branch) {
        this.nickname = nickname;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.mobileNumber = mobileNumber;
        this.bank = bank;
        this.region = region;
        this.branch = branch;
    }

    // Constructor for Other MCB Account
    public BeneficiaryData(String nickname, String accountNumber, String bank) {
        this.nickname = nickname;
        this.accountNumber = accountNumber;
        this.bank = bank;
    }
}
