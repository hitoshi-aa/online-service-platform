package com.onlineservise.ui;

import com.onlineservise.dto.ClientDTO;
import com.onlineservise.dto.ServiceOrderDTO;
import com.onlineservise.service.ClientService;
import com.onlineservise.service.ServiceOrderService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    private TableView<ServiceOrderDTO> ordersTable;

    @FXML
    private TableColumn<ServiceOrderDTO, Long> idColumn;

    @FXML
    private TableColumn<ServiceOrderDTO, String> clientColumn;

    @FXML
    private TableColumn<ServiceOrderDTO, String> phoneColumn;

    @FXML
    private TableColumn<ServiceOrderDTO, String> serviceColumn;

    @FXML
    private TableColumn<ServiceOrderDTO, String> masterColumn;

    @FXML
    private TableColumn<ServiceOrderDTO, String> statusColumn;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField searchField;

    private final ObservableList<ServiceOrderDTO> orderList = FXCollections.observableArrayList();

    private ServiceOrderService orderService;
    private ClientService clientService;

    @FXML
    public void initialize() {
        orderService = SpringContext.getContext().getBean(ServiceOrderService.class);
        clientService = SpringContext.getContext().getBean(ClientService.class);

        int count = clientService.getAllClients().size();
        System.out.println("[DEBUG] Client count in Java: " + count);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        clientColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getClient() != null ? cellData.getValue().getClient().getName() : ""
            )
        );

        phoneColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getClient() != null ? cellData.getValue().getClient().getPhone() : ""
            )
        );

        serviceColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getServices() != null ? 
                cellData.getValue().getServices().stream().map(s -> s.getName()).collect(Collectors.joining(", ")) : ""
            )
        );

        masterColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getMaster() != null ? cellData.getValue().getMaster().getName() : ""
            )
        );

        statusColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getStatus() != null ? cellData.getValue().getStatus() : "Waiting"
            )
        );

        ordersTable.setItems(orderList);
        statusLabel.setText("Clients found: " + count);
        
        loadOrders();
    }

    @FXML
    public void loadOrders() {
        statusLabel.setText("Loading...");
        Task<List<ServiceOrderDTO>> loadTask = new Task<>() {
            @Override
            protected List<ServiceOrderDTO> call() {
                return orderService.getAllOrders();
            }
        };

        loadTask.setOnSucceeded(event -> {
            List<ServiceOrderDTO> allOrders = loadTask.getValue();
            if (MainSession.role.equals("Master")) {
                allOrders = allOrders.stream()
                        .filter(o -> o.getMaster() != null && o.getMaster().getName().equals(MainSession.username))
                        .collect(Collectors.toList());
            }
            orderList.setAll(allOrders);
            statusLabel.setText(MainSession.role + " mode: orders loaded");
        });

        loadTask.setOnFailed(event -> {
            showError("Failed to load orders: " + loadTask.getException().getMessage());
            statusLabel.setText("Error loading orders");
        });

        new Thread(loadTask).start();
    }

    @FXML
    public void searchOrders() {
        String query = searchField.getText().toLowerCase().trim();
        if (query.isEmpty()) {
            loadOrders();
            return;
        }

        List<ServiceOrderDTO> filtered = orderList.stream()
                .filter(o -> (o.getClient() != null && o.getClient().getName().toLowerCase().contains(query)) ||
                             (o.getMaster() != null && o.getMaster().getName().toLowerCase().contains(query)))
                .collect(Collectors.toList());
        orderList.setAll(filtered);
    }

    @FXML
    public void addClient() {
        if (MainSession.role.equals("Master")) {
            showError("Masters cannot add clients");
            return;
        }

        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Add Client");
        nameDialog.setHeaderText("Enter client name");
        String name = nameDialog.showAndWait().orElse("");

        if (name.isBlank()) {
            showError("Client name cannot be empty");
            return;
        }

        TextInputDialog phoneDialog = new TextInputDialog();
        phoneDialog.setTitle("Phone");
        phoneDialog.setHeaderText("Enter phone number");
        String phone = phoneDialog.showAndWait().orElse("");

        if (!phone.startsWith("+380")) {
            showError("Phone must start with +380");
            return;
        }

        ClientDTO clientDTO = ClientDTO.builder().name(name).phone(phone).build();
        
        Task<Void> saveTask = new Task<>() {
            @Override
            protected Void call() {
                // 1. Save client
                ClientDTO savedClient = clientService.saveClient(clientDTO);
                
                // 2. Automatically create an order with a default master (id=1) and a default service (id=1)
                ServiceOrderDTO newOrder = new ServiceOrderDTO();
                newOrder.setClient(savedClient);
                com.onlineservise.dto.MasterDTO master = new com.onlineservise.dto.MasterDTO();
                master.setId(1L);
                newOrder.setMaster(master);
                newOrder.setOrderDate(java.time.LocalDateTime.now());
                newOrder.setStatus("Waiting");
                
                // Add default service to the order
                com.onlineservise.dto.ServiceDTO service = new com.onlineservise.dto.ServiceDTO();
                service.setId(1L);
                service.setName("Electrical Repair");
                newOrder.setServices(java.util.List.of(service));
                
                orderService.saveOrder(newOrder);
                return null;
            }
        };

        saveTask.setOnSucceeded(event -> {
            statusLabel.setText("Client saved to database");
            loadOrders();
        });

        new Thread(saveTask).start();
    }

    @FXML
    public void deleteOrder() {
        ServiceOrderDTO selected = ordersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Select an order to delete");
            return;
        }

        if (MainSession.role.equals("Master")) {
            showError("Masters cannot delete orders");
            return;
        }

        Task<Void> deleteTask = new Task<>() {
            @Override
            protected Void call() {
                orderService.deleteOrder(selected.getId());
                return null;
            }
        };

        deleteTask.setOnSucceeded(event -> {
            orderList.remove(selected);
            statusLabel.setText("Order deleted");
        });

        new Thread(deleteTask).start();
    }

    @FXML
    public void openClientsView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ClientView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Client List");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (Exception e) {
            showError("Could not open client list: " + e.getMessage());
        }
    }

    private void showError(String text) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(text);
            alert.showAndWait();
        });
    }
}
