package com.pharmacyANC.PharmacyANC.busines.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListShopping {
    private Long id;
    private String shoppingListName;
    private Integer size;
    private String createAt;
}
