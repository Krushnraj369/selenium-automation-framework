package com.jk.data;

public class BeneficiaryData {
    public String nickname, accountNumber, accountName, mobileNumber, bank, region, branch;

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
}
