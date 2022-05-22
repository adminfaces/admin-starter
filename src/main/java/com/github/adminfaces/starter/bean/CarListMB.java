package com.github.adminfaces.starter.bean;

import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.model.Car;
import com.github.adminfaces.starter.service.CarService;
import com.github.adminfaces.template.exception.BusinessException;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

/**
 * Created by rmpestano on 12/02/17.
 */
@Named
@ViewScoped
public class CarListMB implements Serializable {

    @Inject
    CarService carService;

    Integer id;

    LazyDataModel<Car> cars;

    Filter<Car> filter = new Filter<>(new Car());

    List<Car> selectedCars; //cars selected in checkbox column

    List<Car> filteredValue;// datatable filteredValue attribute (column filters)

    @PostConstruct
    public void initDataModel() {
        cars = new LazyDataModel<Car>() {

            @Override
            public int count(Map<String, FilterMeta> map) {
                return (int) carService.count(filter);
            }

            @Override
            public List<Car> load(int first, int pageSize, Map<String, SortMeta> sortMap, Map<String, FilterMeta> filterMap) {
                if (has(sortMap)) {
                    sortMap.entrySet().stream()
                            .findAny()
                            .ifPresent(sortField -> {
                                        filter.setSortField(sortField.getKey());
                                        filter.setSortOrder(getSortOrder(SortOrder.ASCENDING == sortField.getValue().getOrder(), sortField.getValue().getOrder() == SortOrder.DESCENDING));
                                    }
                            );
                }
                filter.setFirst(first).setPageSize(pageSize);
                if (has(filterMap)) {
                    filter.setParams(filterMap.entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                    );
                }
                return carService.paginate(filter);
            }

            private com.github.adminfaces.starter.infra.model.SortOrder getSortOrder(boolean asc, boolean desc) {
                return asc ?
                        com.github.adminfaces.starter.infra.model.SortOrder.ASCENDING : desc ?
                        com.github.adminfaces.starter.infra.model.SortOrder.DESCENDING : com.github.adminfaces.starter.infra.model.SortOrder.UNSORTED;
            }

            @Override
            public Car getRowData(String key) {
                return carService.findById(Integer.valueOf(key));
            }

            @Override
            public String getRowKey(Car car) {
                return car.getId().toString();
            }
        };
    }

    public void clear() {
        filter = new Filter<>(new Car());
    }

    public List<String> completeModel(String query) {
        List<String> result = carService.getModels(query);
        return result;
    }

    public void findCarById(Integer id) {
        if (id == null) {
            throw new BusinessException("Provide Car ID to load");
        }
        selectedCars = Arrays.asList(carService.findById(id));
    }

    public void delete() {
        int numCars = 0;
        for (Car selectedCar : selectedCars) {
            numCars++;
            carService.remove(selectedCar);
        }
        selectedCars.clear();
        addDetailMessage(numCars + " cars deleted successfully!");
    }

    public List<Car> getSelectedCars() {
        return selectedCars;
    }

    public List<Car> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<Car> filteredValue) {
        this.filteredValue = filteredValue;
    }

    public void setSelectedCars(List<Car> selectedCars) {
        this.selectedCars = selectedCars;
    }

    public LazyDataModel<Car> getCars() {
        return cars;
    }

    public void setCars(LazyDataModel<Car> cars) {
        this.cars = cars;
    }

    public Filter<Car> getFilter() {
        return filter;
    }

    public void setFilter(Filter<Car> filter) {
        this.filter = filter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
