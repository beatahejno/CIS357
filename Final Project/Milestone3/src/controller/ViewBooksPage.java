package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewBooksPage implements Initializable {
    Library library;

    @FXML private TableView<Transaction> tblTransactions;
    @FXML private TableColumn<Transaction, Integer> bookID;
    @FXML private TableColumn<Transaction, Integer> userID;
    @FXML private TableColumn<Transaction, Date> issueDate;


    public void loadData(){
        tblTransactions.setItems(FXCollections.observableArrayList(activeTransactions()));

        bookID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Transaction, Integer> s) {
                ObservableValue<Integer> val = new ReadOnlyObjectWrapper<>(s.getValue().getBookID());
                return val;
            }
        });

        userID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Transaction, Integer> s) {
                ObservableValue<Integer> val = new ReadOnlyObjectWrapper<>(s.getValue().getUserID());
                return val;
            }
        });

        issueDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction, Date>, ObservableValue<Date>>() {
            @Override
            public ObservableValue<Date> call(TableColumn.CellDataFeatures<Transaction, Date> s) {
                ObservableValue<Date> val= new ReadOnlyObjectWrapper<>(s.getValue().getIssueDate());
                return val;
            }
        });
    }

    public ArrayList<Transaction> activeTransactions(){
        ArrayList<Transaction> result = new ArrayList<>();

        for(Transaction t : library.transactions){
            if(t.isStatus())
                result.add(t);
        }
        return result;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
