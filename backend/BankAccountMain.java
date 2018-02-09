package com.scv.bankaccount.backend;

import java.util.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BankAccountMain {
	static List<Account> accountList = new ArrayList<>();
	static int id = 1;

	public static void main(String[] args) throws ParseException {
		programInitiation();
	}

	private static void programInitiation() throws ParseException {
		Scanner s = new Scanner(System.in);

		while (true) {
			System.out.println(
					"---------Choices----------\n1.Add an Account\n2.Delete an Account\n3.Update an Account\n4.Summary\n5.Exit");
			System.out.println("Enter the Choice");
			int choice2 = s.nextInt();

			switch (choice2) {
			case 1:
				addAnAccount(s);
				break;
			case 2:
				deleteAnAccount(s);
				break;
			case 3:
				updateAnAccount(s);
				break;
			case 4:
				printYearlySummary();
				printMonthlySummary();
				printDailySummary();
				printCategorySummary();
				break;
			case 5:
				System.out.println("EXITING................");
				System.exit(0);
				s.close();
			default:
				System.out.println("Invalid Input");
			}
		}
	}

	private static void addAnAccount(Scanner s) throws ParseException {
		System.out.println("How many Entries do you want to enter?");
		int times = s.nextInt();
		s.nextLine();

		System.out.print("Enter your Account Details as Comma separated string (Eg: Eg:YYYY/MM/DD,100, SB/CB, Notes): \n");

		for (int i = 0; i < times; i++) {
			String userInput = s.nextLine();

			// String Processing
			String[] tokens = userInput.split(",");
			if (tokens.length != 4) {
				System.out.println("Invalid input entered. Requested operation cannot be performed!");
				return;
			}
			
			AccountType typeOfAccount = stringToAccountType(tokens[2]);
			if (typeOfAccount == AccountType.UNKNOWN) {
				System.out.println("Invalid category entered. Requested operation cannot be performed!");
				return;
			}
			
			Date finalDate = stringToDate(tokens[0].trim());
			int amount = Integer.parseInt(tokens[1].trim());
			String notes = tokens[3].trim();
			
			Account e1 = new Account(finalDate, amount, typeOfAccount, notes, id);
			accountList.add(e1);
			id++;
		}
	}

	private static void updateAnAccount(Scanner s) throws ParseException {
		printAllEntriesWithId();
	
		int particularExpenseId = 0;
		int index = 0;		
		
		System.out.println("Enter the S.No. of the Entry to be Updated :");
		int choice = s.nextInt();
		s.nextLine();
		
		Account expenseToBeUpdated = getAccountFromId(accountList, choice);
		index = accountList.indexOf(expenseToBeUpdated);
		particularExpenseId = expenseToBeUpdated.getId();
		
		if (expenseToBeUpdated == null) {
			System.out.println("Element with id '" + choice + "' is not found. Requested operation cannot be done.");
		} else {
			System.out
					.print("Enter your expense as Comma separated string (Eg: Eg:YYYY/MM/DD,100, C01, For Dinner): \n");
			String userInput = s.nextLine();
	
			// String Processing
			String[] tokens = userInput.split(",");
			if (tokens.length != 4) {
				System.out.println("Invalid input entered. Requested operation cannot be performed!");
				return;
			}
			
			AccountType category = stringToAccountType(tokens[2]);
			if (category == AccountType.UNKNOWN) {
				System.out.println("Invalid category entered. Requested operation cannot be performed!");
				return;
			}
			
			Date finalDate = stringToDate(tokens[0].trim());
			int amount = Integer.parseInt(tokens[1].trim());
			String notes = tokens[3].trim();
			
			Account e1 = new Account(finalDate, amount, category, notes, particularExpenseId);
			accountList.set(index, e1);
			System.out.println("Successfully Updated");
		}
	}
//-----------------------------------------------------------------------------------------------------------
	private static void deleteAnAccount(Scanner s) {
		// 8. Prepare map of Delete, Map<Expenses>
		printAllEntriesWithId();
		
		System.out.println("Enter the S.No. of the Entry to be Deleted :");
		int choice = s.nextInt();
		
		Expense expenseToBeDeleted = getExpenseFromId(expenseList, choice);
		if (expenseToBeDeleted == null) {
			System.out.println("Element with id '" + choice + "' is not found");
		} else {
			expenseList.remove(expenseToBeDeleted);
			System.out.println("Successfully Deleted");
		}
	}

	private static void printYearlySummary() {
		// 2. Prepare map of year, List<Expense>
		Map<Integer, List<Expense>> mapForYear = new HashMap<Integer, List<Expense>>();
		for (Expense thisExpense : expenseList) {
			int year = thisExpense.getOnlyYear();
			if (mapForYear.containsKey(year) == false) {
				List<Expense> list = new ArrayList<Expense>();
				mapForYear.put(year, list);
			}
			List<Expense> list = mapForYear.get(year);
			list.add(thisExpense);
		}

		// 3. Print the mapOfYear so that we know that we have written code
		// correctly
		System.out.println("--------The Summary in Years---------");
		for (Map.Entry<Integer, List<Expense>> entry : mapForYear.entrySet()) {
			System.out.println("");
			System.out.print("Key: " + entry.getKey());
			System.out.print(", Value: " + entry.getValue() + "\n");
			List<Expense> expensesInThisYear = entry.getValue();
			int totalYearCount = 0;
			for (Expense e : expensesInThisYear) {
				totalYearCount = totalYearCount + e.getAmount();
			}
			System.out.println("The Total amount in the year " + "is " + totalYearCount);
		}
	}

	private static void printMonthlySummary() {
		// 4. Prepare map of month, List<Expense>
		Map<String, List<Expense>> mapForMonth = new HashMap<String, List<Expense>>();
		for (Expense thisExpense : expenseList) {
			String month = thisExpense.getOnlyYear() + " " + thisExpense.getYearMonth();
			if (mapForMonth.containsKey(month) == false) {
				List<Expense> list = new ArrayList<Expense>();
				mapForMonth.put(month, list);
			}
			List<Expense> list = mapForMonth.get(month);
			list.add(thisExpense);
		}

		// 5. Print the mapOfMonth so that we know that we have written code
		// correctly
		System.out.println("--------The Summary in Months---------");
		for (Map.Entry<String, List<Expense>> entry : mapForMonth.entrySet()) {
			System.out.println("");
			System.out.print("Key: " + entry.getKey());
			System.out.print(", Value: " + entry.getValue() + "\n");
			List<Expense> expensesInThisMonth = entry.getValue();
			int totalMonthCount = 0;
			for (Expense e : expensesInThisMonth) {
				totalMonthCount = totalMonthCount + e.getAmount();
			}
			System.out.println("The Total amount in the month " + "is " + totalMonthCount);
		}
	}

	private static void printDailySummary() {
		// 6. Prepare map of Daily, List<Expense>
		Map<String, List<Expense>> mapForDaily = new HashMap<String, List<Expense>>();
		for (Expense thisExpense : expenseList) {
			String date = thisExpense.getOnlyYear() + " " + thisExpense.getYearMonth() + " " + thisExpense.getDate();
			if (mapForDaily.containsKey(date) == false) {
				List<Expense> list = new ArrayList<Expense>();
				mapForDaily.put(date, list);
			}
			List<Expense> list = mapForDaily.get(date);
			list.add(thisExpense);
		}
		// 7. Print the mapOfDate so that we know that we have written code
		// correctly
		System.out.println("--------The Summary in Dates---------");
		for (Map.Entry<String, List<Expense>> entry : mapForDaily.entrySet()) {
			System.out.println("");
			System.out.print("Key: " + entry.getKey());
			System.out.print(", Value: " + entry.getValue() + "\n");
			List<Expense> expensesInThisDate = entry.getValue();
			int totalDailyCount = 0;
			for (Expense e : expensesInThisDate) {
				totalDailyCount = totalDailyCount + e.getAmount();
			}
			System.out.println("The Total amount in the year " + "is " + totalDailyCount);
		}
	}
//----------------------------------------------------------------------------------------------------------------------------
	private static void printCategorySummary() {
		// 8. Prepare map of Category, List<Expense>
		Map<AccountType, List<Account>> mapFortypeOfAccount = new HashMap<AccountType, List<Account>>();
		for (Account thisExpense : accountList) {
			AccountType containCategory = thisExpense.getCategory();
			if (mapFortypeOfAccount.containsKey(containCategory) == false) {
				List<Account> list = new ArrayList<Account>();
				mapFortypeOfAccount.put(containCategory, list);
			}
			List<Account> list = mapFortypeOfAccount.get(containCategory);
			list.add(thisExpense);
		}
		// 7. Print the mapOfDate so that we know that we have written code
		// correctly
		System.out.println("--------The Summary by typeOfAccount---------");
		for (Map.Entry<AccountType, List<Account>> entry : mapFortypeOfAccount.entrySet()) {
			System.out.println("");
			System.out.print("Key: " + entry.getKey());
			System.out.print(", Value: " + entry.getValue() + "\n");
			List<Account> expensesInThisCategory = entry.getValue();
			int totalCategoryCount = 0;
			for (Account e : expensesInThisCategory) {
				totalCategoryCount = totalCategoryCount + e.getAmount();
			}
			System.out.println("The Total amount in the year " + "is " + totalCategoryCount);
		}
	}

	private static void printAllEntriesWithId() {
		System.out.println("-------- Displaying all the Accounts ---------");
		for (Account thisAccount : accountList) {
			int id = thisAccount.getId();
			System.out.print(id + "." + " " + thisAccount + "\n");
		}
	}

	private static Account getAccountFromId(List<Account> accountList, int choice) {
		Account accountToBeDeleted = null;
		
		for (Account thisAccount : accountList) {
			int id = thisAccount.getId();
			if (choice == id) {
				accountToBeDeleted = thisAccount;
			}
		}
		
		return accountToBeDeleted;
	}

	private static Date stringToDate(String dateAsString) throws ParseException {
		SimpleDateFormat readingFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date finalDate = readingFormat.parse(dateAsString);
		return finalDate;
	}

	private static AccountType stringToAccountType(String typeOfAccountAsString) throws ParseException {
		 return AccountType.from(typeOfAccountAsString.trim().toUpperCase());
	}
}

//	public static void main(String[] args) throws ParseException {
//		Scanner s = new Scanner(System.in);
//
//		System.out
//				.print("Enter your entry as Comma separated string (Eg:YYYY/MM/DD, Name, Type of account, Amount): \n");
//		List<Account> accountList = new ArrayList<>();
//
//		while (s.hasNextLine()) {
//			String userInput = s.nextLine();
//
//			// String Processing
//			String[] tokens = userInput.split(",");
//			if (tokens.length != 4) {
//				System.out.println("Invalid input entered. Exiting!");
//				System.exit(0);
//			}
//			String ymd = tokens[0].trim();
//			SimpleDateFormat readingFormat = new SimpleDateFormat("yy/MM/dd");
//			Date finalDate = readingFormat.parse(ymd);
//
//			String name = tokens[1].trim();
//			AccountType typeOfAccount = AccountType.from(tokens[2].trim().toUpperCase());
//			if (typeOfAccount == AccountType.UNKNOWN) {
//				System.out.println("Invalid Account type");
//			}
//
//			else {
//				int amount = Integer.parseInt(tokens[3].trim());
//
//				Account a1 = new Account(finalDate, name, typeOfAccount, amount, id);
//				accountList.add(a1);
//			}
//		}
//
//		int count = 0;
//		for (Account a : accountList) {
//			count = count + a.getAmount();
//		}
//
//		for (Account a : accountList) {
//			System.out.println(a.toString());
//		}
//		System.out.println("The Total amount in the Bank is " + count);
//
//		s.close();
//
//	}
//}
