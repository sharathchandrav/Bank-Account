package com.scv.bankaccount.backend;

public enum AccountType {
	SB("SavingsBank"), CB("CurrentBank"), UNKNOWN("Unknown account type");
	
	private String data;

	private AccountType(String description) {
		this.data = description;
	}

	public static AccountType from(String accountTypeInString) {
		for (AccountType typeOfAccount : values()) {
			if (typeOfAccount.toString().equals(accountTypeInString)) {
				return typeOfAccount;
			}
		}

		return AccountType.UNKNOWN;
	}
}
