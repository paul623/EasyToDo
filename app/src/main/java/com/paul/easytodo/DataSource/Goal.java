package com.paul.easytodo.DataSource;

import com.paul.easytodo.Utils.DateUtil;
import com.yalantis.beamazingtoday.interfaces.BatModel;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Goal extends LitePalSupport implements BatModel{
    private String name;

    private boolean isChecked;

    private String setDate;

    private String finshDate;



    public String getSetDate() {
        return setDate;
    }

    public void setSetDate(String setDate) {
        this.setDate = setDate;
    }

    public String getFinshDate() {
        return finshDate;
    }

    public void setFinshDate(String finshDate) {
        this.finshDate = finshDate;
    }

    public Goal(String name) {
        this.name = name;
    }
    public Goal(){
        setDate= DateUtil.getCurDate();
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
