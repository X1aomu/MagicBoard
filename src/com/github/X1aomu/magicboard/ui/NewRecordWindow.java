package com.github.X1aomu.magicboard.ui;

import com.github.X1aomu.magicboard.tool.Controller;
import com.github.X1aomu.magicboard.tool.Record;
import com.github.X1aomu.magicboard.tool.Recorder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 创建新记录窗口类
 */
public class NewRecordWindow extends Stage {
  private final Text durationText; // 本次成绩
  private final Label promptLabel = new Label("请输入您的昵称, 不超过 10 个字符:");
  private final TextField inputField = new TextField();
  private final Button confirmButton = new Button("确认");

  public NewRecordWindow() {
    // 此处生成一个临时的 Record 对象来使用 getDurationString() 方法以获得表示用时的字符串.
    durationText = new Text(String.format("你的成绩为: %s",
        new Record("", Recorder.getDuration(), Controller.getDifficulty())
            .getDurationString()));

    VBox vbox = new VBox();
    vbox.getChildren().addAll(durationText, promptLabel, inputField, confirmButton);
    Scene scene = new Scene(vbox);
    setScene(scene);

    durationText.setFont(new Font(14));
    VBox.setMargin(durationText, new Insets(0, 0, 15, 0));
    VBox.setMargin(promptLabel, new Insets(0, 0, 5, 0));
    VBox.setMargin(confirmButton, new Insets(15, 0, 0, 0));
    vbox.setPadding(new Insets(15, 15, 15, 15));
    vbox.setAlignment(Pos.CENTER);
    initModality(Modality.APPLICATION_MODAL);
    setTitle("记录游戏成绩");
    setResizable(false);

    scene.getStylesheets().add("cn/edu/scau/cmi/wangjiayuan/comprehensive/ui/ui.css");

    inputField.setOnAction(new EventConfirm());
    confirmButton.setOnAction(new EventConfirm());
  }

  private class EventConfirm implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      Recorder.addRecord(new Record(inputField.getText(), Recorder.getDuration(),
          Controller.getDifficulty()));
      Node node = (Node)event.getSource();
      Stage stage = (Stage)node.getScene().getWindow();
      stage.close();
    }
  }
}
