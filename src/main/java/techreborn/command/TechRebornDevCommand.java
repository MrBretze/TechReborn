package techreborn.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.Fluid;
import reborncore.api.fuel.FluidPowerManager;
import techreborn.api.recipe.RecipeHandler;

public class TechRebornDevCommand extends CommandBase
{

	@Override
	public String getCommandName()
	{
		return "trdev";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "commands.forge.usage";
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			sender.addChatMessage(new TextComponentString("You need to use arguments, see /trdev help"));
		} else if ("help".equals(args[0]))
		{
			sender.addChatMessage(new TextComponentString("recipes 	- Shows size of the recipe array"));
			sender.addChatMessage(new TextComponentString("fluid     	- Lists the fluid power values"));
		} else if ("recipes".equals(args[0]))
		{
			sender.addChatMessage(new TextComponentString(RecipeHandler.recipeList.size() + " recipes loaded"));
		} else if ("fluid".equals(args[0]))
		{
			for (Object object : FluidPowerManager.fluidPowerValues.keySet().toArray())
			{
				if (object instanceof Fluid)
				{
					Fluid fluid = (Fluid) object;
					sender.addChatMessage(new TextComponentString(
							fluid.getUnlocalizedName() + " : " + FluidPowerManager.fluidPowerValues.get(fluid)));
				} else
				{
					sender.addChatMessage(new TextComponentString("Found invalid fluid entry"));
				}
			}
		} else if ("clear".equals(args[0]))
		{
			EntityPlayerMP playerMP = (EntityPlayerMP) sender;
			List<Block> blocksToRemove = new ArrayList<>();
			blocksToRemove.add(Blocks.grass);
			blocksToRemove.add(Blocks.dirt);
			blocksToRemove.add(Blocks.stone);
			for (int x = 0; x < 25; x++)
			{
				for (int z = 0; z < 25; z++)
				{
					for (int y = 0; y < playerMP.posY; y++)
					{
						BlockPos pos = new BlockPos(playerMP.posX + x, y, playerMP.posZ + z);
						if (blocksToRemove.contains(playerMP.worldObj.getBlockState(pos).getBlock()))
						{
							playerMP.worldObj.setBlockState(pos, Blocks.air.getDefaultState(), 2);
						}
					}
				}
			}
		}
	}
}
