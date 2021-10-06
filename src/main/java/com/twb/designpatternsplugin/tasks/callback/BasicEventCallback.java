package com.twb.designpatternsplugin.tasks.callback;

public interface BasicEventCallback {
    void onSuccess();
    void onFailure(Throwable throwable);
}
