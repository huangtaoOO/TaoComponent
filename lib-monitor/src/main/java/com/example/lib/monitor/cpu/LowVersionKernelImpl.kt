package com.example.lib.monitor.cpu

import android.util.Log
import com.example.lib.monitor.model.CPUTime
import com.example.lib.monitor.utils.toLongDefault0
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.RandomAccessFile

/**
 * Author: huangtao
 * Date: 2023/6/13
 * Desc: 低版本内核Cpu使用率统计实现
 */
class LowVersionKernelImpl : CpuUsageRate() {

    override suspend fun obtainCPUWrap(): Pair<Float, Float> {
        return withContext(Dispatchers.IO) {
            val result = kotlin.runCatching {
                if (currentFailTimes >= failRetryNum) {
                    Log.i(TAG, "current number of consecutive failures = $currentFailTimes")
                    Pair(-1.0F, -1.0F)
                } else {
                    val lastCpuTime = obtainCPUTime()
                    delay(CPU_INTERVAL)
                    val newCpuTime = obtainCPUTime()
                    obtainCPUOccupancyRate(lastCpuTime, newCpuTime).apply {
                        currentFailTimes = 0
                    }
                }
            }.onFailure {
                currentFailTimes++
                Log.i(TAG, "obtainCPUWrap error message : ${it.message}")
            }
            result.getOrDefault(Pair(-1.0F, -1.0F))
        }
    }

    private fun obtainCPUTime(): CPUTime {
        val reader = RandomAccessFile(TOTAL_STAT, "r")
        val load = reader.readLine()
        reader.close()
        val toks = load.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val cpuTime =
            (toks[1].toLongDefault0() + toks[2].toLongDefault0()
                    + toks[3].toLongDefault0() + toks[4].toLongDefault0()
                    + toks[5].toLongDefault0() + toks[6].toLongDefault0()
                    + toks[7].toLongDefault0()) * jiffies
        val idleTime = toks[4].toLongDefault0() * jiffies
        val myCpuTime = readMyPidCpuTimeFile()
        return CPUTime(cpuTime, idleTime, myCpuTime)
    }

}