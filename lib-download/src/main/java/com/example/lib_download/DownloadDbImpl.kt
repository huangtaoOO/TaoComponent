package com.example.lib_download

import android.content.ContentValues
import com.example.lib_download.db.DownloadDbOpenHelper
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
        val writeDb = db.writableDatabase
        val selection =
            "${DownloadDbOpenHelper.FIELDS_URL} = ? AND ${DownloadDbOpenHelper.FIELDS_START_POS} = ?"
        val selectionArgs = arrayOf(task.url, task.startPos.toString())
        writeDb.delete(
            DownloadDbOpenHelper.TABLE_NAME,
            selection,
            selectionArgs
        )
    }

    override fun insert(task: SubDownloadTask) {
        val writeDb = db.writableDatabase
        val value = ContentValues().apply {
            put(DownloadDbOpenHelper.FIELDS_URL, task.url)
            put(DownloadDbOpenHelper.FIELDS_COMPLETE_SIZE, task.completeSize)
            put(DownloadDbOpenHelper.FIELDS_TASK_SIZE, task.taskSize)
            put(DownloadDbOpenHelper.FIELDS_SAVE_FILE, task.saveFile.absolutePath)
            put(DownloadDbOpenHelper.FIELDS_START_POS, task.startPos)
            put(DownloadDbOpenHelper.FIELDS_END_POS, task.endPos)
            put(DownloadDbOpenHelper.FIELDS_CURRENT_POS, task.currentPos)
            put(DownloadDbOpenHelper.FIELDS_STATUS, task.status.name)
        }
        writeDb.insert(DownloadDbOpenHelper.TABLE_NAME, null, value)
    }

    override fun update(task: SubDownloadTask) {
        val writeDb = db.writableDatabase
        val value = ContentValues().apply {
            put(DownloadDbOpenHelper.FIELDS_COMPLETE_SIZE, task.completeSize)
            put(DownloadDbOpenHelper.FIELDS_TASK_SIZE, task.taskSize)
            put(DownloadDbOpenHelper.FIELDS_SAVE_FILE, task.saveFile.absolutePath)
            put(DownloadDbOpenHelper.FIELDS_END_POS, task.endPos)
            put(DownloadDbOpenHelper.FIELDS_CURRENT_POS, task.currentPos)
            put(DownloadDbOpenHelper.FIELDS_STATUS, task.status.name)
        }

        val selection =
            "${DownloadDbOpenHelper.FIELDS_URL} = ? AND ${DownloadDbOpenHelper.FIELDS_START_POS} = ?"
        val selectionArgs = arrayOf(task.url, task.startPos.toString())
        writeDb.update(
            DownloadDbOpenHelper.TABLE_NAME,
            value,
            selection,
            selectionArgs
        )

    }

    override fun queryByTaskTag(url: String): List<SubDownloadTask> {
        val readDb = db.readableDatabase
        val projection =
            arrayOf(
                DownloadDbOpenHelper.FIELDS_URL,
                DownloadDbOpenHelper.FIELDS_TASK_SIZE,
                DownloadDbOpenHelper.FIELDS_START_POS,
                DownloadDbOpenHelper.FIELDS_END_POS,
                DownloadDbOpenHelper.FIELDS_COMPLETE_SIZE,
                DownloadDbOpenHelper.FIELDS_TASK_SIZE,
                DownloadDbOpenHelper.FIELDS_STATUS,
                DownloadDbOpenHelper.FIELDS_SAVE_FILE
            )

        val selection = "${DownloadDbOpenHelper.FIELDS_URL} = ?"
        val selectionArgs = arrayOf(url)

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

        val result = mutableListOf<SubDownloadTask>()
        with(cursor) {
            while (moveToNext()) {
                val sub = SubDownloadTask(
                    getString(getColumnIndexOrThrow(DownloadDbOpenHelper.FIELDS_URL)),
                    getLong(getColumnIndexOrThrow(DownloadDbOpenHelper.FIELDS_TASK_SIZE)),
                    getLong(getColumnIndexOrThrow(DownloadDbOpenHelper.FIELDS_START_POS)),
                    getLong(getColumnIndexOrThrow(DownloadDbOpenHelper.FIELDS_END_POS)),
                    getLong(getColumnIndexOrThrow(DownloadDbOpenHelper.FIELDS_CURRENT_POS)),
                    File(getString(getColumnIndexOrThrow(DownloadDbOpenHelper.FIELDS_SAVE_FILE))),
                    null
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
    }
}