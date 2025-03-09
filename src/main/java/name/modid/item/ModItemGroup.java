package name.modid.item;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import name.modid.THSCommunityProject;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class ModItemGroup {

    //create customKey then Item Group
    public static final RegistryKey<ItemGroup> test_group_key = createCustomKey("test_group");
    public static final ItemGroup test_group = addItemGroup("test_group", test_group_key, ModItem.TEST_ITEM);




    public static ItemGroup addItemGroup(String path, RegistryKey<ItemGroup> customKey, ModItem icon){
       return Registry.register(Registries.ITEM_GROUP, customKey,FabricItemGroup.builder()
               .icon(()-> new ItemStack(icon.asItem()))
               .displayName(Text.translatable("itemgroup."+path))
               .build());
    }

    public static RegistryKey<ItemGroup> createCustomKey(String path){
        return RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(THSCommunityProject.MOD_ID, path));
    }


    public static void registerModItemGroups(){
        THSCommunityProject.LOGGER.info("Item groups are registering!");
    }
}
