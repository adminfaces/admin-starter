package com.github.adminfaces.starter.util;

import com.github.adminfaces.starter.model.Car;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by rmpestano on 07/02/17.
 */
@ApplicationScoped
public class Utils implements Serializable {

    private List<Car> cars;


    @PostConstruct
    public void init() {
        cars = new ArrayList<>();
        IntStream.rangeClosed(1, 50)
                .forEach(i -> cars.add(create(i)));
    }

    private static Car create(int i) {
        return new Car(i).model("model " + i).name("name" + i).price(Double.valueOf(i));
    }

    public static void addDetailMessage(String message) {
        addDetailMessage(message, null);
    }

    public static void addDetailMessage(String message, FacesMessage.Severity severity) {

        FacesMessage facesMessage = Messages.create("").detail(message).get();
        if (severity != null && severity != FacesMessage.SEVERITY_INFO) {
            facesMessage.setSeverity(severity);
        }
        Messages.add(null, facesMessage);
    }

    @Produces
    public List<Car> getCars() {
        return cars;
    }

}
