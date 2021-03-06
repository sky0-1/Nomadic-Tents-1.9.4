package com.yurtmod.init;

import java.io.File;

import com.yurtmod.dimension.TentDimension;
import com.yurtmod.event.TentSleepHandler;
import com.yurtmod.proxies.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = NomadicTents.MODID, name = NomadicTents.NAME, version = NomadicTents.VERSION, acceptedMinecraftVersions = NomadicTents.MCVERSION)
public class NomadicTents 
{
	public static final String MODID = "yurtmod";
	public static final String NAME = "Nomadic Tents";
	public static final String VERSION = "5.01";
	public static final String MCVERSION = "1.9.4";
	
	@SidedProxy(clientSide = "com." + MODID + ".proxies.ClientProxy", serverSide = "com." + MODID + ".proxies.CommonProxy")
	public static CommonProxy proxy;
	
	public static CreativeTabs tab;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		tab = new CreativeTabs("yurtMain")
		{
			@Override
			public Item getTabIconItem() 
			{
				return Content.ITEM_TENT;
			}
		};
		String path = event.getSuggestedConfigurationFile().getAbsolutePath().replaceFirst(MODID + ".cfg", "NomadicTents.cfg");
		Config.mainRegistry(new Configuration(new File(path)));
		Content.mainRegistry();
		TentDimension.mainRegistry();
		proxy.preInitRenders();
	}
	
	@EventHandler
    public void init(FMLInitializationEvent event)
    {    
		Crafting.mainRegistry();
		if(Config.SLEEP_TO_DAY_IN_TENT_DIM)
		{
			MinecraftForge.EVENT_BUS.register(new TentSleepHandler());
		}
    }
}
