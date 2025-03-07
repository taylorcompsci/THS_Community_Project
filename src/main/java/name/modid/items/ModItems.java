package name.modid.items;

import name.modid.THSCommunityProject;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems implements ModInitializer {
    //I will try to make this shorter but it's going to take a solid min to implement....
    public static final Item TEST_ITEM = registerItem("test_item", new Item(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(THSCommunityProject.MOD_ID, "test_item")))), ItemGroups.INGREDIENTS);



    public ModItems(){}
    //Once item is registered, head over to resources/assets.ths-community-project/lang/en_us.json to register the item's name for formatting.
    public static <T extends Item> T registerItem(String path, T item, RegistryKey<ItemGroup> itemGroup){
        T actualItem =  Registry.register(Registries.ITEM, Identifier.of(THSCommunityProject.MOD_ID, path), item);

        return actualItem;
    }

    public static void registerModItems(){
        THSCommunityProject.LOGGER.info("Registering Mod Items!");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(TEST_ITEM);
        });
    }

    @Override
    public void onInitialize() {

    }
}
