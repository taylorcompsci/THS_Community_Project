package blocks;

import name.modid.THSCommunityProject;
import name.modid.items.ModItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
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
    public static HashMap<ModItem, RegistryKey<ItemGroup>> groupRegister = new HashMap<>();

    public Settings settings;
    public ModBlock(Settings settings){
        super(settings);
        this.settings = settings;
    }

    public Settings getSettings(){
        return this.settings;
    }


    //I will try to make this shorter, but it's going to take a solid min to implement....
   // public static final name.modid.items.ModItem TEST_ITEM = registerItem("test_item", new name.modid.items.ModItem(new Item.Settings()), ItemGroups.INGREDIENTS);


    //Once item is registered, head over to resources/assets.ths-community-project/lang/en_us.json to register the item's name for formatting.
    public static <T extends ModBlock> T registerItem(String path, T block, RegistryKey<ItemGroup> itemGroup){
        T actualBlock = Registry.register(Registries.BLOCK, Identifier.of(THSCommunityProject.MOD_ID, path), block);
        actualBlock.getSettings().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(THSCommunityProject.MOD_ID, path)));
        return actualBlock;
    }

    public static void registerModBlocks(){
        THSCommunityProject.LOGGER.info("Registering Mod Blocks!");
        


    }
}
