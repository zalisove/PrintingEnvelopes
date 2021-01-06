package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class User {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty country;
    private SimpleStringProperty street;
    private SimpleStringProperty phone;
    private SimpleStringProperty city;
    private SimpleStringProperty region;
    private SimpleStringProperty district;
    private SimpleStringProperty ZIPCode;
    private SimpleStringProperty printFormat;
    private SimpleIntegerProperty leafCount;
    private SimpleStringProperty payer;
    private SimpleObjectProperty<LocalDate> subscriptionStartDate;
    private SimpleObjectProperty<LocalDate> subscriptionFinishDate;


    public User(int id, String name, String country, String street, String phone,
                String city, String region, String district, String ZIPCode,
                String printFormat, int leafCount, String payer,
                LocalDate subscriptionStartDate, LocalDate subscriptionFinishDate) {

        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.country = new SimpleStringProperty(country);
        this.street = new SimpleStringProperty(street);
        this.phone = new SimpleStringProperty(phone);
        this.city = new SimpleStringProperty(city);
        this.region = new SimpleStringProperty(region);
        this.district = new SimpleStringProperty(district);
        this.ZIPCode = new SimpleStringProperty(ZIPCode);
        this.printFormat = new SimpleStringProperty(printFormat);
        this.leafCount = new SimpleIntegerProperty(leafCount);
        this.payer = new SimpleStringProperty(payer);
        this.subscriptionStartDate = new SimpleObjectProperty<>(subscriptionStartDate);
        this.subscriptionFinishDate = new SimpleObjectProperty<>(subscriptionFinishDate);
    }

    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public String getCountry() {
        return country.get();
    }
    public void setCountry(String country) {
        this.country.set(country);
    }
    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getRegion() {
        return region.get();
    }

    public void setRegion(String region) {
        this.region.set(region);
    }

    public String getDistrict() {
        return district.get();
    }


    public void setDistrict(String district) {
        this.district.set(district);
    }

    public String getZIPCode() {
        return ZIPCode.get();
    }


    public void setZIPCode(String ZIPCode) {
        this.ZIPCode.set(ZIPCode);
    }

    public String getPrintFormat() {
        return printFormat.get();
    }


    public void setPrintFormat(String printFormat) {
        this.printFormat.set(printFormat);
    }

    public int getLeafCount() {
        return leafCount.get();
    }


    public void setLeafCount(int leafCount) {
        this.leafCount.set(leafCount);
    }

    public String getPayer() {
        return payer.get();
    }
    public void setPayer(String payer) {
        this.payer.set(payer);
    }
    public LocalDate getSubscriptionStartDate() {
        return subscriptionStartDate.get();
    }
    public void setSubscriptionStartDate(LocalDate subscriptionStartDate) {
        this.subscriptionStartDate.set(subscriptionStartDate);
    }
    public LocalDate getSubscriptionFinishDate() {
        return subscriptionFinishDate.get();
    }

    public void setSubscriptionFinishDate(LocalDate subscriptionFinishDate) {
        this.subscriptionFinishDate.set(subscriptionFinishDate);
    }

    @Override
    public String toString() {
        return name.get();
    }
}
