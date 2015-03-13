package shibe.croberson.modularteleporters.common.fluid;

import shibe.croberson.modularteleporters.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class MTFluids {
	
	public static Block teleporterJuiceStill;
	public static Block colaStill;
	
	public static Fluid teleporterJuice;
	public static Fluid cola;
	
	public static void init() {
		registerFluids();
	}
	
	public static void registerFluids() {
		teleporterJuice = new Fluid("teleporterJuice");
		FluidRegistry.registerFluid(teleporterJuice); //needs to be a thing before you set values or else the fluid wont know what the hell you're talking about
		teleporterJuice.setDensity(1200);
		teleporterJuice.setGaseous(false);
		teleporterJuice.setLuminosity(8);
		teleporterJuice.setTemperature(295);
		teleporterJuice.setViscosity(1337);
		Reference.teleporterFluidEfficiencies.put(teleporterJuice.getName(), 1.4); //140% of normal value
		
		cola = new Fluid("cola");
		FluidRegistry.registerFluid(cola);
		cola.setDensity(1000);
		cola.setLuminosity(0);
		cola.setTemperature(295);
		cola.setViscosity(1000);
		cola.setGaseous(false);
		//if(config.shouldThisBeAThing()){
			Reference.teleporterFluidEfficiencies.put(cola.getName(), 1.16666); // 116.666% of normal value
		//}
		BlockMTFluid blockTeleporterJuice = new BlockMTFluid(teleporterJuice,"teleporterJuice");
		teleporterJuiceStill = blockTeleporterJuice;
		BlockMTFluid blockCola = new BlockMTFluid(cola,"cola");
		colaStill = blockCola;
		
		
		
		GameRegistry.registerBlock(teleporterJuiceStill, ItemBlock.class, teleporterJuice.getUnlocalizedName());
		GameRegistry.registerBlock(colaStill, ItemBlock.class, cola.getUnlocalizedName());
	}
	
	
	
}
