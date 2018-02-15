package com.github.X1aomu.magicboard;

import com.github.X1aomu.magicboard.tool.Controller;
import com.github.X1aomu.magicboard.tool.Recorder;
import com.github.X1aomu.magicboard.ui.BoardPane;
import com.github.X1aomu.magicboard.ui.ControlPane;
import com.github.X1aomu.magicboard.ui.MyMenuBar;
import com.github.X1aomu.magicboard.ui.TimerLabel;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 主类
 */
public class MainAPP extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    // 初始化各界面组件
    MyMenuBar menuBar = new MyMenuBar(); // 菜单栏
    TimerLabel timer = new TimerLabel(); // 计时器
    BoardPane board = new BoardPane(); // 游戏面板(魔板)
    ControlPane controllPane = new ControlPane(); // 控制面板
    VBox mainVBox = new VBox(); // 主布局, 含菜单栏和次布局

    mainVBox.getChildren().add(timer);
    mainVBox.getChildren().add(board);
    mainVBox.getChildren().add(controllPane);
    VBox vBox = new VBox(); // 次布局, 含除菜单栏外的其他组件
    vBox.getChildren().add(menuBar);
    vBox.getChildren().add(mainVBox);
    Scene scene = new Scene(vBox);

    mainVBox.setPadding(new Insets(15, 15, 15, 15));
    mainVBox.setSpacing(15);
    mainVBox.setAlignment(Pos.TOP_CENTER);
    vBox.setAlignment(Pos.TOP_CENTER);

    scene.getStylesheets().add("com/github/X1aomu/magicboard/ui/ui.css");

    primaryStage.setTitle("魔板游戏");
    primaryStage.setScene(scene);
    primaryStage.sizeToScene();
    primaryStage.setResizable(false);
    primaryStage.show();

    // 添加可以接受控制事件的对象
    Controller.addControlledItem(menuBar);
    Controller.addControlledItem(timer);
    Controller.addControlledItem(board);
    Controller.addControlledItem(controllPane);

    Controller.assignDifficultyChanger(board); // 注册难度改变者

    Recorder.assignRecorder(timer); // 注册记录器

    Recorder.readRecordsFromFile(); // 从文件读入数据
  }

  @Override
  public void stop() {
    Recorder.writeRecordsToFile(); // 保存数据到文件
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
}
