package com.github.X1aomu.magicboard.ui;

import com.github.X1aomu.magicboard.tool.Controllable;
import com.github.X1aomu.magicboard.tool.Controller;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * 控制面板类
 *
 * @author WangJiayuan
 */
public class ControlPane extends HBox implements Controllable {
  private final Button left;
  private final Button right;

  public ControlPane() {
    left = new Button();
    right = new Button();

    getChildren().add(left);
    getChildren().add(right);

    right.setOnAction(e -> {
      Controller.stopGame();
    });

    setAlignment(Pos.CENTER);
    setSpacing(30);
    left.setFont(new Font(14));
    left.setAlignment(Pos.CENTER);
    left.setMinHeight(45);
    left.setMinWidth(70);
    right.setFont(new Font(14));
    right.setText("停止");
    right.setAlignment(Pos.CENTER);
    right.setMinHeight(45);
    right.setMinWidth(70);

    left.getStyleClass().add("control-button");
    left.setId("left-button");
    right.getStyleClass().add("control-button");
    right.setId("right-button");

    stop();
  }

  @Override
  public void start() {
    right.setDisable(false);
    left.setText("暂停");
    left.setOnAction(e -> {
      Controller.pauseGame();
    });
  }

  @Override
  public void stop() {
    right.setDisable(true);
    left.setText("开始");
    left.setOnAction(e -> {
      Controller.startGame();
    });
  }

  @Override
  public void pause() {
    left.setText("继续");
    left.setOnAction(e -> {
      Controller.resumeGame();
    });
  }

  @Override
  public void resume() {
    left.setText("暂停");
    left.setOnAction(e -> {
      Controller.pauseGame();
    });
  }
}
