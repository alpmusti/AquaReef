package com.cantekin.aquareef.injectors;

import android.app.Application;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by clevo on 2015/6/9.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Application getApplication();
}
