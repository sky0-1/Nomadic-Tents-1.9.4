package com.yurtmod.proxies;

import org.apache.commons.lang3.ArrayUtils;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import com.yurtmod.block.BlockTepeeWall;
import com.yurtmod.init.Content;
import com.yurtmod.init.NomadicTents;
import com.yurtmod.structure.StructureType;

import it.unimi.dsi.fastutil.ints.IntArrays;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy 
{
	@Override
	public void preInitRenders() 
	{
		registerVariantRenders();

		// register items		
		register(Content.ITEM_TENT_CANVAS);
		register(Content.ITEM_YURT_WALL);
		register(Content.ITEM_TEPEE_WALL);
		register(Content.ITEM_BEDOUIN_WALL);
		register(Content.ITEM_MALLET);
		register(Content.ITEM_SUPER_MALLET);
		// register blocks
		register(Content.TENT_BARRIER);
		register(Content.SUPER_DIRT);
		//// yurt blocks
		register(Content.YURT_WALL_OUTER);
		register(Content.YURT_WALL_INNER);
		register(Content.YURT_ROOF);
		//// bedouin blocks
		register(Content.BEDOUIN_WALL);
		register(Content.BEDOUIN_ROOF);
		//// door blocks		
		register(Content.YURT_DOOR_SMALL);
		register(Content.YURT_DOOR_MEDIUM);
		register(Content.YURT_DOOR_LARGE);	
		register(Content.TEPEE_DOOR_SMALL);
		register(Content.TEPEE_DOOR_MEDIUM);
		register(Content.TEPEE_DOOR_LARGE);
		register(Content.BEDOUIN_DOOR_SMALL);
		register(Content.BEDOUIN_DOOR_MEDIUM);
		register(Content.BEDOUIN_DOOR_LARGE);
		//// frame blocks
		int[] progress = new int[] {0,1,2,3,4,5,6,7};
		register(Content.FRAME_YURT_WALL, progress);
		register(Content.FRAME_YURT_ROOF, progress);
		register(Content.FRAME_TEPEE_WALL, progress);
		register(Content.FRAME_BEDOUIN_WALL, progress);
		register(Content.FRAME_BEDOUIN_ROOF, progress);
	}

	private void registerVariantRenders()
	{
		int len = StructureType.values().length;
		ResourceLocation[] RLTentItem = new ResourceLocation[len];
		ResourceLocation[] RLTepeeWall = new ResourceLocation[BlockTepeeWall.NUM_TEXTURES];
		for(int i = 0; i < len; i++)
		{
			String modelName = NomadicTents.MODID + ":" + StructureType.getName(i);
			RLTentItem[i] = new ResourceLocation(modelName);
			register(Content.ITEM_TENT, modelName, i);
		}
		for(int j = 0; j < BlockTepeeWall.NUM_TEXTURES; j++)
		{
			String modelName = Content.IB_TEPEE_WALL.getRegistryName() + "_" + j;
			RLTepeeWall[j] = new ResourceLocation(modelName);
			register(Content.IB_TEPEE_WALL, modelName, j);
		}
		ModelBakery.registerItemVariants(Content.ITEM_TENT, RLTentItem);
		ModelBakery.registerItemVariants(Content.IB_TEPEE_WALL, RLTepeeWall);
	}

	private void register(Item i, String name, int... meta)
	{
		if(meta.length < 1) meta = new int[] {0};
		for(int m : meta)
		{
			ModelLoader.setCustomModelResourceLocation(i, m, new ModelResourceLocation(name, "inventory"));
		}
	}

	private void register(Item i, int... meta)
	{
		register(i, i.getRegistryName().toString(), meta);
	}

	private void register(Block b, int... meta)
	{
		Item i = Item.getItemFromBlock(b);
		if(i != null)
		{
			register(i, meta);
		} else System.out.println("Tried to register render for a null ItemBlock. Skipping.");
	}
}
