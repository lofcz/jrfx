package com.company;
import org.junit.Assert;
import org.junit.Test;

/**
 *  Class Tests
 *  Used to test logic specific parts of the game
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class Tests {

    private Inventory inventory;

    @Test
    public void testCantPickSolidItem() {
        inventory = new Inventory();

        ItemBarrel barrel = new ItemBarrel("barrel", "barrelDescription");
        inventory.addItem(barrel);

        Assert.assertTrue(inventory.getNumberOfItems() == 0);
    }

    @Test
    public void testCanPickItem() {
        inventory = new Inventory();

        ItemDirtyJug jug = new ItemDirtyJug("dirtyJug", "dirtyJug");
        inventory.addItem(jug);

        Assert.assertTrue(inventory.getNumberOfItems() == 1);
    }

    @Test
    public void testCantPickTooManyItems() {
        inventory = new Inventory();
        inventory.setMaxItems(10);

        for (int i = 0; i < 20; i++) {
            ItemDirtyJug jug = new ItemDirtyJug("dirtyJug", "dirtyJug");
            inventory.addItem(jug);
        }

        Assert.assertTrue(inventory.getNumberOfItems() == 10);
    }

    @Test
    public void testCanCombineItems() {
        GameMap.inventory = new Inventory();

        ItemDirtyJug dirtyJug = new ItemDirtyJug("dirtyJug", "dirtyJugDescription");
        ItemRug rug = new ItemRug("rug", "rugDescription");

        if (GameMap.inventory.canCombineItems(rug, dirtyJug)) {
            GameMap.inventory.combineItems(rug, dirtyJug);
        }

        Assert.assertTrue(GameMap.inventory.hasItem("cleanJug"));
    }

    @Test
    public void testCanWin() {
        GameMap.inventory = new Inventory();

        ItemDirtyJug dirtyJug = new ItemDirtyJug("dirtyJug", "dirtyJugDescription");
        ItemRug rug = new ItemRug("rug", "rugDescription");
        ItemBarrel barrel = new ItemBarrel("barrel", "barrelDescription");
        ItemCleanJug cleanJug = null;
        ItemBeer beer = null;
        LevelPub levelPub = new LevelPub();

        levelPub.setPlayerHere(true);

        if (GameMap.inventory.canCombineItems(rug, dirtyJug)) {
            cleanJug = (ItemCleanJug)GameMap.inventory.combineItems(rug, dirtyJug);
        }

        if (GameMap.inventory.canCombineItems(cleanJug, barrel)) {
            beer = (ItemBeer)GameMap.inventory.combineItems(cleanJug, barrel);
        }

        if (beer != null) {
            if (levelPub.canGiveBeer()) {
                levelPub.giveBeer();
            }
        }


        Assert.assertTrue(levelPub.getState() == Utils.LEVEL_COMPLETED_CONSTANT);
    }
}
