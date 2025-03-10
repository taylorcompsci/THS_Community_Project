package name.modid.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.*;

public class LightningStick extends ModItem{

    public LightningStick(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand){
        if(world.isClient){
            return ActionResult.PASS;
        }

        BlockPos frontOfPlayer = user.getBlockPos().offset(user.getHorizontalFacing(),10);

        LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        lightning.setPosition(frontOfPlayer.toCenterPos());
        world.spawnEntity(lightning);

        return ActionResult.SUCCESS;
    }
}
