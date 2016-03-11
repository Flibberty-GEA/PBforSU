package ua.skillsup.gea.phonebook;/**
 * Created by yevge_000 on 08.12.2015.
 */

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.skillsup.gea.phonebook.model.Person;
import ua.skillsup.gea.phonebook.model.PersonListWrapper;
import ua.skillsup.gea.phonebook.view.PersonEditDialogController;
import ua.skillsup.gea.phonebook.view.PersonOverviewController;

import ua.skillsup.gea.phonebook.view.RootLayoutController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MainApp extends Application {

    //Stage является основным контейнером, который,
    // как правило, представляет из себя окно с рамками
    // и стандартными кнопками закрыть, уменьшить и увеличить.
    private Stage primaryStage;
    private BorderPane rootLayout;

    // ... AFTER THE OTHER VARIABLES ...
    // ... Після інших змінних ...

    /**
     * The data as an observable list of Persons.
     * Видимий список осіб.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        // Додати деякі вибіркові дані
        personData.add(new Person("Hans", "Muster", "+380679311323"));
        personData.add(new Person("Ruth", "Mueller", "+380679311323"));
        personData.add(new Person("Heinz", "Kurz", "+380679311323"));
        personData.add(new Person("Cornelia", "Meier", "+380679311323"));
        personData.add(new Person("Werner", "Meyer", "+380679311323"));
        personData.add(new Person("Lydia", "Kunz", "+380679311323"));
        personData.add(new Person("Anna", "Best", "+380679311323"));
        personData.add(new Person("Stefan", "Meier", "+380679311323"));
        personData.add(new Person("Martin", "Mueller", "+380679311323"));
    }

    /**
     * Returns the data as an observable list of Persons.
     * Повертає дані у вигляді списку видимих осіб.
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    // ... THE REST OF THE CLASS ... РЕШТА КЛАССУ


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Телефонна книга");

        // Set the application icon.
        this.primaryStage.getIcons().add(new Image("file:resources/images/iconPhoneBook.png"));

        initRootLayout();

        showPersonOverview();
    }

    /**
     * Initializes the root layout.
     * Ініціалізує кореневий макет (RootLayout.fxml).
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            // Завантаження корневого макету з файлу FXML (RootLayout.fxml)
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            // Показати сцену, що містить корневий макет (RootLayout.fxml).
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     ЗАМЕНЯЕТСЯ КОДОМ СНИЗУ
     */
    /**
     * Initializes the root layout and tries to load the last opened
     * person file.
     * Ініціалізує кореневий макет (RootLayout.fxml) та завантажує останній відкритий файл з записами.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            // Завантаження корневого макету з файлу FXML (RootLayout.fxml)
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            // Показати сцену, що містить корневий макет (RootLayout.fxml).
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            //Дють доступ контроллеру к основному классу приложения
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        //Загрузка последнего открытого файла с записями.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }


    /**
     * Shows the person overview inside the root layout.
     * Показує огляд персони (PersonOverview.fxml) всередині кореневої макет.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            // Завантажити огляд людини.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            // Встановити огляд людини в центрі корневого макета.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            // Дає контролер доступу до основного додатку.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * Повернути основный контейнер.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Метод для загрузки и отображения диалога редактирования записей (МЗОДРЗ)
     *
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     *
     * МЗОДРЗ начало
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редагування контактних даних");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    //МЗОДРЗ конец

    //Следующие два метода обеспечивают сохранение и восстановление настроек нашего приложения.
    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("AddressApp");
        }
    }


    //Сделаем наш класс MainApp ответственным за чтение и запись данных нашего приложения.
    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     *
     * @param file
     */
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());

            // Save the file path to the registry.
            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
            /*
            Dialog.create()
                    .title("Error")
                    .masthead("Could not load data from file:\n" + file.getPath())
                    .showException(e);
                    */
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(getPrimaryStage());
            alert.setTitle("Помилка");
            alert.setHeaderText("Не вдалося завантажити дані з файлу:\n" + file.getPath());
            alert.setContentText(String.valueOf(e));

            alert.showAndWait();
        }
    }

    /**
     * Saves the current person data to the specified file.
     *
     * @param file
     */
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            /*
            Dialogs.create().title("Error")
                    .masthead("Could not save data to file:\n" + file.getPath())
                    .showException(e);
                    */
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(getPrimaryStage());
            alert.setTitle("Помилка");
            alert.setHeaderText("Не вдалося зберыгти дані в файл:\n" + file.getPath());
            alert.setContentText(String.valueOf(e));

            alert.showAndWait();
        }
    }
}
