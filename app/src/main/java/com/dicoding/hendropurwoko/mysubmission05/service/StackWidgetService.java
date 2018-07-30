package com.dicoding.hendropurwoko.mysubmission05.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.dicoding.hendropurwoko.mysubmission05.factory.StackRemoteViewsFactory;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
