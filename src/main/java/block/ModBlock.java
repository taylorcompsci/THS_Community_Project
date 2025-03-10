package block;

import name.modid.THSCommunityProject;
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
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import java.util.HashMap;

//because settings are private, I had to inherit, so I can make an accessor.
public class ModBlock extends Block {

    public static HashMap<ModBlock, RegistryKey<ItemGroup>> groupRegister = new HashMap<>();

    public static final ModBlock TEST_BLOCK = register(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(4f).requiresTool(),"test_block", true, ItemGroups.BUILDING_BLOCKS);


    Settings settings;
    public ModBlock(Settings settings){
        super(settings);
        this.settings = settings;
    }

    public static ModBlock register(Settings settings, String path, boolean registerItem, RegistryKey<ItemGroup> itemGroup){
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(THSCommunityProject.MOD_ID, path));
        ModBlock modBlock = new ModBlock(settings.registryKey(blockKey));

        if(registerItem){
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(THSCommunityProject.MOD_ID, path));
            BlockItem blockItem = new BlockItem(modBlock, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }
        groupRegister.put(modBlock, itemGroup);
        return Registry.register(Registries.BLOCK, blockKey, modBlock);
    }

    public static void registerModBlocks(){

        THSCommunityProject.LOGGER.info("Registering mod blocks!");

        for(ModBlock modBlock : groupRegister.keySet()){
            ItemGroupEvents.modifyEntriesEvent(groupRegister.get(modBlock)).register(entries -> {
                entries.add(modBlock.asItem());
            });
        }
    }


}