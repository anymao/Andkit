package com.anymore.wanandroid.repository.database

import androidx.room.Dao
import com.anymore.wanandroid.repository.database.entry.TensorFlowConfig

/**
 * Created by anymore on 2020/11/22.
 */
@Dao
abstract class TensorFlowConfigDao: BaseDao<TensorFlowConfig>() {

}