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


public class ModItemGroup {
    private static final  HashMap<ItemGroup, RegistryKey<ItemGroup>> itemGroupToPath = new HashMap<>();

    public static final ItemGroup TEST_GROUP = addItemGroup("test_group", ModItem.TEST_ITEM);


    public static ItemGroup addItemGroup(String path, ModItem icon){
        // RegistryKey<ItemGroup> itemGroupKey = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(THSCommunityProject.MOD_ID, path));
        Identifier itemGroupKey = Identifier.of(THSCommunityProject.MOD_ID, path);
        ItemGroup foo =  Registry.register(Registries.ITEM_GROUP, itemGroupKey,
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(icon))
                        .displayName(Text.translatable(String.format("itemgroup.ths-community-project.%s", path)))
                        .build());
       // itemGroupToPath.put(foo, null);
        return foo;
    }

    public static RegistryKey<ItemGroup> retrieveItemGroupKey(ItemGroup itemGroup){
        return itemGroupToPath.get(itemGroup);
    }

    public static void registerModItemGroups(){
        System.out.println(itemGroupToPath.get(TEST_GROUP));
        THSCommunityProject.LOGGER.info("Item groups are registering!");
    }
}
