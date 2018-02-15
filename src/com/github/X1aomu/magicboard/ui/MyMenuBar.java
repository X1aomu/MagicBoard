package com.github.X1aomu.magicboard.ui;

import com.github.X1aomu.magicboard.tool.Controllable;
import com.github.X1aomu.magicboard.tool.Controller;
import com.github.X1aomu.magicboard.tool.Recorder;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCombination;

/**
 * 菜单类, 显示游戏菜单
 */
public class MyMenuBar extends MenuBar implements Controllable {
  private final Menu menuGame = new Menu("游戏");
  private final Menu menuSetting = new Menu("设置");
  private final Menu menuDifficulty = new Menu("难度");

  private final MenuItem start = new MenuItem("开始");
  private final MenuItem stop = new MenuItem("停止");
  private final MenuItem pause = new MenuItem("暂停");
  private final MenuItem resume = new MenuItem("继续");
  private final MenuItem leaderboard = new MenuItem("排行榜");
  private final MenuItem exit = new MenuItem("退出");

  private final RadioMenuItem difficultyThree = new RadioMenuItem("3 x 3");
  private final RadioMenuItem difficultyFour = new RadioMenuItem("4 x 4");
  private final RadioMenuItem difficultyFive = new RadioMenuItem("5 x 5");

  private final ToggleGroup groupDifficulty = new ToggleGroup();

  public MyMenuBar() {
    start.setAccelerator(KeyCombination.keyCombination("Ctrl + N"));
    start.setOnAction(e -> {
      Controller.startGame();
    });

    pause.setAccelerator(KeyCombination.keyCombination("Ctrl + P"));
    pause.setOnAction(e -> {
      Controller.pauseGame();
    });

    resume.setAccelerator(KeyCombination.keyCombination("Ctrl + R"));
    resume.setOnAction(e -> {
      Controller.resumeGame();
    });

    stop.setAccelerator(KeyCombination.keyCombination("Ctrl + S"));
    stop.setOnAction(e -> {
      Controller.stopGame();
    });

    leaderboard.setAccelerator(KeyCombination.keyCombination("Ctrl + L"));
    leaderboard.setOnAction(e -> {
      if (pause.isDisable() == false) {
        Controller.pauseGame();
      }
      Recorder.showLeaderBoard();
    });

    exit.setAccelerator(KeyCombination.keyCombination("Ctrl + Q"));
    exit.setOnAction(e -> {
      Platform.exit();
    });

    menuGame.getItems().addAll(start, pause, resume, stop, new SeparatorMenuItem(),
        leaderboard, new SeparatorMenuItem(), exit);

    difficultyThree.setToggleGroup(groupDifficulty);
    difficultyThree.setOnAction(e -> {
      Controller.setDifficulty(3);
    });

    difficultyFour.setToggleGroup(groupDifficulty);
    difficultyFour.setOnAction(e -> {
      Controller.setDifficulty(4);
    });

    difficultyFive.setToggleGroup(groupDifficulty);
    difficultyFive.setOnAction(e -> {
      Controller.setDifficulty(5);
    });

    difficultyFour.setSelected(true); // 默认选中

    menuDifficulty.getItems().addAll(difficultyThree, difficultyFour, difficultyFive);

    menuSetting.getItems().add(menuDifficulty);

    getMenus().add(menuGame);
    getMenus().add(menuSetting);

    stop();
  }

  @Override
  public void start() {
    start.setDisable(true);
    pause.setDisable(false);
    resume.setDisable(true);
    stop.setDisable(false);
    menuDifficulty.setDisable(true);
  }

  @Override
  public void stop() {
    start.setDisable(false);
    pause.setDisable(true);
    resume.setDisable(true);
    stop.setDisable(true);
    menuDifficulty.setDisable(false);
  }

  @Override
  public void pause() {
    pause.setDisable(true);
    resume.setDisable(false);
  }

  @Override
  public void resume() {
    pause.setDisable(false);
    resume.setDisable(true);
  }

}
