package com.nitnelave.CreeperHeal.block;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Rails;

import com.nitnelave.CreeperHeal.utils.CreeperUtils;

/**
 * Rail implementation of CreeperBlock.
 *
 * @author nitnelave
 *
 */
class CreeperRail extends CreeperBlock {
  private static final BlockFace[] FACES = { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH,
      BlockFace.SOUTH };
  private static final BlockFace[] UP_DOWN = { BlockFace.DOWN, BlockFace.SELF, BlockFace.UP };

  public static final Set<Material> RAIL_TYPES = CreeperUtils.createFinalHashSet(Material.RAILS,
      Material.ACTIVATOR_RAIL,
      Material.DETECTOR_RAIL,
      Material.POWERED_RAIL);

  private final Rails data;

  /*
   * Constructor.
   */
  CreeperRail(BlockState blockState) {
    super(blockState);
    data = castData(blockState, Rails.class);
  }

  /*
   * (non-Javadoc)
   *
   * @see com.nitnelave.CreeperHeal.block.CreeperBlock#update()
   */
  @Override
  public void update() {
    Rails[][] railData = new Rails[3][4];
    Block block = getBlock();
    for (int i = 0; i < 3; i++) {
      Block upBlock = block.getRelative(UP_DOWN[i]);
      for (int j = 0; j < 4; j++) {
        Block tmpBlock = upBlock.getRelative(FACES[j]);
        if (RAIL_TYPES.contains(tmpBlock.getType()))
          railData[i][j] = castData(tmpBlock.getState(), Rails.class);
      }
    }
    super.update();

    java.lang.reflect.Field theDataField = stateData.get();
    if (theDataField == null) {
      try {
        theDataField = block.getState().getClass().getDeclaredField("data");
        theDataField.setAccessible(true);
      } catch (Exception exception) {
        logger.fatal("", exception);
        return;
      }
      stateData.compareAndSet(null, theDataField);
    }

    try {
      theDataField.set(block.getState(), data);
    } catch (Exception exception) {
      logger.catching(exception);
      block.getState().setData(data);
    }

    for (int i = 0; i < 3; i++) {
      Block upBlock = block.getRelative(UP_DOWN[i]);
      for (int j = 0; j < 4; j++) {
        Block tmpBlock = upBlock.getRelative(FACES[j]);
        if (RAIL_TYPES.contains(tmpBlock.getType()))
          tmpBlock.getState().setData(railData[i][j]);
      }
    }

  }

  private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(CreeperRail.class);
  private static final AtomicReference<java.lang.reflect.Field> stateData = new AtomicReference<java.lang.reflect.Field>();

  /*
   * (non-Javadoc)
   *
   * @see com.nitnelave.CreeperHeal.block.CreeperBlock#getAttachingFace()
   */
  @Override
  public BlockFace getAttachingFace() {
    Rails data = this.data;
    if (data.isOnSlope())
      return data.getDirection();
    return BlockFace.DOWN;
  }

  /**
   * Checks if the rail is ascending.
   *
   * @return True, if it is ascending
   */
  public boolean isAscending() {
    return data.isOnSlope();
  }

}
