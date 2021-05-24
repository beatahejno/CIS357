package model;

import java.util.*;

public class Transaction {

    private int bookID;
    private int userID;
    private Date issueDate;
    private boolean status; // true : active false : complete


    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Transaction(int bookID, int userID) {
        this.bookID = bookID;
        this.userID = userID;
        this.issueDate = new Date();
        this.status=true;
    }
}
