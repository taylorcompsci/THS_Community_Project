package name.modid.item;

import name.modid.component.ModComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.List;

public class LightningStick extends ModItem{

    public LightningStick(Settings settings) {
        super(settings.component(ModComponents.count_component,0));
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type){
        int count = stack.get(ModComponents.count_component);
        tooltip.add(Text.translatable("item.ths-community-project.counter.info", count).formatted(Formatting.GOLD));
    }



    public ActionResult use(World world, PlayerEntity user, Hand hand){
        if(world.isClient){
            return ActionResult.PASS;
        }

        BlockPos frontOfPlayer = user.getBlockPos().offset(user.getHorizontalFacing(),10);

        LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        lightning.setPosition(frontOfPlayer.toCenterPos());
        world.spawnEntity(lightning);

        ItemStack stack = user.getStackInHand(hand);
        int count = stack.get(ModComponents.count_component);
        stack.set(ModComponents.count_component, ++count);

        return ActionResult.SUCCESS;
    }
}
