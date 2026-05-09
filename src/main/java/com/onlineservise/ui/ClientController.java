package com.onlineservise.ui;

import com.onlineservise.dto.ClientDTO;
import com.onlineservise.service.ClientService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientController {

    @FXML
    private TableView<ClientDTO> clientTable;
    @FXML
    private TableColumn<ClientDTO, String> nameColumn;
    @FXML
    private TableColumn<ClientDTO, String> phoneColumn;
    @FXML
    private Label statusLabel;

    private final ObservableList<ClientDTO> clientList = FXCollections.observableArrayList();
    private ClientService clientService;

    @FXML
    public void initialize() {
        clientService = SpringContext.getContext().getBean(ClientService.class);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        clientTable.setItems(clientList);
        loadClients();
    }

    @FXML
    public void loadClients() {
        Task<java.util.List<ClientDTO>> task = new Task<>() {
            @Override
            protected java.util.List<ClientDTO> call() {
                return clientService.getAllClients();
            }
        };
        task.setOnSucceeded(e -> {
            clientList.setAll(task.getValue());
            statusLabel.setText("Clients loaded.");
        });
        new Thread(task).start();
    }
}
