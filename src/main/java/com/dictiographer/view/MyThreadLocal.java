package com.dictiographer.view;

/**
 * User: Vitaly Sazanovich
 * Date: 1/13/13
 * Time: 9:22 PM
 */
public class MyThreadLocal {
    public static final ThreadLocal userThreadLocal = new ThreadLocal();

    public static void set(ThreadContext user) {
        userThreadLocal.set(user);
    }

    public static void unset() {
        userThreadLocal.remove();
    }

    public static ThreadContext get() {
        return (ThreadContext) userThreadLocal.get();
    }
}
