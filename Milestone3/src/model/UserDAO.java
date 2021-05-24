package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.ZoneId;

public class UserDAO {
    Connection connection=null;

    public  UserDAO() throws SQLException {
        String url = "jdbc:sqlite:C:\\Users\\Beata\\Desktop\\SVSU\\!classes\\CIS357\\Milestone3_all\\libdb.db";
        connection = DriverManager.getConnection(url);

    }

    public void insertUser(User u) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into users values (?,?,?,?,?,?,?)");
        preparedStatement.setInt(1, u.getID());
        preparedStatement.setString(2,u.getName());
        preparedStatement.setString(3,u.getAddress());
        preparedStatement.setString(4,u.getEmail());
        preparedStatement.setDate(5, Date.valueOf(u.getDateOfBirth()));
        preparedStatement.setBoolean(6, u.getStudent());
        preparedStatement.setDouble(7,u.getBalance());

        preparedStatement.executeUpdate();
        System.out.println("Success : "+preparedStatement);

    }

    public void deleteUser(User u) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from users where ID=?");
        preparedStatement.setInt(1, u.getID());
        preparedStatement.executeUpdate();
        System.out.println("Success : "+preparedStatement);

    }

    public void updateUser(User u) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update users set name=?, email=?, " +
                "dateOfBirth=?, isStudent=?, balance=? where ID=? ");
        preparedStatement.setInt(6, u.getID());
        preparedStatement.setString(1, u.getName());
        preparedStatement.setString(2,u.getEmail());
        preparedStatement.setDate(3, Date.valueOf(u.getDateOfBirth()));
        preparedStatement.setBoolean(4, u.getStudent());
        preparedStatement.setDouble(5,u.getBalance());
        preparedStatement.executeUpdate();
        System.out.println("Success : "+preparedStatement);

    }

    public ObservableList getUsers() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<User> temp = FXCollections.observableArrayList();
        while(rs.next()){
            temp.add(new User(rs.getString(2),rs.getString(4),rs.getString(3),
                    rs.getDate(5).toLocalDate(),rs.getBoolean(6)));
            temp.get(temp.size()-1).setBalance(rs.getDouble(7));
        }
        System.out.println("Success : "+preparedStatement);
        return temp;

    }
}
