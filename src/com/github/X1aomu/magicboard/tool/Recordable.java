package com.github.X1aomu.magicboard.tool;

/**
 * 可记录接口
 *
 * @author WangJiayuan
 *
 */
public interface Recordable {
  
  /**
   * 锁定记录.
   */
  void lock();

  /**
   * 返回记录的时间.
   * @return
   */
  double getDuration();

}
