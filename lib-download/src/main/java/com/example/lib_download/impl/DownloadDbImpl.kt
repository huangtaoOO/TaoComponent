package com.example.lib_download.impl

import android.content.ContentValues
import android.util.Log
import com.example.lib_download.DownloadConfig
import com.example.lib_download.core.DownloadStatus
import com.example.lib_download.SubDownloadTask
import com.example.lib_download.core.DownloadDbHelper
import com.example.lib_download.db.DownloadDbOpenHelper
import com.example.lib_download.model.SubDownloadModel
import java.io.File

/**
 * Author: huangtao
 * Date: 2022/12/27
 * Desc: 下载的db存储 默认实现
 */
class DownloadDbImpl : DownloadDbHelper {

    private val db by lazy {
        DownloadDbOpenHelper(DownloadConfig.context)
    }

    override fun delete(task: SubDownloadTask) {
        try {
            val writeDb = db.writableDatabase
            val selection =
                "${DownloadDbOpenHelper.FIELDS_URL} = ? AND ${DownloadDbOpenHelper.FIELDS_START_POS} = ?"
            val selectionArgs = arrayOf(task.subDownload.url, task.subDownload.startPos.toString())
            writeDb.delete(
                DownloadDbOpenHelper.TABLE_NAME,
                selection,
                selectionArgs
            )
        } catch (e: Exception) {
            Log.e(DownloadConfig.TAG, e.message, e)
        }
    }

    override fun insert(task: SubDownloadTask) {
        try {
            val writeDb = db.writableDatabase
            val value = ContentValues().apply {
                put(DownloadDbOpenHelper.FIELDS_URL, task.subDownload.url)
                put(DownloadDbOpenHelper.FIELDS_COMPLETE_SIZE, task.subDownload.completeSize)
                put(DownloadDbOpenHelper.FIELDS_TASK_SIZE, task.subDownload.taskSize)
                put(DownloadDbOpenHelper.FIELDS_SAVE_FILE, task.subDownload.saveFile)
                put(DownloadDbOpenHelper.FIELDS_START_POS, task.subDownload.startPos)
                put(DownloadDbOpenHelper.FIELDS_END_POS, task.subDownload.endPos)
                put(DownloadDbOpenHelper.FIELDS_CURRENT_POS, task.subDownload.currentPos)
                put(DownloadDbOpenHelper.FIELDS_STATUS, task.subDownload.status.name)
            }
            writeDb.insert(DownloadDbOpenHelper.TABLE_NAME, null, value)
        } catch (e: Exception) {
            Log.e(DownloadConfig.TAG, e.message, e)
        }
    }

    override fun update(task: SubDownloadTask) {
        try {
            val writeDb = db.writableDatabase
            val value = ContentValues().apply {
                put(DownloadDbOpenHelper.FIELDS_COMPLETE_SIZE, task.subDownload.completeSize)
                put(DownloadDbOpenHelper.FIELDS_TASK_SIZE, task.subDownload.taskSize)
                put(DownloadDbOpenHelper.FIELDS_SAVE_FILE, task.subDownload.saveFile)
                put(DownloadDbOpenHelper.FIELDS_END_POS, task.subDownload.endPos)
                put(DownloadDbOpenHelper.FIELDS_CURRENT_POS, task.subDownload.currentPos)
                put(DownloadDbOpenHelper.FIELDS_STATUS, task.subDownload.status.name)
            }

            val selection =
                "${DownloadDbOpenHelper.FIELDS_URL} = ? AND ${DownloadDbOpenHelper.FIELDS_START_POS} = ?"
            val selectionArgs = arrayOf(task.subDownload.url, task.subDownload.startPos.toString())
            writeDb.update(
                DownloadDbOpenHelper.TABLE_NAME,
                value,
                selection,
                selectionArgs
            )
        } catch (e: Exception) {
            Log.e(DownloadConfig.TAG, e.message, e)
        }
    }

    override fun queryByTaskTag(url: String, saveFile: String): List<SubDownloadModel> {
        try {
            val readDb = db.readableDatabase
            val projection =
                arrayOf(
                    DownloadDbOpenHelper.FIELDS_URL,
                    DownloadDbOpenHelper.FIELDS_TASK_SIZE,
                    DownloadDbOpenHelper.FIELDS_START_POS,
                    DownloadDbOpenHelper.FIELDS_END_POS,
                    DownloadDbOpenHelper.FIELDS_COMPLETE_SIZE,
                    DownloadDbOpenHelper.FIELDS_CURRENT_POS,
                    DownloadDbOpenHelper.FIELDS_STATUS,
                    DownloadDbOpenHelper.FIELDS_SAVE_FILE
                )

            val selection =
                "${DownloadDbOpenHelper.FIELDS_URL} = ? AND ${DownloadDbOpenHelper.FIELDS_SAVE_FILE}"
            val selectionArgs = arrayOf(url, saveFile)

            val sortOrder = "${DownloadDbOpenHelper.FIELDS_START_POS} DESC"

            val cursor = readDb.query(
                DownloadDbOpenHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
            )

            val result = mutableListOf<SubDownloadModel>()
            with(cursor) {
                while (moveToNext()) {
                    val sub = SubDownloadModel(
                        getString(getColumnIndexOrThrow(DownloadDbOpenHelper.FIELDS_URL)),
                        getLong(getColumnIndexOrThrow(DownloadDbOpenHelper.FIELDS_TASK_SIZE)),
                        getLong(getColumnIndexOrThrow(DownloadDbOpenHelper.FIELDS_START_POS)),
                        getLong(getColumnIndexOrThrow(DownloadDbOpenHelper.FIELDS_END_POS)),
                        getLong(getColumnIndexOrThrow(DownloadDbOpenHelper.FIELDS_CURRENT_POS)),
                        getString(getColumnIndexOrThrow(DownloadDbOpenHelper.FIELDS_SAVE_FILE))
                    ).apply {
                        completeSize =
                            getLong(getColumnIndexOrThrow(DownloadDbOpenHelper.FIELDS_COMPLETE_SIZE))
                        val statusStr =
                            getString(getColumnIndexOrThrow(DownloadDbOpenHelper.FIELDS_STATUS))
                        status = when (statusStr) {
                            DownloadStatus.IDLE.name -> DownloadStatus.IDLE
                            DownloadStatus.DOWNLOADING.name -> DownloadStatus.DOWNLOADING
                            DownloadStatus.PAUSE.name -> DownloadStatus.PAUSE
                            DownloadStatus.ERROR.name -> DownloadStatus.ERROR
                            DownloadStatus.COMPLETED.name -> DownloadStatus.COMPLETED
                            else -> DownloadStatus.PAUSE
                        }
                    }
                    result.add(sub)
                }
            }
            cursor.close()
            return result
        } catch (e: Exception) {
            Log.e(DownloadConfig.TAG, e.message, e)
        }
        return emptyList()
    }
}