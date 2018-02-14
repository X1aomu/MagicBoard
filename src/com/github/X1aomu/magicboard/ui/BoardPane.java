package com.github.X1aomu.magicboard.ui;

import java.util.Arrays;
import java.util.Random;

import com.github.X1aomu.magicboard.tool.Controllable;
import com.github.X1aomu.magicboard.tool.Controller;
import com.github.X1aomu.magicboard.tool.DifficultyAdjustable;
import com.github.X1aomu.magicboard.tool.Recorder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * 游戏面板类, 显示魔板 每个可移动的方块使用按钮来实现
 * 
 * @author WangJiayuan
 */
public class BoardPane extends GridPane implements Controllable, DifficultyAdjustable {
  private final double BUTTON_SIDE_LENGTH = 60; // 边长

  private int size; // 实际面板的大小, 即游戏难度
  private Button[] buttonList; // 方块列表, 该列表内的方块是有序的, 每个方块代表的数字有序递增
  private Button blankButton; // 空白方块

  public BoardPane(Integer difficulty) {
    setDifficulty(difficulty);
  }

  public BoardPane() {
    this(4);
  }

  /**
   * 点击按钮触发的事件 如果当前按钮可移动, 则移动到空白位置
   * 
   * @author WangJiayuan
   */
  private class EventMove implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      Button source = (Button)event.getSource();

      // 获取被点中方块的坐标
      int sourceIndexOfColumn = GridPane.getColumnIndex(source);
      int sourceIndexOfRow = GridPane.getRowIndex(source);

      // 与空白位置的坐标对比, 横纵坐标的差的绝对值之和为 1 即可移动
      if (Math.abs(GridPane.getColumnIndex(blankButton) - sourceIndexOfColumn)
          + Math.abs(GridPane.getRowIndex(blankButton) - sourceIndexOfRow) == 1) {
        // 交换被点中方块和空白方块
        GridPane.setColumnIndex(source, GridPane.getColumnIndex(blankButton));
        GridPane.setRowIndex(source, GridPane.getRowIndex(blankButton));
        GridPane.setColumnIndex(blankButton, sourceIndexOfColumn);
        GridPane.setRowIndex(blankButton, sourceIndexOfRow);

        if (isOrdered()) {
          Recorder.lock(); // 锁定记时器
          Controller.stopGame(); // 停止游戏
          Recorder.newRecord(); // 创建记录
        }
      }
    }
  }

  /**
   * 判断是否已经有序. 有序是指各方块在面板上的位置从左到右从上到下的顺序与它们在 buttonList 中的的顺序一致. 如果有序,
   * 则代表游戏成功(完成).
   * 
   * @return
   */
  private boolean isOrdered() {
    for (Button b : buttonList) {
      if (GridPane.getRowIndex(b) * size + GridPane.getColumnIndex(b) != Arrays
          .asList(buttonList).indexOf(b)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 生成随机数组, 以用来打乱方块的顺序. 返回的数组 array 的意思为放置在位置 i 上的方块的序号为 array[i]. 本算法的时间复杂度为
   * O(n).
   * 
   * @return
   */
  private int[] genRandomArray() {
    // 初始化
    int[] array = new int[size * size - 1];
    for (int i = 0; i != array.length; ++i) {
      array[i] = i;
    }
    // 打乱
    Random random = new Random();
    for (int times = 0; times != array.length; ++times) {
      int indexB = array.length - times - 1;
      int indexA = random.nextInt(array.length - times);
      if (indexA != indexB) {
        int temp = array[indexA];
        array[indexA] = array[indexB];
        array[indexB] = temp;
      }
    }
    return array;
  }

  /**
   * 判断生成的随机数组是否有效. 当且仅当数组中有偶数个逆序数对的时候有效. 时间复杂度 O(n^2).
   * 
   * @param array
   * @return
   */
  private boolean isRandomArrayValid(int[] array) {
    int count = 0;
    for (int indexA = 0; indexA < array.length - 1; ++indexA) {
      for (int indexB = indexA + 1; indexB < array.length; ++indexB) {
        if (array[indexA] > array[indexB]) {
          ++count;
        }
      }
    }
    if (count % 2 == 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 开始, 打乱方块, 使所有数字方块可以点击.
   */
  @Override
  public void start() {
    int[] randomArray = genRandomArray();
    while (isRandomArrayValid(randomArray) != true) {
      randomArray = genRandomArray();
    }

    getChildren().clear();
    for (int index = 0; index != randomArray.length; ++index) {
      add(buttonList[randomArray[index]], index % size, index / size);
    }
    add(blankButton, size - 1, size - 1);

    for (Button b : buttonList) {
      b.setDisable(false);
    }
  }

  /**
   * 结束, 重置所有方块, 且所有数字方块不可点击.
   */
  @Override
  public void stop() {
    getChildren().clear();
    for (Button b : buttonList) {
      add(b, Arrays.asList(buttonList).indexOf(b) % size,
          Arrays.asList(buttonList).indexOf(b) / size);
      b.setDisable(true);
      b.setText(Integer.toString(Arrays.asList(buttonList).indexOf(b) + 1));

    }
    add(blankButton, size - 1, size - 1);
  }

  /**
   * 暂停, 所有数字方块不可点击, 并隐藏方块显示的数字.
   */
  @Override
  public void pause() {
    for (Button b : buttonList) {
      b.setDisable(true);
      b.setText("");
    }
  }

  /**
   * 继续, 所有数字方块可点击, 恢复方块显示的数字.
   */
  @Override
  public void resume() {
    for (Button b : buttonList) {
      b.setDisable(false);
      b.setText(Integer.toString(Arrays.asList(buttonList).indexOf(b) + 1));
    }
  }

  /**
   * 设置游戏难度. 将初始化方块列表 buttonList 和所有方块
   */
  @Override
  public void setDifficulty(Integer difficulty) {
    this.size = difficulty;
    buttonList = new Button[size * size - 1];
    blankButton = new Button();

    for (int i = 0; i < buttonList.length; ++i) {
      buttonList[i] = new Button();
    }

    for (Button b : buttonList) {
      b.setOnAction(new EventMove());
    }

    for (Button b : buttonList) {
      b.setFont(new Font(20));
      b.setMinHeight(BUTTON_SIDE_LENGTH);
      b.setMinWidth(BUTTON_SIDE_LENGTH);
      b.setAlignment(Pos.CENTER);
      b.getStyleClass().add("number-button");
    }

    setAlignment(Pos.CENTER);
    blankButton.setMinHeight(BUTTON_SIDE_LENGTH);
    blankButton.setMinWidth(BUTTON_SIDE_LENGTH);
    blankButton.setDisable(true); // 空白方块始终不可点击

    blankButton.getStyleClass().add("number-button");
    getStyleClass().add("board-pane");

    stop();

    // 重置窗口大小
    // 只有当本节点已经在 stage 中才有用
    if (getScene() != null && getScene().getWindow() != null) {
      Stage stage = (Stage)getScene().getWindow();
      stage.sizeToScene();
      stage.setResizable(false);
    }
  }

  @Override
  public Integer getDifficulty() {
    return size;
  }

  @Override
  public Integer[] getDifficultyList() {
    return Arrays.copyOf(difficultys, difficultys.length);
  }
}
