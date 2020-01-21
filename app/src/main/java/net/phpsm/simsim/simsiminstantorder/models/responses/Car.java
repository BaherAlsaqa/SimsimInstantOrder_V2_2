package net.phpsm.simsim.simsiminstantorder.models.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 09/01/2018.
 */

public class Car {
    @SerializedName("id")
    public int id;
    @SerializedName("model")
    public String model;
    @SerializedName("brand")
    private CarBrands brand;
    @SerializedName("car_type")
    public String car_type;
    @SerializedName("car_color")
    public CarColors carColors;

    public Car(){}

    public Car(int id, String model, CarBrands brand, String car_type, CarColors carColors) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.car_type = car_type;
        this.carColors = carColors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarBrands getBrand() {
        return brand;
    }

    public void setBrand(CarBrands brand) {
        this.brand = brand;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public CarColors getCarColors() {
        return carColors;
    }

    public void setCarColors(CarColors carColors) {
        this.carColors = carColors;
    }

    public class Brand {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;

        public Brand(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
