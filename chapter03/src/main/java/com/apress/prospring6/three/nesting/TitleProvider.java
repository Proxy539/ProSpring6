package com.apress.prospring6.three.nesting;

public class TitleProvider {

    private String title = "Gravity";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // bulder method
    public static TitleProvider instance(final String title) {
        var childProvider = new TitleProvider();
        if (title != null) {
            childProvider.setTitle(title);
        }
        return childProvider;
    }

}
