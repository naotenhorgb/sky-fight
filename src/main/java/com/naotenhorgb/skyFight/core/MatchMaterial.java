package com.naotenhorgb.skyFight.core;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


@SuppressWarnings("deprecation")
public class MatchMaterial {

    public Material getMaterial(String string) {
        if(!XMaterial.matchXMaterial(string).isPresent()){
            return XMaterial.valueOf("AIR").parseMaterial();
        }
        return XMaterial.matchXMaterial(string).get().parseMaterial();
    }

    public ItemStack getItemStack(String string) {
        if(!XMaterial.matchXMaterial(string).isPresent()){
            return XMaterial.valueOf("AIR").parseItem();
        }
        return XMaterial.matchXMaterial(string).get().parseItem();
    }

}
