package com.paul.easytodo.DataSource;

import org.litepal.crud.LitePalSupport;

public class WordsBean extends LitePalSupport {
    String words;
    String add_date;

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getAdd_date() {
        return add_date;
    }

    public void setAdd_date(String add_date) {
        this.add_date = add_date;
    }
}
