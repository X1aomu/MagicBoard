package com.github.X1aomu.magicboard.ui;

import java.time.Duration;
import java.time.Instant;

import com.github.X1aomu.magicboard.tool.Controllable;
import com.github.X1aomu.magicboard.tool.Recordable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * 计时器类, 显示游戏时间
 * 该类使用 java.time 包来实现与时间相关的操作
 * 
 * @author WangJiayuan
 */
public class TimerLabel extends Label implements Controllable, Recordable {
  private final double NANO_PER_SECOND = 1000000000;

  private final Timeline animation; // 更新时间的动画

  private Instant  lastStartTime;
  private double   lastDurationSeconds;
  private double   durationSeconds;
  private double   lockedRecord;

  public TimerLabel() {
    setFont(new Font(18));
    setAlignment(Pos.CENTER);

    // 初始化动画并添加计时器, 以一定帧率更新标签内容
    EventHandler<ActionEvent> eventhander = e -> {
      update();
    };
    animation = new Timeline(new KeyFrame(javafx.util.Duration.millis(100), eventhander));
    animation.setCycleCount(Timeline.INDEFINITE);

    stop();
  }

  /**
   * 更新标签内容到最新的时长
   */
  private void update() {
    // 上次开始后的用时
    Duration d = Duration.between(lastStartTime, Instant.now());
    // 总用时
    // 使用纳秒来提高精度
    durationSeconds = lastDurationSeconds + d.getSeconds()
        + d.getNano() / NANO_PER_SECOND;
    int s = (int)durationSeconds;
    // 更新标签内容
    setText(String.format("%02d:%02d:%02d", s / 3600, (s / 60) % 60, s % 60));
  }

  @Override
  public void start() {
    stop();
    resume();
  }

  @Override
  public void stop() {
    lastDurationSeconds = 0.0;
    durationSeconds = 0.0;
    lastStartTime = Instant.now();
    update();
    animation.stop();
    setText("请开始游戏");
  }

  @Override
  public void pause() {
    Duration d = Duration.between(lastStartTime, Instant.now());
    lastDurationSeconds += d.getSeconds() + d.getNano() / NANO_PER_SECOND;
    animation.pause();
    setText("游戏已暂停");
  }

  @Override
  public void resume() {
    lastStartTime = Instant.now();
    animation.play();
  }

  @Override
  public double getDuration() {
    return lockedRecord;
  }

  @Override
  public void lock() {
    update();
    lockedRecord = durationSeconds;
  }
}
