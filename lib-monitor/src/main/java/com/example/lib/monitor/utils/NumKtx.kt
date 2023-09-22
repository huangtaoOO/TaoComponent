package com.example.lib.monitor.utils

fun String.toLongDefault0() =
    kotlin.runCatching {
        this.toLongOrNull()
    }.getOrNull() ?: 0L