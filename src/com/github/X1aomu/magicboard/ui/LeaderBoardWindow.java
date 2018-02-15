package com.github.X1aomu.magicboard.ui;

import java.util.Arrays;

import com.github.X1aomu.magicboard.tool.Controller;
import com.github.X1aomu.magicboard.tool.Record;
import com.github.X1aomu.magicboard.tool.Recorder;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 显示排行榜窗口类
 */
public class LeaderBoardWindow extends Stage {
  private final Integer[] difficultyList = Controller.getDifficultyList();
  private final Label titleLabel = new Label("游戏成绩排行榜");
  private final Label noRecordLabel = new Label("这里空空如也, 快来创建记录吧!");
  private final Record[][] recordLists = new Record[difficultyList.length][];
  private final RadioButton[] difficultyButtons = new RadioButton[difficultyList.length];
  private final ToggleGroup difficultyGroup = new ToggleGroup();
  private final GridPane[] tables = new GridPane[difficultyList.length];
  private final Button button = new Button("关闭");
  private final Font titleFont = new Font(16);
  private final Font difficultyFont = new Font(14);
  private final HBox hbox = new HBox();
  private final VBox layout = new VBox();

  public LeaderBoardWindow() {
    // 初始化组件
    titleLabel.setFont(titleFont);
    noRecordLabel.setPadding(new Insets(5));
    for (Integer d : difficultyList) {
      int index = Arrays.asList(difficultyList).indexOf(d);
      Record[] list = recordLists[index] = Recorder.getRecordList(d);
      difficultyButtons[index] = new RadioButton(String.format("%d x %d", d, d));
      difficultyButtons[index].setFont(difficultyFont);
      difficultyButtons[index].setPadding(new Insets(15, 0, 0, 0));
      difficultyButtons[index].setToggleGroup(difficultyGroup);
      difficultyButtons[index].setOnAction(e -> {
        selectDifficulty(d);
      });

      tables[index] = new GridPane();
      for (Record r : list) {
        Label ranking = new Label(Integer.toString(Arrays.asList(list).indexOf(r) + 1));
        Label name = new Label(r.getName());
        Label time = new Label(r.getDurationString());

        tables[index].addRow(Arrays.asList(list).indexOf(r), ranking, name, time);

        tables[index].setAlignment(Pos.CENTER);
        tables[index].setHgap(10);
        tables[index].setVgap(5);
        tables[index].setPadding(new Insets(5));
        ranking.setAlignment(Pos.CENTER_RIGHT);
        ranking.setMinWidth(16);
        name.setAlignment(Pos.CENTER_LEFT);
        name.setMinWidth(130);
        time.setAlignment(Pos.CENTER);
      }
    }
    for (ToggleButton level : difficultyButtons) {
      level.setPadding(new Insets(2, 5, 2, 5));
      level.setAlignment(Pos.CENTER);
      hbox.getChildren().add(level);
    }
    hbox.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    setScene(scene);

    VBox.setMargin(button, new Insets(15, 0, 0, 0));
    VBox.setMargin(hbox, new Insets(15, 20, 0, 20));
    layout.setPadding(new Insets(15));
    layout.setAlignment(Pos.CENTER);
    initModality(Modality.APPLICATION_MODAL);
    setTitle("游戏成绩排行榜");

    scene.getStylesheets().add("com/github/X1aomu/magicboard/ui/ui.css");

    button.setOnAction(e -> {
      Button b = (Button)e.getSource();
      Stage s = (Stage)b.getScene().getWindow();
      s.close();
    });

    // 设置默认状态
    difficultyButtons[Arrays.asList(difficultyList).indexOf(Controller.getDifficulty())]
        .setSelected(true);
    selectDifficulty(Controller.getDifficulty());
  }

  /**
   * 选择显示的难度.
   * @param difficulty
   */
  private void selectDifficulty(Integer difficulty) {
    int index = Arrays.asList(difficultyList).indexOf(difficulty);
    Record[] list = recordLists[index] = Recorder.getRecordList(difficulty);

    // 添加组件
    layout.getChildren().clear();
    layout.getChildren().add(titleLabel);
    layout.getChildren().add(hbox);
    if (list.length == 0) {
      layout.getChildren().add(noRecordLabel);
    } else {
      layout.getChildren().add(tables[index]);
    }

    layout.getChildren().add(button);
    sizeToScene();
    setResizable(false);
  }
}
