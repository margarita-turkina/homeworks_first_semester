package com.mipt.margaritaturkina;

public abstract class Worker {
  public abstract void work(int input);
  public boolean goHome(String homeAddress, String currentLocation) {
    return homeAddress.equals(currentLocation);
  }
}
