# Andkit

## 简介

Andkit是一个比较灵活的Android组件化方案，是我在学习和工作中的积累和一次尝试。总结了工作和学习其他项目中的一些优秀的思想，同时也解决之前遇到的一些问题。

### Andkit 的核心架构

![Andkit 的基本架构图](http://cdn.1or1.icu/1580899195.png)

如上图所展示，Andkit框架最核心由这三部分组成，它们的作用分别是:

- andkit-lifecycle：定义框架之中Activity和Fragment的必要接口（用于实现依赖注入、EventBus事件订阅等），并且将Activity、Fragment和Application在生命周期中的一些关键回调事件进行封装处理,其实andkit-lifecycle部分。
- andkit-repository：封装仓储层的实现，如OkHttp、Retrofit、Room、Gson等等与数据的操作相关的组件的配置和提供，并且暴露方法，可通过Application直接获取全局统一的仓储层对象实例。
- andkit：统筹lifecycle和repository层，管理各个组件间依赖注入的完成、管理不同模块的AbsApplicationWrapper[代理Application，组件内没有真正的Application，具体必须要在Application生命周期中要调用的方法，可在AbsApplicationWrapper对应生命周期中完成，并且通过@Kiss注解添加到主模块的Application上]。

___注意:Andkit框架选择了Dagger2作为依赖注入的工具，所以如果在子模块(组件)也使用了Dagger2去注入你的Activity或者Fragment，那么需要将子模块的Module通过@Component 中的 modules参数添加到主模块的Component中去，这样在子模块(组件)无论是在单独运行时亦或是作为模块运行时，其依赖注入都会正常。详情请参考[WanAndroid模块的Component](https://github.com/anymao/Andkit/blob/master/examples/wanandroid/src/main/java/com/anymore/wanandroid/di/component/WanAndroidComponent.kt)和[wa-user模块的Module](https://github.com/anymao/Andkit/blob/master/examples/wa-user/src/main/java/com/anymore/wanandroid/di/module/UserModule.kt)。虽然在Andkit框架中引入了Dagger2作为依赖注入的工具，但是实际上在实际开发子模块时候，可以完全不需要使用Dagger2进行注入，自己进行依赖的管理。___

事实上，Andkit框架只依赖以上三个库就可以完成组件化的工作，但是由于IActivity和IFragment中只提供了少量方法：

```kotlin
interface IActivity {
    fun useFragment() = false
    fun useEventBus() = false
    fun injectable() = false
}
```

```kotlin
interface IFragment {
    fun useEventBus()=false
    fun injectable()=false
}
```

所以无论是Activity还是Fragment对于应用的模式都没有要求，无论你是想用MVP还是MVVM都可以，只要按照要求实现IActivity和IFragment接口中的方法就行了，在之前工作和学习用到的框架中，都是提供了类似于BaseActivity和BaseFragment，Base*提供了模板代码，业务开发中直接继承，他们都提供了大量封装好的基类方法，便于快速开发，少写很多重复代码。Andkit提供了模板代码，但不强制必须依赖这些模板代码，你也可以直接定制适合自己业务的模板代码。

因此，andkit框架亦实现了一套mvvm和mvp的基础框架

所以完整版的框架结构图如下:

![完整版的框架结构图](http://cdn.1or1.icu/1580899137.png)

- andkit-dagger：提供一套与Dagger2相关的注解和Module，在dagger-android中会使用到。
- andkit-mvvm:一整套MVVM的模板代码，使用了Databinding。
- andkit-mvp:一整套MVP的模板代码

andkit作为整个应用的顶级统筹者，它不关心应用的底层是MVP还是MVVM,所以组件化时候，A模块是MVVM，那么可以直接A模块依赖andkit-mvvm，对应的Activity或者Fragment继承自BaseActivity即可以按照MVVM的模式开发，同理B模块如果按照MVP模式开发，那么B模块依赖andkit-mvp即可以按照MVP模式开发。如果说andkit-mvvm或者andkit-mvp设计不够合理或者不满足业务需求，可以自己依赖andkit-lifecycle和andkit-repository实现自己的框架即可。

## 工程目录结构

![工程目录结构](http://cdn.1or1.icu/1580898157.png)

整个工程分为三个文件夹，即三个部分：

- andkit-libs: andkit框架的所有组件，其他工程主要依赖本框架的子组件即可实现对应模块开发。
- examples:以WanAndroid相关Api开发的demoAPP，组件之间通过ARoute实现页面路由跳转。.
- flutter：flutter 模块，提供了两个页面(关于和妹子图片)，flutter模块子模块被wa-mine模块调用。



基于Andkit框架的示例代码，本示例是使用鸿洋WanAndroid开发API开发，同时尝试组件化，WanAndroid项目的架构图如下:

![WanAndroid项目架构图](http://cdn.1or1.icu/1580996246.png)

___注意：底层组件(route、resources、common、repository)并不是完全严格意义上的平行关系互不依赖，由于某些需求，组件间会去共用一些方法和类，所以底层组件间可能会产生一些依赖关系___

- wa-resources:存放组件间共用资源，无实际代码
- wa-common:提供组件间共用方法，线程池等
- wa-repository:提供组件间的共用仓储层配置，如Room，OkHttp等
- wa-route:提供组件间路由跳转相关的路由路径信息和路由拦截器(如登陆拦截器)
- wa-about:是使用flutter开发的两个页面，主要是想尝试一下如何将Flutter集成到现有APP中
- wa-browse:提供WebActivity和FragmentActivity，对于常用的url跳转和fragment打开提供打开方式
- wa-user:用户模块，提供登陆和注册退出功能
- wa-articles:文章模块，提供首页和知识体系两个页面相关的数据列表
- wa-mine:我的模块，提供todo、收藏列表等功能
- wanandroid:应用的壳模块，提供MainActivity的界面布局

### 组件单独运行

将gradle.properties中的moduleRunning设置为true,并同步Sync Now，同步完成后，wa-user、wa-articles、wa-browse和wa-mine均为application，都可以单独运行起来，此时wanandroid不参与工程的编译(也没有办法参与编译，因为application不可以依赖application，只能依赖library)，此时项目结构如下：

![组件单独运行时项目结构](http://cdn.1or1.icu/1581168248.png)



在组件的build.gradle中，通过moduleRunning来判断AndroidManifest的位置，因为如果是组件化的时候，组件是不可以有Application的，但是在单独运行的时候，应用需要有Application：

```groovy
sourceSets {
            main {
                if (moduleRunning.toBoolean()) {
                    manifest.srcFile 'src/profile/AndroidManifest.xml'
                }else {
                    manifest.srcFile 'src/main/AndroidManifest.xml'
                }
            }
}
```

此时组件可以单独编译运行起来。

### 组件化运行

将gradle.properties中的moduleRunning设置为true,并同步Sync Now，同步完成后，wa-user、wa-articles、wa-browse和wa-mine均为library，wanandroid模块参与编译且成为唯一的application模块，此时项目结构为:

![组件化运行时项目结构](http://cdn.1or1.icu/1580898157.png)

wanandroid项目体验apk:

![apk-url](http://cdn.1or1.icu/apk-url.png)

### 关于组件中如何使用在Application相应的生命周期调用相关方法

由于组件中不能声明Application，但是实际上在一些组件中有这种在Application中初始化的情况等需求，如同本例中mine组件由于引入了Flutter，所以在Application中需要初始化Flutter。

由于整个应用中只能声明一个Application，而且在组件化的时候，这个Application应当是处于最顶层的，子模块无法直接在Application中去为所欲为。但是andkit-lifecycle中提供了一个重要接口[IApplicationLifecycle](https://github.com/anymao/Andkit/blob/master/andkit-libs/andkit-lifecycle/src/main/java/com/anymore/andkit/lifecycle/application/IApplicationLifecycle.kt)它的作用是用来模拟Application中的重要的生命周期回调方法，并且在真正的Application的对于的周期中调用，没错，在组件中我们可以生命任意个IApplicationLifecycle（实际上是继承[AbsApplicationWrapper](https://github.com/anymao/Andkit/blob/master/andkit-libs/andkit-lifecycle/src/main/java/com/anymore/andkit/lifecycle/application/AbsApplicationWrapper.kt)直接在这里面当做Application），并且在它的生命周期回调方法中搞事情。andkit组件中，提供了[AndkitApplication](https://github.com/anymao/Andkit/blob/master/andkit-libs/andkit/src/main/java/com/anymore/andkit/AndkitApplication.kt)实际运行时候AndroidManifest中声明的Application必须是AndkitApplication（组件中没有使用注入，无需在Application中搞事情）或者其子类。如果组件中定义了AbsApplicationWrapper，则需要通过[@Kiss](https://github.com/anymao/Andkit/blob/master/andkit-libs/andkit/src/main/java/com/anymore/andkit/annotations/Kiss.kt)或者[@Kisses](https://github.com/anymao/Andkit/blob/master/andkit-libs/andkit/src/main/java/com/anymore/andkit/annotations/Kisses.kt)注解将定义的AbsApplicationWrapper挂载到真正的Application上，并且指定其优先级，___注意:优先级数字越大，在Application对应生命周期中越先调用执行。___如本例中所有的AbsApplicationWrapper调用顺序为:

![WanAndroid AbsApplicationWrapper调用顺序](http://cdn.1or1.icu/1581170339.png)

### 关于壳模块集成子组件后的操作

Andkit组件化中，在壳模块中再集成新的子组件模块后，有可能需要做的操作：

- 在壳模块的Component的注解中添加组件中的Module，这样在运行时，Dagger2才能完成组件中的依赖注入。
- 在壳模块声明的Application上通过@Kiss和@Kisses注解，将子模块声明的AbsApplicationWrapper挂载到真正的Application上，Application会在运行时收集排序所有的AbsApplicationWrapper，并且管理。关于AbsApplicationWrapper为什么必须声明在Application的注解中，而不是通过AndroidManifest的meta-data节点中，这样在运行时候也可以读取到AbsApplicationWrapper啊？对这块我的考虑是，每个模块只需要关心它是不是需要在Application的某些生命周期的回调中搞事情？如果需要，就自己搞个AbsApplicationWrapper，组件在它里面搞事情就可以了，但是真正组件化的时候，A，B，C都有各自的AbsApplicationWrapper，那么其实它在整个组件化时候各自的顺序其实不应该是各组件关心的事情，这一步应当由壳模块的Application来指定。

### 参考项目

[MVVMArms](https://github.com/xiaobailong24/MVVMArms)