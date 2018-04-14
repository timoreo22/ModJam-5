package com.srkw.tweakoni;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.Block;

public class BlockRotate {
	
	public static int MAX_ID = 1024;
	public static byte[] rotateType = new byte[MAX_ID];
	public static final byte[] SIDE_OPPOSITE = { 1, 0, 3, 2, 5, 4 };
	public static final byte[] SIDE_LEFT = { 4, 5, 5, 4, 2, 3 };
	
	public static final class RotationType {

		public static final int STAIRS = 1;
		public static final int SLAB = 2;
		public static final int CHEST = 3;
		public static final int PISTON = 4;

		private RotationType() {

		}
	}

	static {

		rotateType[Block.getIdFromBlock(Blocks.STONE_SLAB)] = RotationType.SLAB;
		rotateType[Block.getIdFromBlock(Blocks.WOODEN_SLAB)] = RotationType.SLAB;
		rotateType[Block.getIdFromBlock(Blocks.STONE_SLAB2)] = RotationType.SLAB;
		rotateType[Block.getIdFromBlock(Blocks.PURPUR_SLAB)] = RotationType.SLAB;
		
		rotateType[Block.getIdFromBlock(Blocks.PISTON)] = RotationType.PISTON;
		rotateType[Block.getIdFromBlock(Blocks.STICKY_PISTON)] = RotationType.PISTON;
		rotateType[Block.getIdFromBlock(Blocks.DISPENSER)] = RotationType.PISTON;
		rotateType[Block.getIdFromBlock(Blocks.DROPPER)] = RotationType.PISTON;
		rotateType[Block.getIdFromBlock(Blocks.OBSERVER)] = RotationType.PISTON;
		
		rotateType[Block.getIdFromBlock(Blocks.TRAPPED_CHEST)] = RotationType.CHEST;
		rotateType[Block.getIdFromBlock(Blocks.CHEST)] = RotationType.CHEST;

		rotateType[Block.getIdFromBlock(Blocks.OAK_STAIRS)] = RotationType.STAIRS;
		rotateType[Block.getIdFromBlock(Blocks.STONE_STAIRS)] = RotationType.STAIRS;
		rotateType[Block.getIdFromBlock(Blocks.BRICK_STAIRS)] = RotationType.STAIRS;
		rotateType[Block.getIdFromBlock(Blocks.STONE_BRICK_STAIRS)] = RotationType.STAIRS;
		rotateType[Block.getIdFromBlock(Blocks.NETHER_BRICK_STAIRS)] = RotationType.STAIRS;
		rotateType[Block.getIdFromBlock(Blocks.SANDSTONE_STAIRS)] = RotationType.STAIRS;
		rotateType[Block.getIdFromBlock(Blocks.SPRUCE_STAIRS)] = RotationType.STAIRS;
		rotateType[Block.getIdFromBlock(Blocks.BIRCH_STAIRS)] = RotationType.STAIRS;
		rotateType[Block.getIdFromBlock(Blocks.JUNGLE_STAIRS)] = RotationType.STAIRS;
		rotateType[Block.getIdFromBlock(Blocks.QUARTZ_STAIRS)] = RotationType.STAIRS;
		rotateType[Block.getIdFromBlock(Blocks.RED_SANDSTONE_STAIRS)] = RotationType.STAIRS;
		rotateType[Block.getIdFromBlock(Blocks.PURPUR_STAIRS)] = RotationType.STAIRS;
	}


//Rotating
public static IBlockState rotateVanillaBlock(World world, IBlockState state, BlockPos pos) {
	int bId = Block.getIdFromBlock(state.getBlock()), bMeta = state.getBlock().getMetaFromState(state);
    Block block = state.getBlock();
	if(canRotate(state.getBlock())){
        
        switch (rotateType[bId]) {
            case 1:
                return block.getStateFromMeta(++bMeta % 8);
            case 2:
                return block.getStateFromMeta((bMeta + 8) % 16);
            case 3:
                BlockPos offsetPos;
                for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                    offsetPos = pos.offset(facing);
                    if (isEqual(world.getBlockState(offsetPos).getBlock(), state.getBlock())) {
                        return block.getStateFromMeta(SIDE_OPPOSITE[bMeta]);
                    }
                }
                return block.getStateFromMeta(SIDE_LEFT[bMeta]);
            case 4:
            	return block.getStateFromMeta(++bMeta % 6);
            default:
                return block.getStateFromMeta(SIDE_LEFT[bMeta]);
        }
    }
	return block.getStateFromMeta(bMeta);
    }
    public static boolean canRotate(Block block) {

        return Block.getIdFromBlock(block) < MAX_ID ;
}		//note maybe add && rotateType[Block.getIdFromBlock(block)] != 0
    public static boolean isEqual(Block blockA, Block blockB) {

		if (blockA == blockB) {
			return true;
		}
		if (blockA == null | blockB == null) {
			return false;
		}
		return blockA.equals(blockB) || blockA.isAssociatedBlock(blockB);
}


}
