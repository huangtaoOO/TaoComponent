package com.example.base.di

import javax.inject.Qualifier

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc:
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Remote

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Local