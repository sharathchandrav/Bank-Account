package com.scv.bankaccount.backend;

import java.time.Month;
import java.util.Date;

public class Account {

	private String notes;
	private AccountType typeOfAccount;
	private int amount;
	private Date accountFinalDate;
	private int id;


	public Account(Date accountFinalDate,
			int amount,
			AccountType typeOfAccount, 
			String notes, 
			int id) {
		this.notes = notes;
		this.amount = amount;
		this.typeOfAccount = typeOfAccount;
		this.accountFinalDate = accountFinalDate;
		this.id = id;

	}
	
	public String toString() {
		String show = "The name of the Account holder is '" + this.notes + "' with the Account type '"
				+ this.typeOfAccount + "'. " + "has the Balance Rs. '" + this.amount + "' on the date '"
				+ this.accountFinalDate + "' \n";
		return show;
	}

	public int getAmount() {
		return amount;
	}
	public int getOnlyYear() {
		return accountFinalDate.getYear() + 1900;
	}

	public String getYearMonth() {
		int monthNumber;
		monthNumber = accountFinalDate.getMonth() + 1;
		return Month.of(monthNumber).name();
	}

	public int getDate() {
		return accountFinalDate.getDate();
	}

	public AccountType getCategory() {
		return typeOfAccount;
	}

	public int getId() {
		return id;
	}
}
