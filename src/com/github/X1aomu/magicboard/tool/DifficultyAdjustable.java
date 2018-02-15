package com.github.X1aomu.magicboard.tool;

/**
 * 可调整难度接口
 */
public interface DifficultyAdjustable {

  Integer[] difficultys = { 3, 4, 5 };

  void setDifficulty(Integer difficulty);

  Integer getDifficulty();

  Integer[] getDifficultyList();

}
