package techreborn.items;

import java.security.InvalidParameterException;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import techreborn.client.TechRebornCreativeTabMisc;
import techreborn.init.ModItems;
import techreborn.lib.ModInfo;

public class ItemDusts extends ItemTextureBase
{
	public static final String[] types = new String[] { "almandine", "aluminumBrass", "aluminum", "alumite",
			"andradite", "antimony", "ardite", "ashes", "basalt", "bauxite", "biotite", "brass", "bronze", "cadmium",
			"calcite", "charcoal", "chrome", "cinnabar", "clay", "coal", "cobalt", "copper", "cupronickel", "darkAshes",
			"darkIron", "diamond", "electrum", "emerald", "enderEye", "enderPearl", "endstone", "flint", "galena",
			"gold", "graphite", "grossular", "indium", "invar", "iridium", "iron", "kanthal", "lapis", "lazurite",
			"lead", "limestone", "lodestone", "magnesium", "magnetite", "manganese", "manyullyn", "marble", "mithril",
			"netherrack", "nichrome", "nickel", "obsidian", "osmium", "peridot", "phosphorous", "platinum",
			"potassiumFeldspar", "pyrite", "pyrope", "redGarnet", "redrock", "ruby", "saltpeter", "sapphire", "sawDust",
			"silicon", "silver", "sodalite", "spessartine", "sphalerite", "steel", "sulfur", "tellurium", "teslatite",
			"tetrahedrite", "tin", "titanium", "tungsten", "uvarovite", "vinteum", "voidstone", "yellowGarnet", "zinc",
			"greenSapphire", "olivine" };

	public ItemDusts()
	{
		setUnlocalizedName("techreborn.dust");
		setHasSubtypes(true);
		setCreativeTab(TechRebornCreativeTabMisc.instance);
	}

	public static ItemStack getDustByName(String name, int count)
	{
		for (int i = 0; i < types.length; i++)
		{
			if (types[i].equalsIgnoreCase(name))
			{
				return new ItemStack(ModItems.dusts, count, i);
			}
		}

		if (name.equalsIgnoreCase("glowstone"))
		{
			return new ItemStack(Items.glowstone_dust, count);
		}
		if (name.equalsIgnoreCase("redstone"))
		{
			return new ItemStack(Items.redstone, count);
		}
		if (name.equalsIgnoreCase("gunpowder"))
		{
			return new ItemStack(Items.gunpowder, count);
		}
		throw new InvalidParameterException("The dust " + name + " could not be found.");
	}

	public static ItemStack getDustByName(String name)
	{
		return getDustByName(name, 1);
	}

	@Override
	// gets Unlocalized Name depending on meta data
	public String getUnlocalizedName(ItemStack itemStack)
	{
		int meta = itemStack.getItemDamage();
		if (meta < 0 || meta >= types.length)
		{
			meta = 0;
		}

		return super.getUnlocalizedName() + "." + types[meta];
	}

	// Adds Dusts SubItems To Creative Tab
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
	{
		for (int meta = 0; meta < types.length; ++meta)
		{
			list.add(new ItemStack(item, 1, meta));
		}
	}

	@Override
	public String getTextureName(int damage)
	{
		return ModInfo.MOD_ID + ":items/dust/" + types[damage] + "Dust";
	}

	@Override
	public int getMaxMeta()
	{
		return types.length;
	}

}
