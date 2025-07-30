package com.naotenhorgb.skyFight.core;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.inventory.ItemStack;


public class MatchMaterial {

    public ItemStack getItemStack(String string) {
        if(!XMaterial.matchXMaterial(string).isPresent()){
            return XMaterial.valueOf("AIR").parseItem();
        }
        return XMaterial.matchXMaterial(string).get().parseItem();
    }

}
