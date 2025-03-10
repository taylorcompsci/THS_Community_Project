package name.modid.item;


import name.modid.THSCommunityProject;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.function.Function;

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
    public static final ModItem TEST_ITEM = registerItem("test_item", ModItem::new, new Settings(), ModItemGroup.test_group_key);
    //foodItem
    public static final ModItem TEST_ITEM2 = registerItem("test_item2", ModItem::new, new Settings().food(
            new FoodComponent.Builder().alwaysEdible().nutrition(5).build(),
            ConsumableComponents.food().consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.GLOWING, 6*20,1),1.0f)).build()
                    ),
            ItemGroups.FOOD_AND_DRINK
            );
    public final static ModItem LIGHTNING_STICK = registerItem("lightning_stick", LightningStick::new, new Settings(), ItemGroups.TOOLS);
    //Once item is registered, head over to resources/assets.ths-community-project/lang/en_us.json to register the item's name for formatting.
    public static ModItem registerItem(String path, Function<Settings, ModItem> itemFactory, Settings settings, RegistryKey<ItemGroup> itemGroup){
           RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(THSCommunityProject.MOD_ID, path));

           ModItem item = itemFactory.apply(settings.registryKey(itemKey));

           ModItem actualItem =  Registry.register(Registries.ITEM, itemKey, item);
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
