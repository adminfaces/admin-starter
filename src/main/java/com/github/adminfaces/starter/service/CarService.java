/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adminfaces.starter.service;

import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.infra.model.SortOrder;
import com.github.adminfaces.starter.model.Car;
import com.github.adminfaces.template.exception.BusinessException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.github.adminfaces.template.util.Assert.has;

/**
 * @author rmpestano
 *         Car Business logic
 */
@Stateless
public class CarService implements Serializable {

    @Inject
    List<Car> allCars;

    public List<Car> listByModel(String model) {
        return allCars.stream()
                .filter(c -> c.getModel().equalsIgnoreCase(model))
                .collect(Collectors.toList());

    }

    public List<Car> paginate(Filter<Car> filter) {
        List<Car> pagedCars = new ArrayList<>();
        if(has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
                pagedCars = allCars.stream().
                    sorted((c1, c2) -> {
                        if (filter.getSortOrder().isAscending()) {
                            return c1.getId().compareTo(c2.getId());
                        } else {
                            return c2.getId().compareTo(c1.getId());
                        }
                    })
                    .collect(Collectors.toList());
            }

        int page = filter.getFirst() + filter.getPageSize();
        if (filter.getParams().isEmpty()) {
            pagedCars = pagedCars.subList(filter.getFirst(), page > allCars.size() ? allCars.size() : page);
            return pagedCars;
        }

        List<Predicate<Car>> predicates = configFilter(filter);

        List<Car> pagedList = allCars.stream().filter(predicates
                .stream().reduce(Predicate::or).orElse(t -> true))
                .collect(Collectors.toList());

        if (page < pagedList.size()) {
            pagedList = pagedList.subList(filter.getFirst(), page);
        }

        if (has(filter.getSortField())) {
            pagedList = pagedList.stream().
                    sorted((c1, c2) -> {
                        boolean asc = SortOrder.ASCENDING.equals(filter.getSortOrder());
                        if (asc) {
                            return c1.getId().compareTo(c2.getId());
                        } else {
                            return c2.getId().compareTo(c1.getId());
                        }
                    })
                    .collect(Collectors.toList());
        }
        return pagedList;
    }

    private List<Predicate<Car>> configFilter(Filter<Car> filter) {
        List<Predicate<Car>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<Car> idPredicate = (Car c) -> c.getId().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }

        if (filter.hasParam("minPrice") && filter.hasParam("maxPrice")) {
            Predicate<Car> minMaxPricePredicate = (Car c) -> c.getPrice()
                    >= Double.valueOf((String) filter.getParam("minPrice")) && c.getPrice()
                    <= Double.valueOf((String) filter.getParam("maxPrice"));
            predicates.add(minMaxPricePredicate);
        } else if (filter.hasParam("minPrice")) {
            Predicate<Car> minPricePredicate = (Car c) -> c.getPrice()
                    >= Double.valueOf((String) filter.getParam("minPrice"));
            predicates.add(minPricePredicate);
        } else if (filter.hasParam("maxPrice")) {
            Predicate<Car> maxPricePredicate = (Car c) -> c.getPrice()
                    <= Double.valueOf((String) filter.getParam("maxPrice"));
            predicates.add(maxPricePredicate);
        }

        if (has(filter.getEntity())) {
            Car filterEntity = filter.getEntity();
            if (has(filterEntity.getModel())) {
                Predicate<Car> modelPredicate = (Car c) -> c.getModel().toLowerCase().contains(filterEntity.getModel().toLowerCase());
                predicates.add(modelPredicate);
            }

            if (has(filterEntity.getPrice())) {
                Predicate<Car> pricePredicate = (Car c) -> c.getPrice().equals(filterEntity.getPrice());
                predicates.add(pricePredicate);
            }

            if (has(filterEntity.getName())) {
                Predicate<Car> namePredicate = (Car c) -> c.getName().toLowerCase().contains(filterEntity.getName().toLowerCase());
                predicates.add(namePredicate);
            }
        }
        return predicates;
    }

    public List<String> getModels(String query) {
        return allCars.stream().filter(c -> c.getModel()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Car::getModel)
                .collect(Collectors.toList());
    }

    public void insert(Car car) {
        validate(car);
        car.setId(allCars.stream()
                .mapToInt(c -> c.getId())
                .max()
                .getAsInt()+1);
        allCars.add(car);
    }

    public void validate(Car car) {
        BusinessException be = new BusinessException();
        if (!car.hasModel()) {
            be.addException(new BusinessException("Car model cannot be empty"));
        }
        if (!car.hasName()) {
           be.addException(new BusinessException("Car name cannot be empty"));
        }

        if (!has(car.getPrice())) {
            be.addException(new BusinessException("Car price cannot be empty"));
        }

        if (allCars.stream().filter(c -> c.getName().equalsIgnoreCase(car.getName())
                && c.getId() != c.getId()).count() > 0) {
            be.addException(new BusinessException("Car name must be unique"));
        }
        if(has(be.getExceptionList())) {
            throw be;
        }
    }


    public void remove(Car car) {
        allCars.remove(car);
    }

    public long count(Filter<Car> filter) {
        return allCars.stream()
                .filter(configFilter(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Car findById(Integer id) {
        return allCars.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Car not found with id " + id));
    }

    public void update(Car car) {
        validate(car);
        allCars.remove(allCars.indexOf(car));
        allCars.add(car);
    }
}
