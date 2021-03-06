package techreborn.items;

import java.security.InvalidParameterException;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import techreborn.client.TechRebornCreativeTabMisc;
import techreborn.init.ModItems;
import techreborn.lib.ModInfo;

public class ItemDustsSmall extends ItemTextureBase
{

	public static final String[] types = new String[] { "Almandine", "AluminumBrass", "Aluminum", "Alumite",
			"Andradite", "Antimony", "Ardite", "Ashes", "Basalt", "Bauxite", "Biotite", "Brass", "Bronze", "Cadmium",
			"Calcite", "Charcoal", "Chrome", "Cinnabar", "Clay", "Coal", "Cobalt", "Copper", "Cupronickel", "DarkAshes",
			"DarkIron", "Diamond", "Electrum", "Emerald", "EnderEye", "EnderPearl", "Endstone", "Flint", "Glowstone",
			"Gold", "Graphite", "Grossular", "Gunpowder", "Indium", "Invar", "Iridium", "Iron", "Kanthal", "Lapis",
			"Lazurite", "Lead", "Limestone", "Lodestone", "Magnesium", "Magnetite", "Manganese", "Manyullyn", "Marble",
			"Mithril", "Netherrack", "Nichrome", "Nickel", "Obsidian", "Osmium", "Peridot", "Phosphorous", "Platinum",
			"PotassiumFeldspar", "Pyrite", "Pyrope", "RedGarnet", "Redrock", "Redstone", "Ruby", "Saltpeter",
			"Sapphire", "Silicon", "Silver", "Sodalite", "Spessartine", "Sphalerite", "Steel", "Sulfur", "Tellurium",
			"Teslatite", "Tetrahedrite", "Tin", "Titanium", "Tungsten", "Uvarovite", "Vinteum", "Voidstone",
			"YellowGarnet", "Zinc", "Galena", "Olivine" };

	public ItemDustsSmall()
	{
		setUnlocalizedName("techreborn.dustsmall");
		setHasSubtypes(true);
		setCreativeTab(TechRebornCreativeTabMisc.instance);
	}

	public static ItemStack getSmallDustByName(String name, int count)
	{
		for (int i = 0; i < types.length; i++)
		{
			if (types[i].equalsIgnoreCase(name))
			{
				return new ItemStack(ModItems.smallDusts, count, i);
			}
		}
		throw new InvalidParameterException("The small dust " + name + " could not be found.");
	}

	public static ItemStack getSmallDustByName(String name)
	{
		return getSmallDustByName(name, 1);
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
		return ModInfo.MOD_ID + ":items/smallDust/small" + types[damage] + "Dust";
	}

	@Override
	public int getMaxMeta()
	{
		return types.length;
	}

}
