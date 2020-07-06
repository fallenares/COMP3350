package comp3350.pbbs.persistence;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import comp3350.pbbs.application.*;
import comp3350.pbbs.objects.*;

/**
 * StubDatabase
 * Azizul Hakim
 * PBBS
 *
 * This class defines the persistence layer (stub database).
 */
public class StubDatabase {
	private String databaseName;                    //name of the database
	private ArrayList<BudgetCategory> budgets;      //ArrayList for budgets
	private ArrayList<CreditCard> creditCards;      //ArrayList for credit cards
	private ArrayList<Transaction> transactions;    //ArrayList for transactions
	private ArrayList<User> user;                   //ArrayList for user
	private Date date;                         		//local date variable

	/**
	 * @param name name of the database
	 * This method is the constructor of the database stub
	 */
	public StubDatabase(String name){
		this.databaseName = name;
		budgets = new ArrayList<BudgetCategory>();
		creditCards = new ArrayList<CreditCard>();
		transactions = new ArrayList<Transaction>();
		user = new ArrayList<User>();
	}

	/**
	 * This method performs the date calculation
	 * @return a Date object
	 */
	public static Date calcDate(Date d, int n) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(calendar.DATE, n);
		d = calendar.getTime();
		return d;
	}

	/**
	 * This method is used for populating fake data into the stub database
	 */
	public void populateData(){
		BudgetCategory rent, groceries, utilities, phoneBill;   //various types of BudgetCategories
		CreditCard card1, card2;                                //multiple cards
		Transaction t1, t2, t3, t4;                             //multiple transactions
		User user1;                                             //user variable

		budgets = new ArrayList<BudgetCategory>();
		rent = new BudgetCategory("Rent/Mortgage", 500);
		budgets.add(rent);
		groceries = new BudgetCategory("Groceries", 100);
		budgets.add(groceries);
		utilities = new BudgetCategory("Utilities", 80);
		budgets.add(utilities);
		phoneBill = new BudgetCategory("Phone Bill", 75);
		budgets.add(phoneBill);

		creditCards = new ArrayList<CreditCard>();
		card1 = new CreditCard("1000100010001000", "Jimmy", 12, 2021, 18);
		creditCards.add(card1);
		card2 = new CreditCard("1002100310041005", "Jimmy", 11, 2021, 15);
		creditCards.add(card2);

		date = new Date();
		transactions = new ArrayList<Transaction>();
		t1 = new Transaction(calcDate(date, -5), 50, "Bought Chickens", card1, groceries);
		transactions.add(t1);
		t2 = new Transaction(calcDate(date, -8), 450, "Rent Paid", card2, rent);
		transactions.add(t2);
		t3 = new Transaction(calcDate(date, 2), 40, "Hydro bill paid", card2, utilities);
		transactions.add(t3);
		t4 = new Transaction(calcDate(date, 3), 75, "Phone Bill paid", card2, phoneBill);
		transactions.add(t4);

		user = new ArrayList<User>();
		user1 = new User("Jimmy","Kimel");
		user.add(user1);
	}

	/**
	 * This method will add all the budgets to a budget list
	 * @return true if added successfully.
	 */
	public boolean addBudgetCategories(List<BudgetCategory> budgetList){
		return budgets.addAll(budgetList);
	}

	/**
	 * This method will find if a budget exist or not
	 * @return the BudgetCategory object
	 */
	public BudgetCategory findBudgetCategory(BudgetCategory currentBudget){
		BudgetCategory budgetCategory = null;
		int index = budgets.indexOf(currentBudget);
		if(index >= 0){
			budgetCategory = budgets.get(index);
		}
		return budgetCategory;
	}

	/**
	 * This method will insert a new budget category with the budgets ArrayList.
	 */
	public boolean insertBudgetCategory(BudgetCategory newBudget){
		return budgets.add(newBudget);
	}

	/**
	 * Getter method to get the budgets.
	 * @return budgets ArrayList.
	 */
	public ArrayList<BudgetCategory> getBudgets() {
		return budgets;
	}

	/**
	 * This method will be used to update a Budget.
	 * @return updated budgetCategory
	 */
	public BudgetCategory updateBudgetCategory(BudgetCategory currentBudget, BudgetCategory newBudget){
		int index = budgets.indexOf(currentBudget);
		BudgetCategory result = null;
		if (index >= 0){
			result = budgets.set(index,newBudget);
		}
		return result;
	}

	/**
	 * This method will remove a budget category.
	 * @return newly deleted budgetCategory
	 */
	public BudgetCategory deleteBudgetCategory(BudgetCategory currentBudget){
		int index = budgets.indexOf(currentBudget);
		BudgetCategory result = null;
		if (index >= 0){
			result = budgets.remove(index);
		}
		return result;
	}

	/**
	 * This method will add all the cards to a card list.
	 * @return true if added successfully.
	 */
	public boolean addAllCreditCards(List<CreditCard> cardList){
		return cardList.addAll(creditCards);
	}

	/**
	 * This method will find if a card exist or not.
	 * @return the card object.
	 */
	public boolean findCreditCard(CreditCard currCard){
		return creditCards.indexOf(currCard) >= 0;
	}

	/**
	 * This method will insert a new card with the ArrayList.
	 */
	public void insertCreditCard(CreditCard newCard){
		creditCards.add(newCard);
	}

	/**
	 * Getter method to get the credit cards.
	 * @return creditCards ArrayList.
	 */
	public ArrayList<CreditCard> getCreditCards(){
		return creditCards;
	}

	/**
	 * This method will be used to update a credit card.
	 * @return true if updated correctly.
	 */
	public boolean updateCreditCard(CreditCard currCard, CreditCard newCard){
		int index = creditCards.indexOf(currCard);
		if (index >= 0) {
			creditCards.set(index, newCard);
			return true;
		}
		return false;
	}

	/**
	 * This method will remove a credit card.
	 */
	public void deleteCreditCard(CreditCard currCard){
		creditCards.remove(currCard);
	}

	/**
	 * This method will add all the transactions to a transaction list.
	 * @return true if added successfully.
	 */
	public boolean addTransactions(List<Transaction> transactionsList){
		return transactions.addAll(transactionsList);
	}

	/**
	 * This method will find if a transaction exist or not.
	 * @return the transaction object.
	 */
	public Transaction findTransaction(Transaction currentTransaction){
		Transaction transaction = null;
		int index = transactions.indexOf(currentTransaction);
		if(index >= 0){
			transaction = transactions.get(index);
		}
		return transaction;
	}

	/**
	 * This method will insert a new transaction with the ArrayList.
	 */
	public boolean insertTransaction(Transaction newTransaction){
		return transactions.add(newTransaction);
	}

	/**
	 * Getter method to get the transactions.
	 * @return transactions ArrayList.
	 */
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * This method will be used to update a transaction.
	 */
	public boolean updateTransaction(Transaction currentTransaction, Transaction newTransaction){
		boolean toReturn = false;
		int index = transactions.indexOf(currentTransaction);
		if (index >= 0){
			toReturn = transactions.set(index, newTransaction) != null;
		}
		return toReturn;
	}

	/**
	 * This method will be used to remove a transaction.
	 */
	public void deleteTransaction(Transaction currentTransaction){
		int index = transactions.indexOf(currentTransaction);
		if (index >= 0){
			transactions.remove(index);
		}
	}

	/**
	 * Getter method to get the user.
	 * @return user ArrayList.
	 */
	public ArrayList<User> getUser(){
		return user;
	}

	/**
	 * This method will be used to update the user.
	 * @return updated user
	 */
	public User updateUser(User currentUser, User newUser){
		int index = user.indexOf(currentUser);
		User result = null;
		if (index >= 0){
			result = user.set(index,newUser);
		}
		return result;
	}

}