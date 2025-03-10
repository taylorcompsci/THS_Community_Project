package component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import name.modid.THSCommunityProject;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import javax.lang.model.type.PrimitiveType;

public class ModComponents
{
    //type is Codec.TYPE for example Integer would Codec.INT
    public static <T> ComponentType<T> registerComponent(String path, PrimitiveCodec<T> type){
        return Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Identifier.of(THSCommunityProject.MOD_ID, path),
                ComponentType.<T>builder().codec(type).build()
        );
    }

    public static final ComponentType<Integer> count_component = registerComponent("count_component", Codec.INT);

    public static void initialize(){
        THSCommunityProject.LOGGER.info("Registering {} componenets", THSCommunityProject.MOD_ID);
    }

}
