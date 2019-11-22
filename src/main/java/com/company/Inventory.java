package com.company;
import java.util.ArrayList;

/**
 *  Class Item
 *  Represents in-game inventory
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class Inventory implements iForm {

    private int maxItems = 10;
    private ArrayList<Item> items = new ArrayList<Item>();

    @Override
    public void Render() {

    }

    @Override
    public void StartRender() {

    }

    public boolean addItem(Item item) {
        if (!item.getSolid() && items.size() < maxItems) {

            items.add(item);
            return true;
        }

        return false;
    }

    public boolean hasItem(Item item) {
        return items.contains(item);
    }

    public boolean hasItem(String item) {
        return items.stream().filter(x -> x.getName().equals(item)).findFirst().orElse(null) != null;
    }

    public void removeItem(String item) {
        Item itm = items.stream().filter(x -> x.getName().equals(item)).findFirst().orElse(null);

        if (itm != null) {
            items.remove(itm);
        }
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public int getNumberOfItems() {
        return items.size();
    }

    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }

    public int getMaxItems() {
        return maxItems;
    }

    public boolean canCombineItems(Item a, Item b) {
        if (a instanceof ItemRug && b instanceof ItemDirtyJug) {
            return true;
        }

        if (a instanceof ItemCleanJug && b instanceof ItemBarrel) {
            return true;
        }

        return false;
    }

    public Item combineItems(Item a, Item b) {
        if (a instanceof ItemRug && b instanceof ItemDirtyJug) {

            GameMap.inventory.removeItem(a);
            GameMap.inventory.removeItem(b);

            ItemCleanJug cleanJug = new ItemCleanJug("cleanJug", "cleanJugDescription");
            GameMap.inventory.addItem(cleanJug);
            return cleanJug;
        }

        if (a instanceof ItemCleanJug && b instanceof ItemBarrel) {

            GameMap.inventory.removeItem(a);

            ItemBeer beer = new ItemBeer("beer", "beerDescription");
            GameMap.inventory.addItem(beer);
            return beer;
        }

        return null;
    }

}
