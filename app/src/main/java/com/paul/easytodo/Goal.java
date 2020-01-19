package com.paul.easytodo;

import com.yalantis.beamazingtoday.interfaces.BatModel;

import org.litepal.crud.LitePalSupport;

public class Goal extends LitePalSupport implements BatModel{
    private String name;

    private boolean isChecked;

    public Goal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public String getText() {
        return getName();
    }
}
