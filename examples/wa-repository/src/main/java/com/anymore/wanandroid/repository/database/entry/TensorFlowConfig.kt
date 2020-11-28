package com.anymore.wanandroid.repository.database.entry

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

/**
 * 记录TensorFlow模型配置表
 * @param modelId 除主键外的一个特殊id，是服务端在录入此模型配置时生成的一个唯一凭证，
 * @param modelDir 模型文件和标签文件存储的父目录路径，建议为[${模型文件存储总文件夹}/$modelId]
 * @param modelFileName 模型文件的文件名
 * @param labelFileName 标签文件的文件名
 * @param inputSize 输入图片大小
 * @param dataType 输入数据的DataType {@link org.tensorflow.lite.DataType}
 *                  这里存储只存储其枚举对应的数据值，虽然这样很不可靠，如果说DataType值切换就懵逼了，但是考虑模块依赖关系，
 *                  没有去依赖tensorflow模块,暂且认为是可靠的吧
 * @param versionCode 模型文件版本号
 * Created by anymore on 2020/11/22.
 */
@Parcelize
@Entity(tableName = "t_tf_model_configs", indices = [Index(value = ["modelId"], unique = true)])
data class TensorFlowConfig(
    var modelId: String,
    var modelDir: String,
    var modelFileName: String,
    var labelFileName: String,
    var inputSize: Int,
    var dataType: Int,
    var versionCode: Int
) : BaseModel(), Parcelable