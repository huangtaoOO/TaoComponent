package com.example.lib.monitor.cpu

import android.util.Log
import android.util.SparseArray
import com.example.lib.monitor.model.CPUGroup
import com.example.lib.monitor.model.CPUTime
import com.example.lib.monitor.utils.toLongDefault0
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Author: huangtao
 * Date: 2023/6/13
 * Desc: 高版本内核Cpu使用率统计实现
 */
class HighVersionKernelImpl(
    private val groups: List<CPUGroup>,
    private val cpuCoreCurFreq: (coreIdx: Int) -> String,
) :
    CpuUsageRate() {

    private fun cpuTime(coreIdx: Int) =
        "/sys/devices/system/cpu/cpufreq/policy$coreIdx/stats/time_in_state"

    private fun idleTime(path: String) =
        "$path/time"

    private fun idleTimeCatalogue(coreIdx: Int) =
        "/sys/devices/system/cpu/cpu$coreIdx/cpuidle/"

    override suspend fun obtainCPUWrap(): Pair<Float, Float> {
        return withContext(Dispatchers.IO) {
            val result = kotlin.runCatching {
                if (currentFailTimes >= failRetryNum) {
                    //失败超过三次了，判断为后续都无法成功为避免损耗性能，返回-1
                    Log.i(TAG, "current number of consecutive failures = $currentFailTimes")
                    Pair(-1.0F, -1.0F)
                } else {
                    //用来存每个核心第一次的时间，用来校准。
                    //比如：一秒的时间里这个核心都在跑或者都在空闲的情况
                    val sparseArray = SparseArray<Long>()
                    val lastCpuTime = obtainCPUTime(sparseArray)
                    delay(CPU_INTERVAL)
                    val newCpuTime = obtainCPUTime(sparseArray)
                    obtainCPUOccupancyRate(lastCpuTime, newCpuTime).also {
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

    private fun obtainCPUTime(lastData: SparseArray<Long>): CPUTime {
        var cpuTime = 0L
        for (group in groups) {
            val core = group.cores.firstOrNull() ?: continue
            cpuTime += readCPUTimeFile(cpuTime(core), group.cores.size)
        }
        val idleTime = readIdleTime(lastData)
        val myCpuTime = readMyPidCpuTimeFile()
        return CPUTime(cpuTime, idleTime, myCpuTime)
    }

    private fun readCPUTimeFile(path: String, coreSize: Int): Long {
        val lines = File(path).readLines()
        var time = 0L
        for (line in lines) {
            //读取的运行时间单位为 10ms
            time += (line.split(" ")[1].toLongDefault0() * coreSize)
        }
        //统一转化为毫秒单位
        return time * jiffies
    }

    private fun readIdleTimeFile(coreIdx: Int): Long {
        var time = 0L
        val listFiles: Array<File>? = File(idleTimeCatalogue(coreIdx)).listFiles()
        if (!listFiles.isNullOrEmpty()) {
            for (file in listFiles) {
                //读取空闲时间的单位为微秒
                if (file.name.contains("state")){
                    val line = File((idleTime(file.absolutePath))).readLines().firstOrNull() ?: continue
                    time += line.toLongDefault0()
                }
            }
        }
        return time
    }

    private fun readIdleTime(lastData: SparseArray<Long>): Long {
        val oneMs = 1000L
        // 返回的 采样周期内的 idle时间
        var totalIdleDeltaTime = 0L
        for (group in groups) {
            for (core in group.cores) {
                //获取cpu当前最新的idle时间
                val nowIdleTime = readIdleTimeFile(core)
                val lastIdleTime = lastData[core]
                if (lastIdleTime == null) {
                    lastData[core] = nowIdleTime
                    totalIdleDeltaTime += nowIdleTime
                    continue
                }
                val deltaIdleTime = (nowIdleTime - lastIdleTime)
                totalIdleDeltaTime += (lastIdleTime + deltaIdleTime)
            }
        }
        return totalIdleDeltaTime / oneMs
    }

    private fun readFileData(path: String): String? {
        try {
            val file = File(path)
            if (!file.exists()) {
                return null
            }
            return file.readLines().first()
        } catch (tr: Throwable) {
            Log.w(TAG, "Read file $path error: ${tr.message}")
            return null
        }
    }
}