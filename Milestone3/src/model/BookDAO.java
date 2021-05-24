package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.ZoneId;

public class BookDAO {
    Connection connection=null;

    public  BookDAO() throws SQLException {
        String url = "jdbc:sqlite:C:\\Users\\Beata\\Desktop\\SVSU\\!classes\\CIS357\\Milestone3_all\\libdb.db";
        connection = DriverManager.getConnection(url);
    }

    public void insertBook(Book b) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into books values (?,?,?,?,?,?,?)");
        preparedStatement.setInt(1, b.getID());
        preparedStatement.setString(2,b.getName());
        preparedStatement.setString(3,b.getAuthor());
        preparedStatement.setString(4,b.getPublisher());
        preparedStatement.setString(5, b.getGenre());
        preparedStatement.setString(6, b.getISBN());
        preparedStatement.setLong(7,b.getYear());

        preparedStatement.executeUpdate();
        System.out.println("Success : "+preparedStatement);

    }

    public void deleteBook(Book b) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from books where ID=?");
        preparedStatement.setInt(1, b.getID());
        preparedStatement.executeUpdate();
        System.out.println("Success : "+preparedStatement);

    }

    public void updateBook(Book b) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update books set name=?, author=?, " +
                "publisher=?, genre=?, ISBN=?, year=? where ID=? ");
        preparedStatement.setString(1, b.getName());
        preparedStatement.setString(2,b.getAuthor());
        preparedStatement.setString(3,b.getPublisher());
        preparedStatement.setString(4, b.getGenre());
        preparedStatement.setString(5, b.getISBN());
        preparedStatement.setLong(6,b.getYear());
        preparedStatement.setInt(7, b.getID());
        preparedStatement.executeUpdate();
        System.out.println("Success : "+preparedStatement);

    }

    public ObservableList getBooks() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from books");
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<Book> temp = FXCollections.observableArrayList();
        while(rs.next()){
            temp.add(new Book(rs.getString(2),rs.getString(3),rs.getString(4),
                    rs.getString(5),rs.getString(6),rs.getLong(7)));
        }
        System.out.println("Success : "+preparedStatement);
        return temp;

    }
}
