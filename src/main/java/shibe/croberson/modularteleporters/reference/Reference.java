package shibe.croberson.modularteleporters.reference;

import java.util.HashMap;

public class Reference {
	public static final String NAME = "Modular Teleporters";
	public static final String MODID = "modularteleporters";
	public static final String VERSION = "dev";

	public static String resourcePrefix = MODID + ":";
	
	public static float metalHardness = 3.00F;
	
	public static int teleporterMaxHeight = 130; //adjust later for config values
	public static int teleporterMaxSize = 130;
	
	public static int catalystMaxSize = 130;
	public static int catalystMaxHeight = 150;
	/** Register your fluid here with a value of efficiency **/
	public static HashMap teleporterFluidEfficiencies = new HashMap();
	
	
}