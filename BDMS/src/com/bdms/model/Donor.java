package BDMS.src.com.bdms.model

import java.time.LocalDate;

public class Donor {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String bloodGroup;
    private String phone;
    private String city;
    private LocalDate lastDonationDate;

    public Donor() {
    }

    public Donor(String name, int age, String gender, String bloodGroup, String phone, String city,
            LocalDate lastDonationDate) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.city = city;
        this.lastDonationDate = lastDonationDate;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Donor{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", lastDonationDate=" + lastDonationDate +
                '}';
    }
}
