package com.pharmacyANC.PharmacyANC.busines.controller;

import com.pharmacyANC.PharmacyANC.busines.model.Items;
import com.pharmacyANC.PharmacyANC.busines.model.ListShopping;
import com.pharmacyANC.PharmacyANC.busines.model.ShoppingList;
import com.pharmacyANC.PharmacyANC.busines.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/list")
public class ListController {

    private final ShoppingListService shoppingListService;

    @Autowired
    public ListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }


    @GetMapping
    public List<ListShopping> getShoppingList() {
        return shoppingListService.getShoppingList();
    }


    @GetMapping("{id}")
    public ShoppingList getListByIdAndItems(@PathVariable("id") Long id) {
        return shoppingListService.getListById(id);
    }

    @PostMapping
    public ShoppingList addList(@RequestBody ShoppingList shoppingList) {
        return shoppingListService.addList(shoppingList);
    }

    @PutMapping("{id}")
    public Items addListItems(@PathVariable("id") Long id, @RequestBody Items items) {
        return shoppingListService.addListItems(id, items);
    }

    @PutMapping("/item/{itemId}/{make}")
    private Items markItem(@PathVariable("itemId") Long itemId, @PathVariable("make") boolean make) {
        return shoppingListService.markItem(itemId, make);
    }


    @DeleteMapping("{id}")
    public void deleteList(@PathVariable("id") Long id) {
        shoppingListService.deleteList(id);
    }

    @DeleteMapping("{idList}/items/{idItems}")
    public void deleteItemsFromList(@PathVariable("idList") Long idList, @PathVariable("idItems") Long idItems) {
        shoppingListService.deleteItemsFromList(idList, idItems);
    }
}
