package com.team254.frc2014;

import com.team254.frc2014.actions.DriveAction;
import com.team254.frc2014.actions.WaitAction;

public abstract class AutoMode implements Runnable {

  Action currentAction = null;
  Thread autoThread = new Thread(this);
  private boolean alive = true;

  protected abstract void routine();

  public void run() {
    System.out.println("Starting auto mode!");
    routine();
    System.out.println("Ending auto mode!");
  }

  public void start() {
    stop();
    alive = true;
    autoThread = new Thread(this);
    autoThread.start();
  }

  public void stop() {
    stopCurrentAction();
    alive = false;
  }

  public void stopCurrentAction() {
    if (currentAction != null) {
      currentAction.kill();
    }
  }

  public void startCurrentAction() {
    if (currentAction != null) {
      currentAction.run();
    }
  }

  public void runAction(Action action) {
    if (alive) {
      stopCurrentAction();
      currentAction = action;
      startCurrentAction();
    }
  }

  public void drive(double feet, double timeout) {
    runAction(new DriveAction(feet, timeout));
  }

  public void waitTime(double seconds) {
    runAction(new WaitAction(seconds));
  }
}