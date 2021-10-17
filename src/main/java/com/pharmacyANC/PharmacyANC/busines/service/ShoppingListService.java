package com.pharmacyANC.PharmacyANC.busines.service;

import com.pharmacyANC.PharmacyANC.busines.model.Items;
import com.pharmacyANC.PharmacyANC.busines.model.ListShopping;
import com.pharmacyANC.PharmacyANC.busines.model.ShoppingList;
import com.pharmacyANC.PharmacyANC.connectionDb.ConnectionDBServiceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingListService {

    private final ConnectionDBServiceList connectionDBServiceList;

    @Autowired
    public ShoppingListService(ConnectionDBServiceList connectionDBServiceList) {
        this.connectionDBServiceList = connectionDBServiceList;
    }

    public List<ListShopping> getShoppingList() {
        return connectionDBServiceList.getLists();
    }

    public ShoppingList addList(ShoppingList list) {
        Items item;
        List<Items> newLIst = new ArrayList<>();
        list.setSize(list.getItems().size());
        ShoppingList shoppingList = connectionDBServiceList.addList(list);
        List<Items> lists = list.getItems();
        for (Items items : lists) {
            String fieldValue = items.getItemName();
            item = new Items();
            item = connectionDBServiceList.getItemsName(fieldValue);
            newLIst.add(item);
            shoppingList.setItems(newLIst);
            connectionDBServiceList.addItemsList(shoppingList.getId(), item.getItemId());
        }
        return shoppingList;
    }


    public ShoppingList getListById(Long id) {
        return connectionDBServiceList.getById(id);
    }

    public void deleteList(Long id) {
        connectionDBServiceList.deleteList(id);
    }

    public Items addListItems(Long id, Items items) {
        List<Items> list = new ArrayList<>();
        items = connectionDBServiceList.getItemsName(items.getItemName());
        ShoppingList shoppingList = connectionDBServiceList.getListBy(id);
        if (shoppingList.getSize() != 0) {
            ShoppingList shoppingList1 = getListById(id);
            list = shoppingList1.getItems();
        }
        list.add(items);
        connectionDBServiceList.updateList(list.size(), id);
        connectionDBServiceList.addItemsList(id, items.getItemId());
        return items;
    }

    public void deleteItemsFromList(Long idList, Long idItems) {
        List<Items> itemsList = new ArrayList<>();
        connectionDBServiceList.updateListItems(idItems);
        ShoppingList shoppingList = connectionDBServiceList.getListBy(idList);
        shoppingList.setItems(itemsList);
        connectionDBServiceList.updateList(shoppingList.getItems().size(), idList);
    }

    public Items markItem(Long itemId, boolean make) {
        return connectionDBServiceList.markItem(itemId, make);
    }
}
