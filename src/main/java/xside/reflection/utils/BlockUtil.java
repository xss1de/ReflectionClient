package xside.reflection.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;

public class BlockUtil {
	public static IBlockState getState(BlockPos pos)
	{
		return Minecraft.getMinecraft().world.getBlockState(pos);
	}
	public static float getHardness(BlockPos pos) {
		return getState(pos).getPlayerRelativeBlockHardness(Minecraft.getMinecraft().player, Minecraft.getMinecraft().world, pos);
	}
	public static boolean isBlockEdge(EntityLivingBase entity) {
		return (Minecraft.getMinecraft().world
				.getCollisionBoxes(entity,
						entity.getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(0.001D, 0.0D, 0.001D))
				.isEmpty() && entity.onGround);
	}
}
