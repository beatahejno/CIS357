package model;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Library {
    private int MAX_BOOK_LIMIT=3;
    private int MAX_LOAN_DAYS=14;

    public TransactionDAO transactionDAO;
    public ArrayList<Transaction> transactions;
    public UserDAO userDAO;
    public ArrayList<User> users;
    public BookDAO bookDAO;
    public ArrayList<Book> books;
    public ArrayList<String> msgLog;

    public Library()
    {
        transactions = new ArrayList<Transaction>();
        users = new ArrayList<User>();
        books = new ArrayList<Book>();
        msgLog =new ArrayList<String>();
        try {
            transactionDAO = new TransactionDAO();
            bookDAO = new BookDAO();
            userDAO = new UserDAO();

            users.addAll(userDAO.getUsers());
            books.addAll(bookDAO.getBooks());
            transactions.addAll(transactionDAO.getTransactions());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String printMsgLog(){
        String result= "";
        for (String msg : msgLog){
            result += msg + " \n ";
        }
        msgLog.clear();
        return result;
    }

    public void addBook(Book b) {
        books.add(b);
        try {
            bookDAO.insertBook(b);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        msgLog.add("Book ID "+b.getID()+" added to Library");
    }
    public void addUser(User u)
    {
        users.add(u);
        try {
            userDAO.insertUser(u);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        msgLog.add("User ID "+u.getID()+" added to Library");
    }
    public void addTransaction(Transaction t)
    {
        transactions.add(t);
        try {
            transactionDAO.insertTransaction(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean isAvailable(int bookID){
        for(Transaction t:transactions){
            if (t.getBookID() == bookID && t.isStatus())
                return false;
        }
        return true;

    }
    public int getBorrowCount(int userID){
        int count=0;
        for(Transaction t: transactions){
            if (t.getUserID()== userID && t.isStatus())
                count++;
        }
        return count;
    }

    public Date getDueDate(Date issueDate) {
        Date dueDate;
        Calendar c = Calendar.getInstance();
        c.setTime(issueDate);
        c.add(Calendar.DATE,MAX_LOAN_DAYS);
        dueDate = c.getTime();
        return dueDate;
    }

    public boolean issueBook(int userID, int bookID)
    {
        User u = getUser(userID);
        Book b = getBook(bookID);

        //check if book is unavailable
        if (!isAvailable(bookID)){
            msgLog.add("This book is currently unavailable!");
            return false;
        }

        //check if max books limit has been reached or outstanding fines
        if (getBorrowCount(userID) >= MAX_BOOK_LIMIT) {
            msgLog.add("User has reached the maximum limit of borrowed books!");
            return false;
        }
        //check if user has outstanding balance
        if (u.getBalance() > 0){
            msgLog.add("User has an outstanding balance of $"+u.getBalance()+"!");
            return false;
        }

        // ready to issue book.
        Transaction trans = new Transaction(bookID,userID);
        transactions.add(trans);
        try {
            transactionDAO.insertTransaction(trans);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        msgLog.add(b.getName()+" has been issued to "+u.getName()+"." );
        msgLog.add("The due date is "+getDueDate(trans.getIssueDate()));
        return true;
    }

    public boolean returnBook(int bookID)
    {
        Transaction trans=null;
        for (Transaction t: transactions)
            if (t.getBookID()==bookID && t.isStatus()){
                trans = t;
                break;
            }
        if (trans==null){
            msgLog.add("Book currently not borrowed");
            return false;
        }

        //compute Fines
        int userID = trans.getUserID();
        User u = getUser(userID);
        Book b = getBook(bookID);
        double fine = computeFine(trans);
        //System.out.println(fi)
        u.setBalance(u.getBalance()+fine);
        trans.setStatus(false);
        try {
            transactionDAO.updateTransaction(trans);
            userDAO.updateUser(u);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(fine==0)
            msgLog.add("Thanks for returning "+b.getName()+"!");
        else {
                msgLog.add("You returned " + b.getName() + " " + fine + " days late!");
                msgLog.add("Your outstanding balance is $" + u.getBalance());
        }
        return true;

    }

    public void collectFine(User u){
        if (u.getBalance()<=0){
            msgLog.add("User has no outstanding balances..");
        }
        else{
            msgLog.add("User dues collected....");
            u.setBalance(0);
            try {
                userDAO.updateUser(u);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private double computeFine(Transaction t)
    {
        //long ms = new Date().getTime() - getDueDate(t.getIssueDate()).getTime();
        long ms = new Date().getTime() - t.getIssueDate().getTime();
        //System.out.println(ms);
        if (ms<=14) //change in final
            return 0;
        //return  TimeUnit.DAYS.convert(ms,TimeUnit.MILLISECONDS);
        return TimeUnit.SECONDS.convert(ms, TimeUnit.MILLISECONDS);

    }

    public ArrayList<Book> searchBook(String name)
    {
        ArrayList<Book> results= new ArrayList<Book>();
        for (Book b: books) {
            if (b.getName().toLowerCase().contains(name.toLowerCase()))
                results.add(b);
        }

        return results;
    }

    public ArrayList<User> searchUser(String name)
    {
        ArrayList<User> results= new ArrayList<User>();
        for (User b: users) {
            if (b.getName().toLowerCase().contains(name.toLowerCase()))
                results.add(b);
        }

        return results;
    }

    public void printResults(ArrayList<Book> results)
    {
        if (results.size()==0)
            msgLog.add("No books matched the search criteria");
        for (Book b: results) {
            msgLog.add("Book Id : "+b.getID()+" Book Name : "+b.getName());
        }
    }

    public void printResultsUser(ArrayList<User> results)
    {
        if (results.size()==0)
            msgLog.add("No users matched the search criteria");
        for (User u: results) {
            msgLog.add("User Id : "+u.getID()+" User Name : "+u.getName());
        }
    }

    public Book getBook(int bookID){
        for (Book temp : books){
            if (temp.getID()==bookID){
                return temp;
            }
        }
        return null;
    }

    public User getUser(int userID){
        for (User temp : users){
            if (temp.getID()==userID){
                return temp;
            }
        }
        return null;
    }



}
