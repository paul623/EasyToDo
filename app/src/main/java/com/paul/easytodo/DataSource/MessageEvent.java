package com.paul.easytodo.DataSource;

public class MessageEvent {
    boolean flag;

    public MessageEvent(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
