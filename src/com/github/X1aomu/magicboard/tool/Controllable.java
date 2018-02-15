package com.github.X1aomu.magicboard.tool;

/**
 * 可控制接口. 可接收游戏控制事件以便做出相应动作.
 */
public interface Controllable {

  void start();

  void stop();

  void pause();

  void resume();

}
