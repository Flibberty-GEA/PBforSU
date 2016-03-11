package ua.skillsup.gea.phonebook.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ua.skillsup.gea.phonebook.util.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by Flibberty on 29.12.2015.
 */
public class Person {

    //обовязкові
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty phoneNumber;
    //за бажанням
    private final StringProperty phoneType;
    private final ObjectProperty<LocalDate> birthday;
    private final StringProperty eMail;
    private final StringProperty address;


    /**
     * Default constructor.
     * Конструктор за замовчуванням.
     */
    public Person() {
        this(null, null, null);
    }

    /**
     * Constructor with some initial data.
     * Конструктор з деякими початковими даними.
     *
     * @param firstName
     * @param lastName
     * @param phoneNumber
     */
    public Person(String firstName, String lastName, String phoneNumber) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);

        // Some initial dummy data, just for convenient testing.
        // Деякі початкові фіктивні дані, просто для зручного тестування.
        this.phoneType = new SimpleStringProperty("оберіть тип телефону");
        this.eMail = new SimpleStringProperty("зазначте ваш e-mail");
        this.address = new SimpleStringProperty("зазначте вашу адресу");
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1900, 12, 31));
    }

    public String getFirstName() { return firstName.get();}

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }


    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }


    public String getPhoneNumber() { return phoneNumber.get(); }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber.set(phoneNumber); }

    public StringProperty phoneNumberProperty() { return phoneNumber; }


    public String getPhoneType() { return phoneType.get(); }

    public StringProperty phoneTypeProperty() { return phoneType; }

    public void setPhoneType(String phoneType) { this.phoneType.set(phoneType); }


    public String getAddress() {return address.get();}

    public void setAddress(String address) { this.address.set(address);}

    public StringProperty addressProperty() {
        return address;
    }


    @XmlJavaTypeAdapter(LocalDateAdapter.class)//JAXB не знает как сконвертировать тип LocalDate в XML. Поэтому мы должны предоставить собственный класс LocalDateAdapter и определить процесс конвертации вручную.
    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }


    public String geteMail() {
        return eMail.get();
    }

    public void seteMail(String eMail) {
        this.eMail.set(eMail);
    }

    public StringProperty eMailProperty() {
        return eMail;
    }

}
