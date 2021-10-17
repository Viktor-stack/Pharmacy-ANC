package com.pharmacyANC.PharmacyANC.busines.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Items {
    private Long itemId;
    private String itemName;
    private boolean make;
}
