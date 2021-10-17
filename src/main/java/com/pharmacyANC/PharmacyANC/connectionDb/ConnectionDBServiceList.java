package com.pharmacyANC.PharmacyANC.connectionDb;

import com.pharmacyANC.PharmacyANC.busines.model.Items;
import com.pharmacyANC.PharmacyANC.busines.model.ListShopping;
import com.pharmacyANC.PharmacyANC.busines.model.ShoppingList;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConnectionDBServiceList {
    private static final String dbServer = "postgresql-54685-0.cloudclusters.net"; // change it to your database server name
    private static final int dbPort = 18702; // change it to your database server port
    private static final String dbName = "Pharmacy-ANC";
    private static final String userName = "ViktorRoot";
    private static final String password = "Viktor547";
    private static final String DB = String.format("jdbc:postgresql://%s:%d/%s?user=%s&password=%s",
            dbServer, dbPort, dbName, userName, password);

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(DB);

        } catch (SQLException throwaways) {
            throwaways.printStackTrace();
        }
    }


    public List<ListShopping> getLists() {
        List<ListShopping> shoppingLists = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM list");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ListShopping shoppingList = new ListShopping();
                shoppingList.setId(resultSet.getLong("list_id"));
                shoppingList.setShoppingListName(resultSet.getString("name"));
                shoppingList.setSize(resultSet.getInt("size"));
                shoppingList.setCreateAt(resultSet.getString("createat"));
                shoppingLists.add(shoppingList);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return shoppingLists;
    }

    public Items getItemsName(String itemName) {
        Items items = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM items WHERE item_name=?");
            preparedStatement.setString(1, itemName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                items = new Items();
                items.setItemId(resultSet.getLong("item_id"));
                items.setItemName(resultSet.getString("item_name"));
                items.setMake(resultSet.getBoolean("make"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return items;
    }

    public ShoppingList addList(ShoppingList list) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO list VALUES(DEFAULT,?,?)");
            preparedStatement.setString(1, list.getShoppingListName());
            preparedStatement.setInt(2, list.getSize());
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM list");
            ResultSet resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()) {
                list.setId(resultSet.getLong("list_id"));
                list.setShoppingListName(resultSet.getString("name"));
                list.setSize(resultSet.getInt("size"));
                list.setCreateAt(resultSet.getString("createat"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void addItemsList(Long id, Long itemId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO items_list VALUES(?,?)");
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, itemId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ShoppingList getById(Long id) {
        ShoppingList shoppingList = new ShoppingList();
        List<Items> itemsArrayList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT l.list_id, name, size, createAt, i.item_id, item_name, make FROM list l inner join items_list il on l.list_id = il.list_id inner join items i on il.item_id = i.item_id where l.list_id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                shoppingList.setId(resultSet.getLong("list_id"));
                shoppingList.setShoppingListName(resultSet.getString("name"));
                shoppingList.setSize(resultSet.getInt("size"));
                shoppingList.setCreateAt(resultSet.getString("createat"));
                Items items = new Items();
                items.setItemId(resultSet.getLong("item_id"));
                items.setItemName(resultSet.getString("item_name"));
                items.setMake(resultSet.getBoolean("make"));
                itemsArrayList.add(items);
                shoppingList.setItems(itemsArrayList);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return shoppingList;
    }


    public ShoppingList getListBy(Long id) {
        ShoppingList shoppingList = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM list WHERE list_id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                shoppingList = new ShoppingList();
                shoppingList.setId(resultSet.getLong("list_id"));
                shoppingList.setShoppingListName(resultSet.getString("name"));
                shoppingList.setSize(resultSet.getInt("size"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return shoppingList;
    }

    public void updateList(Integer size, Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE list SET size=? WHERE list_id=?");
            preparedStatement.setInt(1, size);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateListItems(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM items_list WHERE item_id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Items markItem(Long itemId, boolean make) {
        Items items = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE items SET make=? WHERE item_id=?");
            preparedStatement.setBoolean(1, make);
            preparedStatement.setLong(2, itemId);
            preparedStatement.executeUpdate();
            Statement statement = connection.createStatement();
            String SQL = String.format("SELECT * FROM items WHERE item_id=%s", itemId);
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                items = new Items();
                items.setItemId(resultSet.getLong("item_id"));
                items.setItemName(resultSet.getString("item_name"));
                items.setMake(resultSet.getBoolean("make"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return items;
    }

    public void deleteList(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM list WHERE list_id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
