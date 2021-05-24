package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.ZoneId;

public class TransactionDAO {

    Connection connection=null;

    public  TransactionDAO() throws SQLException {
        String url = "jdbc:sqlite:C:\\Users\\Beata\\Desktop\\SVSU\\!classes\\CIS357\\Milestone3_all\\libdb.db";
        connection = DriverManager.getConnection(url);

    }

    public void insertTransaction(Transaction t) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO libTransactions VALUES (?,?,?,?)");
        preparedStatement.setInt(1, t.getBookID());
        preparedStatement.setInt(2, t.getUserID());
        preparedStatement.setDate(3, new java.sql.Date(t.getIssueDate().getTime()));
        preparedStatement.setBoolean(4, t.isStatus());

        preparedStatement.executeUpdate();
        System.out.println("Success : "+preparedStatement);

    }

    public void deleteTransaction(Transaction t) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from libTransactions where bookID=?" +
                " AND userID=? AND issueDate=?");
        preparedStatement.setInt(1, t.getBookID());
        preparedStatement.setInt(2, t.getUserID());
        preparedStatement.setDate(3, (Date) t.getIssueDate());
        preparedStatement.executeUpdate();
        System.out.println("Success : "+preparedStatement);

    }

    public void updateTransaction(Transaction t) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update libTransactions set status=? where bookID=?" +
                " AND userID=? AND issueDate=?");
        preparedStatement.setBoolean(1, t.isStatus());
        preparedStatement.setInt(2, t.getBookID());
        preparedStatement.setInt(3, t.getUserID());
        preparedStatement.setDate(4, (Date) t.getIssueDate());

        preparedStatement.executeUpdate();
        System.out.println("Success : "+preparedStatement);

    }

    public ObservableList getTransactions() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from libTransactions");
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<Transaction> temp = FXCollections.observableArrayList();
        while(rs.next()){
            temp.add(new Transaction(rs.getInt(1),rs.getInt(2)));
            temp.get(temp.size()-1).setIssueDate(rs.getDate(3));
            temp.get(temp.size()-1).setStatus(rs.getBoolean(4));
        }
        System.out.println("Success : "+preparedStatement);
        return temp;

    }
}
