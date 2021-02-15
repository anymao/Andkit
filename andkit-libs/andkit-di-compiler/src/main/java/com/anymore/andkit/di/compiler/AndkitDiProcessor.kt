package com.anymore.andkit.di.compiler

import com.anymore.andkit.di.DataBundle
import com.anymore.andkit.di.RepositoryConfiguration
import com.anymore.andkit.di.RetrofitService
import com.anymore.andkit.di.RoomDao
import com.anymore.andkit.di.compiler.generators.ActivityDataBundleModuleGenerator
import com.anymore.andkit.di.compiler.generators.FragmentDataBundleModuleGenerator
import com.anymore.andkit.di.compiler.generators.RepositoryConfigurationModuleGenerator
import com.google.auto.service.AutoService
import net.ltgt.gradle.incap.IncrementalAnnotationProcessor
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType
import java.util.*
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedOptions
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
@IncrementalAnnotationProcessor(IncrementalAnnotationProcessorType.AGGREGATING)
@SupportedOptions(value = [SUPPORT_OPTION_MODULE_NAME])
class AndkitDiProcessor : BaseProcessor() {

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        moduleName = processingEnv?.options?.get(SUPPORT_OPTION_MODULE_NAME)
            ?.capitalize(Locale.getDefault())
            ?: throw RuntimeException("没有设置${SUPPORT_OPTION_MODULE_NAME}")
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        if (annotations?.isNotEmpty() == true && roundEnv != null) {
            logger.v("开始处理注解:$annotations")
            try {
                processRetrofitService(annotations, roundEnv)
                processRoomDao(annotations, roundEnv)
                processBundle(annotations, roundEnv)
                processRepositoryConfiguration(annotations, roundEnv)
            } catch (e: Exception) {
                logger.e(e)
                return false
            }
            return true
        }
        return false
    }


    private fun processRetrofitService(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ) {
        logger.v("开始处理注解:${RetrofitService::class.qualifiedName}")
        val retrofitServiceElements = roundEnv.getElementsAnnotatedWith(RetrofitService::class.java)
        retrofitServiceElements.forEach {
            logger.v("找到：${it.simpleName}")
        }
    }

    private fun processRoomDao(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ) {
        logger.v("开始处理注解:${RoomDao::class.qualifiedName}")
        val roomDaoElements = roundEnv.getElementsAnnotatedWith(RoomDao::class.java)
        roomDaoElements.forEach {
            logger.v("找到：${it.simpleName}")
            it.kind
        }

    }

    private fun processBundle(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ) {
        val activityMirror = elementUtils.getTypeElement(ACTIVITY).asType()
        val fragmentMirror = elementUtils.getTypeElement(FRAGMENT).asType()
        val fragmentV4Mirror = elementUtils.getTypeElement(FRAGMENT_V4).asType()
        val fragmentXMirror = elementUtils.getTypeElement(FRAGMENT_X).asType()

        logger.v("开始处理注解:${DataBundle::class.qualifiedName}")

        val dataBundleElements = roundEnv.getElementsAnnotatedWith(DataBundle::class.java)
        val activityElements = dataBundleElements.filter { it.kind == ElementKind.CLASS }
            .filter { (typeUtils.isSubtype(it.asType(), activityMirror)) }
        if (activityElements.isNotEmpty()) {
            val activityModuleGenerator = ActivityDataBundleModuleGenerator(moduleName, filer)
            activityElements.forEach {
                logger.v("find ${it.simpleName}")
                activityModuleGenerator.addActivityDataBundleElement(it)
            }
            activityModuleGenerator.generate()
        }

        val fragmentElements = dataBundleElements.filter { it.kind == ElementKind.CLASS }.filter {
            typeUtils.isSubtype(
                it.asType(),
                fragmentMirror
            ) || typeUtils.isSubtype(
                it.asType(),
                fragmentV4Mirror
            ) || typeUtils.isSubtype(it.asType(), fragmentXMirror)
        }
        if (fragmentElements.isNotEmpty()) {
            val fragmentModuleGenerator = FragmentDataBundleModuleGenerator(moduleName, filer)
            fragmentElements.forEach {
                fragmentModuleGenerator.addFragmentDataBundleElement(it)
            }
            fragmentModuleGenerator.generate()
        }
    }

    private fun processRepositoryConfiguration(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ) {
        logger.v("开始处理注解：${RepositoryConfiguration::class.qualifiedName}")
        val repositoryConfigurationElements =
            roundEnv.getElementsAnnotatedWith(RepositoryConfiguration::class.java)
        if (repositoryConfigurationElements.size > 1) {
            throw Exception("only one RepositoryConfiguration Support!,but found ${repositoryConfigurationElements.map { it.simpleName }}")
        }
        repositoryConfigurationElements.firstOrNull { it.kind == ElementKind.CLASS }?.let {
            val generator = RepositoryConfigurationModuleGenerator(
                elementUtils.getPackageOf(it).qualifiedName.toString(),
                it,
                filer
            )
            generator.generate()
        }
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return SupportType.getSupportTypes()
    }

}