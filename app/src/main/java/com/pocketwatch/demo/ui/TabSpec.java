package com.pocketwatch.demo.ui;

/**
 * Created by bmalavazi on 12/27/14.
 */
public class TabSpec {
    private String tabSpecTag;
    private int tabSpecResId;
    private Class<?> tabSpecClass;

    public TabSpec(String tabTag, int tabResId, Class<?> tabClass) {
        this.tabSpecTag = tabTag;
        this.tabSpecResId = tabResId;
        this.tabSpecClass = tabClass;
    }

    public String getTabTag() { return tabSpecTag; }
    public int getTabIndicator() { return tabSpecResId; }
    public Class<?> getTabClass() { return tabSpecClass; }
}
