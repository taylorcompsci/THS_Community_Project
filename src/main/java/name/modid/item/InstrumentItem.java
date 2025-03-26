package name.modid.item;

import name.modid.THSCommunityProject;
import net.fabricmc.api.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import oshi.hardware.Display;

import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;


public class InstrumentItem extends ModItem implements ModInitializer {

    private boolean isGUIShown = false;
    private DisplayInfo displayInfo;

    private InstrumentItem(Settings settings, DisplayInfo displayInfo) {
        super(settings);
        this.displayInfo = displayInfo;
    }

    public static Function<Settings, InstrumentItem> build(DisplayInfo info){

        return new Function<Settings, InstrumentItem>() {
            @Override
            public InstrumentItem apply(Settings settings1) {
                return new InstrumentItem(settings1,info);
            }
        };
    }

    private void toggleGUI(PlayerEntity user){
        isGUIShown = !isGUIShown;
        if(isGUIShown){
            //show gui
        }
        else{
            //hideGui
        }
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand){
        if(world.isClient) return ActionResult.PASS;
        toggleGUI(user);
        return ActionResult.SUCCESS;
    }

    @Override
    public void onInitialize(){
    }

}

