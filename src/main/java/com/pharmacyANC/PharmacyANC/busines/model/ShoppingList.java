package com.pharmacyANC.PharmacyANC.busines.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingList {
    private Long id;
    private String shoppingListName;
    private Integer size;
    private String createAt;
    private List<Items> items;
}
