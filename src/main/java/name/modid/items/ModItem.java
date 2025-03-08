package name.modid.items;


import name.modid.THSCommunityProject;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import java.util.HashMap;
import java.util.Objects;

//because settings are private, I had to inherit, so I can make an accessor.
public class ModItem extends Item {

    //This is to register items under item groups.
    public static HashMap<ModItem, RegistryKey<ItemGroup>> groupRegister = new HashMap<>();

    public Settings settings;
    public ModItem(Settings settings){
        super(settings);
        this.settings = settings;
    }

    public Settings getSettings(){
        return this.settings;
    }


    //I will try to make this shorter, but it's going to take a solid min to implement....
    public static final ModItem TEST_ITEM = registerItem("test_item", new ModItem(new Item.Settings()), ItemGroups.INGREDIENTS);


    //Once item is registered, head over to resources/assets.ths-community-project/lang/en_us.json to register the item's name for formatting.
    public static <T extends ModItem> T registerItem(String path, T item, RegistryKey<ItemGroup> itemGroup){
        T actualItem =  Registry.register(Registries.ITEM, Identifier.of(THSCommunityProject.MOD_ID, path), item);
        actualItem.getSettings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(THSCommunityProject.MOD_ID, path)));
        groupRegister.put(actualItem, itemGroup);
        return actualItem;
    }

    public static void registerModItems(){
        THSCommunityProject.LOGGER.info("Registering Mod Items!");

        for(ModItem ITEM : groupRegister.keySet()){
            ItemGroupEvents.modifyEntriesEvent(groupRegister.get(ITEM)).register(entries -> {
                entries.add(ITEM);
            });
        }


    }
}
