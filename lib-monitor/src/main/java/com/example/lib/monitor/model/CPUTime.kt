package com.example.lib.monitor.model

/**
 * Author: huangtao
 * Date: 2023/9/22
 * Desc: CPU计算所需的数据 单位=毫秒
 */
data class CPUTime(
    val cpuTime: Long = 0L,
    val idleTime: Long = 0L,
    val myCpuTime: Long = 0L,
)