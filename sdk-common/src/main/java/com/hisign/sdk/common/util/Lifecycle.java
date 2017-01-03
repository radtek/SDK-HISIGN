package com.hisign.sdk.common.util;

/**
 * 生命周期定义
 * @author lnj2050
 *
 */
public abstract interface Lifecycle{
  public abstract void init();

  public abstract void start();

  public abstract void stop();

  public abstract void close();
}