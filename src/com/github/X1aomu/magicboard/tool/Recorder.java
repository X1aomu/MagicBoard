package com.github.X1aomu.magicboard.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.github.X1aomu.magicboard.ui.LeaderBoardWindow;
import com.github.X1aomu.magicboard.ui.NewRecordWindow;

/**
 * 记录器类
 *
 * @author WangJiayuan
 *
 */
public class Recorder {
  private Recorder() {}

  /**
   * 操作 Recordable
   */
  private final static int LOWEST_RANKING = 10; // 记录的最低排名

  private static Recordable recorder;
  // 所有难度的记录的映射
  private static Map<Integer, ArrayList<Record>> mapRecordList = new HashMap<Integer, ArrayList<Record>>() {
    private static final long serialVersionUID = 1L;
    {
      // 添加各个难度
      for (Integer i : Controller.getDifficultyList()) {
        put(i, new ArrayList<Record>());
      }
    }
  };

  /**
   * 注册记录器.
   * @param o
   */
  public static void assignRecorder(Recordable o) {
    recorder = o;
  }

  /**
   * 锁定当前记录的时间.
   */
  public static void lock() {
    recorder.lock();
  }

  /**
   * 获得当前游戏的时间.
   * @return
   */
  public static double getDuration() {
    return recorder.getDuration();
  }

  /**
   * 添加一条记录.
   * @param r
   */
  public static void addRecord(Record r) {
    ArrayList<Record> recordList = mapRecordList.get(r.getDifficulty());

    if (recordList != null) { // 忽略掉可能的不存的难度的记录
      recordList.add(r);
      Collections.sort(recordList);
      // 剔除掉最低排名之后的记录
      if (recordList.size() > LOWEST_RANKING) {
        recordList.remove(recordList.size() - 1);
      }
    }
  }

  /**
   * 返回指定难度的记录的数组.
   * @param difficulty
   * @return
   */
  public static Record[] getRecordList(Integer difficulty) {
    ArrayList<Record> recordList = mapRecordList.get(difficulty);
    return recordList.toArray(new Record[recordList.size()]);
  }

  /**
   * 返回包含所有记录的数组.
   * @return
   */
  public static Record[] getRecordListAll() {
    ArrayList<Record> recordListAll = new ArrayList<>();
    for (Integer i : Controller.getDifficultyList()) {
      for (Record record : mapRecordList.get(i)) {
        recordListAll.add(record);
      }
    }
    return recordListAll.toArray(new Record[recordListAll.size()]);
  }

  /**
   * 创建新记录.
   */
  public static void newRecord() {
    NewRecordWindow window = new NewRecordWindow();
    window.showAndWait();
  }

  /**
   * 显示排行榜.
   */
  public static void showLeaderBoard() {
    LeaderBoardWindow window = new LeaderBoardWindow();
    window.showAndWait();
  }

  /**
   * 保存记录到文件.
   */
  public static void writeRecordsToFile() {
    DataFile.writeOut(getRecordListAll());
  }

  /**
   * 从文件读入记录.
   */
  public static void readRecordsFromFile() {
    Record[] recordsAll = DataFile.readIn();
    for (Record r : recordsAll) {
      addRecord(r);
    }
  }
}
