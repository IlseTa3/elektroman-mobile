package be.ucll.jmelektromanex.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public Long userId;

    public String firstName;
    public String lastName;
    public String username;
    public String password;

    public LocalDate birthdate;
    public String municipality;
    public String postalcode;
    public String street;
    public String houseNr;
    public String boxnr;

    public User() {
    }

    public User(String firstName, String lastName, LocalDate dateOfBirth
            , String street, String housNr, String boxNr, String postalcode,
                String municipality, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = dateOfBirth;
        this.street = street;
        this.houseNr = housNr;
        this.boxnr = boxNr;
        this.postalcode = postalcode;
        this.municipality = municipality;
        this.username = username;
        this.password = password;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public String getBoxnr() {
        return boxnr;
    }

    public void setBoxnr(String boxnr) {
        this.boxnr = boxnr;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthdate=" + birthdate +
                ", municipality='" + municipality + '\'' +
                ", postalcode='" + postalcode + '\'' +
                ", street='" + street + '\'' +
                ", houseNr='" + houseNr + '\'' +
                ", boxnr='" + boxnr + '\'' +
                '}';
    }
}

