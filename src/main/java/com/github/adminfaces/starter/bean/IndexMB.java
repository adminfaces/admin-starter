/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adminfaces.starter.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author rmpestano
 */
@Named
@ViewScoped
public class IndexMB implements Serializable {

    List<String> itens;

    List<String> subItens;

    List<String> filteredSubItens = new ArrayList<>();

    String selectedItem;

    String selectedSubItem;

    @PostConstruct
    public void init() {
        itens = new ArrayList<>(15);
        subItens = new ArrayList<>(150);

        IntStream.rangeClosed(1, 15)
            .forEach(i -> {
                itens.add("item " + i);
                IntStream.rangeClosed(1, 10)
                    .forEach(j -> {
                        subItens.add("subitem " + i + "" + j);
                    });
            });
    }

    public List<String> getItens() {
        return itens;
    }

    public void setItens(List<String> itens) {
        this.itens = itens;
    }

    public List<String> getFilteredSubItens() {
        return filteredSubItens;
    }

    public void setFilteredSubItens(List<String> filteredSubItens) {
        this.filteredSubItens = filteredSubItens;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getSelectedSubItem() {
        return selectedSubItem;
    }

    public void setSelectedSubItem(String selectedSubItem) {
        this.selectedSubItem = selectedSubItem;
    }

    public void onItemChange() {
        filteredSubItens.clear();
        subItens.stream().filter(subItem -> selectedItem != null && subItem.contains(selectedItem))
            .forEach(filteredSubItens::add);
    }
}
