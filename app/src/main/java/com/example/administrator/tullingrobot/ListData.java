package com.example.administrator.tullingrobot;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class ListData {
    private String content;
    public static final int SEND=1;
    public static final int RECEIVER=2;
    private int flag;
    public ListData(String content,int flag){
        setContent(content);
        setFlag(flag);
    }
    public void setContent(String content) {
        this.content = content;
    }



    public String getContent() {
        return content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
