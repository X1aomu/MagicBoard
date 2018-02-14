package com.github.X1aomu.magicboard.tool;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 数据文件相关类. 读入和存储数据文件. 因使用了 Alert 类, 本类中的方法应该在 javafx 的线程中调用.
 * 
 * @author WangJiayuan
 */
public class DataFile {
  private DataFile() {
  }

  private static final String fileName = "records.dat";

  private static final File dataFile = new File(System.getProperty("user.home")
      + File.separator + ".local/MagicBoard" + File.separator + fileName);

  public static void writeOut(Record[] recordList) {
    try {
      System.err.println("数据文件: " + dataFile.getAbsolutePath());

      if (!dataFile.exists()) {
        System.err.println("文件不存在, 创建文件");
        dataFile.getParentFile().mkdirs();
        System.err.println("创建文件成功");
      }
      System.err.println("正在写入文件...");
      PrintWriter output = new PrintWriter(dataFile, "UTF-8");

      for (Record r : recordList) {
        output.printf("%d %f %s\n", r.getDifficulty(), r.getDuration(), r.getName());
      }

      output.close();
      System.err.println("写入文件成功");
    }
    catch (IOException e) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("警告");
      alert.setHeaderText("写入文件发生错误");
      alert.setContentText("保存游戏数据时发生了一点问题, 您的游戏成绩将不会保存到文件.\n您可以查看控制台日志获取更详细的信息.\n");
      alert.showAndWait();

      e.printStackTrace();
    }
  }

  public static Record[] readIn() {
    ArrayList<Record> recordList = new ArrayList<>();

    try {
      System.err.println("数据文件: " + dataFile.getAbsolutePath());
      if (dataFile.exists() && dataFile.isFile()) {
        System.err.println("正在读取文件...");
        Scanner input = new Scanner(dataFile, "UTF-8");

        while (input.hasNextInt()) {
          int difficulty = input.nextInt();
          if (!input.hasNextDouble()) {
            System.err.println("数据文件内容错误, 删除数据文件或许可以解决这个问题.");
            input.close();
            throw (new IOException());
          }
          double time = input.nextDouble();
          String name = input.nextLine().substring(1); // 跳过一个空白字符
          recordList.add(new Record(name, time, difficulty));
        }

        input.close();
        System.err.println("读取文件成功");
      } else {
        System.err.println("没有找到文件, 稍候将创建文件");
      }
    }
    catch (IOException e) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("警告");
      alert.setHeaderText("读取文件发生错误");
      alert.setContentText("读取游戏数据时发生了一点问题, 无法获取记录, 但您可以继续游戏.\n您可以查看控制台日志获取更详细的信息.\n");
      alert.showAndWait();

      e.printStackTrace();
    }

    return recordList.toArray(new Record[recordList.size()]);
  }
}
