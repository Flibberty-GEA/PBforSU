package ua.skillsup.gea.phonebook.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ua.skillsup.gea.phonebook.MainApp;
import ua.skillsup.gea.phonebook.model.Person;
import ua.skillsup.gea.phonebook.util.DateUtil;

import java.io.IOException;

/**
 * Created by Flibberty on 29.12.2015.
 */
public class PersonOverviewController {

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private TableColumn<Person, String> phoneNumberColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label phoneTypeLabel;
    @FXML
    private Label eMailLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label birthdayLabel;

    // Reference to the main application.
    // Посилання до основного додатка.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     * Конструктор викликається перед методом ініціалізації (initialize()).
     */
    public PersonOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * Ініціалізує клас контролера. Цей метод викликається автоматично
     * Після того як файл FXML був завантажений.
     */

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        // Ініціалізація таблиці людей з двома колонами.
        firstNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().lastNameProperty());
        phoneNumberColumn.setCellValueFactory(
                cellData -> cellData.getValue().phoneNumberProperty());

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * Викликається основним додатком, щоб дати зворотне посилання до себе.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        // Додати видимий список даних в таблицю
        personTable.setItems(mainApp.getPersonData());
    }



    /**
     * в БРАТСКОМ
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            phoneNumberLabel.setText(person.getPhoneNumber());
            //streetLabel.setText(person.getStreet());
            //postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            phoneTypeLabel.setText(person.getPhoneType());
            addressLabel.setText(person.getAddress());
            eMailLabel.setText(person.geteMail());

            // TODO: We need a way to convert the birthday into a String!
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {

            // Person is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            phoneNumberLabel.setText("");
            //streetLabel.setText("");
            //postalCodeLabel.setText("");
            phoneTypeLabel.setText("");
            addressLabel.setText("");
            eMailLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    /**
     * Called when the user clicks on the delete button.
     * Викликати, коли користувач натисне "Видалити".
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            //Нічого не обрано.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Помилка");
            alert.setHeaderText("Не обрано контакт.");
            alert.setContentText("Оберіть контакт зі списку.");

            alert.showAndWait();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     * Викликати, коли користувач натисне "Додати". Віткрити вікно для редагування...
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     * Викликати, коли користувач натисне "Змінити". Віткрити вікно для редагування...
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Помилка");
            alert.setHeaderText("Не обрано контакт.");
            alert.setContentText("Оберіть контакт зі списку.");

            alert.showAndWait();
        }
    }
}