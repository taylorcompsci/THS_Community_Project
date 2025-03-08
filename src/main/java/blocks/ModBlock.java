package blocks;

import name.modid.THSCommunityProject;
import name.modid.items.ModItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import java.util.HashMap;

//because settings are private, I had to inherit, so I can make an accessor.
public class ModBlock extends Block {

    //This is to register items under item groups.
    public static HashMap<ModBlock, RegistryKey<ItemGroup>> groupRegister = new HashMap<>();

    public Settings settings;
    public ModBlock(Settings settings){
        super(settings);
        this.settings = settings;
    }

    public Settings getSettings(){
        return this.settings;
    }
    //I will try to make this shorter, but it's going to take a solid min to implement....
   public static final ModBlock TEST_BLOCK = registerBlock("test_block", AbstractBlock.Settings.create(), ItemGroups.BUILDING_BLOCKS);


    //Once item is registered, head over to resources/assets.ths-community-project/lang/en_us.json to register the item's name for formatting.
    public static ModBlock registerBlock(String path,ModBlock.Settings settings, RegistryKey<ItemGroup> itemGroup){
        ModBlock block = new ModBlock(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(THSCommunityProject.MOD_ID, path))));
        ModBlock actualBlock = Registry.register(Registries.BLOCK, path, block);
        registerBlockItem(path, block);
        groupRegister.put(actualBlock, itemGroup);
        return actualBlock;
    }

    private static void registerBlockItem(String path, ModBlock block){
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(THSCommunityProject.MOD_ID, path));
        BlockItem item = new BlockItem(block, new ModItem.Settings().registryKey(key));
        Registry.register(Registries.ITEM,key, item);
    }


    public static void registerModBlocks(){
        THSCommunityProject.LOGGER.info("Registering Mod Blocks!");
        for(ModBlock block: groupRegister.keySet()){
            ItemGroupEvents.modifyEntriesEvent(groupRegister.get(block)).register(entries -> {
                entries.add(block);
            });
        }


    }
}
