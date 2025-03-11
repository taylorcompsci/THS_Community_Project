package name.modid.item;

import net.fabricmc.api.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;


public class InstrumentItem extends ModItem implements ModInitializer {

    private boolean isGUIShown = false;


    public InstrumentItem(Settings settings) {
        super(settings);
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
