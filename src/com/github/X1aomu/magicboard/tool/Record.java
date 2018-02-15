package com.github.X1aomu.magicboard.tool;

/**
 * 成绩记录类, 表示单条记录
 */
public class Record implements Comparable<Record> {
  private final String name; // 昵称
  private final double duration; // 用时
  private final Integer difficulty; // 难度

  public Record(String name, double duration, int difficulty) {
    // 最大长度为 10
    if (name.length() > 10) {
      name = name.substring(0, 10);
    } else if (name.length() == 0) {
      name = "*匿名玩家*";
    }
    this.name = name;
    this.duration = duration;
    this.difficulty = difficulty;
  }

  public String getName() {
    return name;
  }

  public double getDuration() {
    return duration;
  }

  public Integer getDifficulty() {
    return difficulty;
  }

  /**
   * 返回一个表示用时的字符串, 格式为 HH:mm:ss.
   * 
   * @return
   */
  public String getDurationString() {
    int s = (int)duration;
    return String.format(String.format("%02d:%02d:%02d.%02d", s / 3600, (s / 60) % 60,
        s % 60, ((int)(duration * 100)) % 100));
  }

  @Override
  public int compareTo(Record o) {
    if (difficulty < o.difficulty) {
      return -1;
    } else if (difficulty > o.difficulty) {
      return 1;
    } else {
      if (duration < o.duration) {
        return -1;
      } else if (duration > o.duration) {
        return 1;
      } else {
        return name.compareTo(o.name);
      }
    }
  }
}
