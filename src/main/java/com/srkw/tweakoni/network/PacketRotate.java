package com.srkw.tweakoni.network;

import com.srkw.tweakoni.BlockRotate;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRotate implements IMessage {
	
	private BlockPos blockPos;
	
	// just a needed placeholder
	public PacketRotate() {}
	
	 public PacketRotate(BlockPos bp) {
		blockPos = bp;
	}

	 @Override
	    public void fromBytes(ByteBuf buf) {
	        blockPos = BlockPos.fromLong(buf.readLong());
	    }

	    @Override
	    public void toBytes(ByteBuf buf) {
	        buf.writeLong(blockPos.toLong());
	    }
	    
	    public static class Handler implements IMessageHandler<PacketRotate, IMessage> {
	    	  // Do note that the default constructor is required, but implicitly defined in this case
	    	public Handler() {
				
			}

	    	  @Override public IMessage onMessage(PacketRotate message, MessageContext ctx) {
	    		  System.out.println("Message get!");
	    	    // This is the player the packet was sent to the server from
	    	    EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
	    	    // The value that was sent
	    	    BlockPos bp = message.blockPos;
	    	    System.out.println("value get! : " + bp);
	    	    World world = serverPlayer.getServerWorld();
	    	    IBlockState state = world.getBlockState(bp);
	    	    // Execute the action on the main server thread by adding it as a scheduled task
	    	    serverPlayer.getServerWorld().addScheduledTask(() -> {
	    	    	 if (world.isBlockLoaded(message.blockPos)) {
	    	    		 System.out.println("Block is loaded!");
	    	    		 Block block = state.getBlock();
	    	             System.out.println("The block is :" + block.getUnlocalizedName());
	    	    	try {
	    	    	IBlockState s = BlockRotate.rotateVanillaBlock(world, state, bp);
	    	    	 boolean b = world.setBlockState(bp,s);
	    	         System.out.println("Block rotated state : " + s);
	    	         System.out.println(b ? "This Block can't be rotated!":"This Block have Been rotated!");
	    	    	}catch(IllegalArgumentException e) {
	    	    		
	    	    	}}
	    	    });
	    	    // No response packet
	    	    return null;
	    	  }
}}
