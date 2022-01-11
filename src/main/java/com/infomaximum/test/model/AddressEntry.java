package com.infomaximum.test.model;

import java.util.Objects;

public class AddressEntry {

    private String city;
    private String street;
    private String house;
    private String floor;

    public AddressEntry(String city, String street, String house, String floor) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.floor = floor;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getFloor() {
        return floor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressEntry that = (AddressEntry) o;
        return city.equals(that.city) && street.equals(that.street) && house.equals(that.house) && floor.equals(that.floor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, house, floor);
    }

    @Override
    public String toString() {
        return "[" +
                "city = '" + city + '\'' +
                ", street = '" + street + '\'' +
                ", house = '" + house + '\'' +
                ", floor = '" + floor + '\'' +
                ']';
    }

}
