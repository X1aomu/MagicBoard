package com.github.X1aomu.magicboard.tool;

import java.util.HashSet;

/**
 * 控制器类.
 */
public class Controller {
  private Controller() {
  }

  /**
   * 操作 Controllable. 向各可控制组件发送控制指令, 达到控制游戏进程的目的. 具体实现由各组件通过实现 Controllable 来完成.
   */
  private static HashSet<Controllable> controlledItemSet = new HashSet<>();

  public static void addControlledItem(Controllable controlledItem) {
    controlledItemSet.add(controlledItem);
  }

  public static void deleteControlledItem(Controllable controlledItem) {
    controlledItemSet.remove(controlledItem);
  }

  public static void startGame() {
    for (Controllable c : controlledItemSet) {
      c.start();
    }
  }

  public static void pauseGame() {
    for (Controllable c : controlledItemSet) {
      c.pause();
    }
  }

  public static void resumeGame() {
    for (Controllable c : controlledItemSet) {
      c.resume();
    }
  }

  public static void stopGame() {
    for (Controllable c : controlledItemSet) {
      c.stop();
    }
  }

  /**
   * 操作 DifficultyAdjustable. 控制游戏的难度.
   */
  private static DifficultyAdjustable difficultyChanger;

  public static void assignDifficultyChanger(DifficultyAdjustable d) {
    difficultyChanger = d;
  }

  public static void setDifficulty(Integer d) {
    difficultyChanger.setDifficulty(d);
  }

  public static Integer getDifficulty() {
    return difficultyChanger.getDifficulty();
  }

  public static Integer[] getDifficultyList() {
    return difficultyChanger.getDifficultyList();
  }
}
