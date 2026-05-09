package com.onlineservise.ui;

import com.onlineservise.entity.Client;
import com.onlineservise.repository.ClientRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class MainController {

    @FXML
    private TableView<OrderData> ordersTable;

    @FXML
    private TableColumn<OrderData, Integer> idColumn;

    @FXML
    private TableColumn<OrderData, String> clientColumn;

    @FXML
    private TableColumn<OrderData, String> phoneColumn;

    @FXML
    private TableColumn<OrderData, String> serviceColumn;

    @FXML
    private TableColumn<OrderData, String> masterColumn;

    @FXML
    private TableColumn<OrderData, String> statusColumn;

    @FXML
    private Label statusLabel;

    private final ObservableList<OrderData> clients =
        FXCollections.observableArrayList();

    private final ClientRepository clientRepository =
        SpringContext
            .getContext()
            .getBean(ClientRepository.class);

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(
            new PropertyValueFactory<>("id")
        );

        clientColumn.setCellValueFactory(
            new PropertyValueFactory<>("client")
        );

        phoneColumn.setCellValueFactory(
            new PropertyValueFactory<>("phone")
        );

        serviceColumn.setCellValueFactory(
            new PropertyValueFactory<>("service")
        );

        masterColumn.setCellValueFactory(
            new PropertyValueFactory<>("master")
        );

        statusColumn.setCellValueFactory(
            new PropertyValueFactory<>("status")
        );

        ordersTable.setItems(clients);

        statusLabel.setText("System ready");
    }

    @FXML
    public void loadClients() {

        clients.clear();

        List<Client> dbClients =
            clientRepository.findAll();

        String[] services = {

            "Windows Installation",
            "Laptop Diagnostics",
            "PC Cleaning",
            "SSD Upgrade",
            "Data Recovery",
            "Router Configuration"
        };

        String[] masters = {

            "Andrii Kovalenko",
            "Dmytro Hrytsenko",
            "Ivan Melnyk",
            "Olena Bondar"
        };

        String[] statuses = {

            "Completed",
            "In Progress",
            "Waiting"
        };

        for (Client client : dbClients) {

            String randomService =
                services[
                    (int)(Math.random() * services.length)
                    ];

            String master =
                masters[
                    (int)(Math.random() * masters.length)
                    ];

            String randomStatus =
                statuses[
                    (int)(Math.random() * statuses.length)
                    ];

            if (
                MainSession.role.equals("Master")
                    &&
                    !master.equals(MainSession.username)
            ) {

                continue;
            }

            clients.add(

                new OrderData(
                    client.getId().intValue(),
                    client.getName(),
                    client.getPhone(),
                    randomService,
                    master,
                    randomStatus
                )
            );
        }

        ordersTable.refresh();

        if (MainSession.role.equals("Manager")) {

            statusLabel.setText(
                "Manager mode: all clients loaded"
            );
        }

        else {

            statusLabel.setText(
                "Master mode: only your orders loaded"
            );
        }
    }

    @FXML
    public void addClient() {

        if (MainSession.role.equals("Master")) {

            showError(
                "Masters cannot add clients"
            );

            return;
        }

        TextInputDialog nameDialog =
            new TextInputDialog();

        nameDialog.setTitle("Add Client");

        nameDialog.setHeaderText(
            "Enter client name"
        );

        String name =
            nameDialog.showAndWait().orElse("");

        if (name.isBlank()) {

            showError(
                "Client name cannot be empty"
            );

            return;
        }

        TextInputDialog phoneDialog =
            new TextInputDialog();

        phoneDialog.setTitle("Phone");

        phoneDialog.setHeaderText(
            "Enter phone number"
        );

        String phone =
            phoneDialog.showAndWait().orElse("");

        if (!phone.startsWith("+380")) {

            showError(
                "Phone must start with +380"
            );

            return;
        }

        Client client = new Client();

        client.setName(name);

        client.setPhone(phone);

        clientRepository.save(client);

        statusLabel.setText(
            "Client saved to database"
        );

        loadClients();
    }

    private void showError(String text) {

        Alert alert =
            new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Access Error");

        alert.setHeaderText(null);

        alert.setContentText(text);

        alert.showAndWait();
    }
}