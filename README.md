Libraries-for-Android-Developers
================================

Libraries for Android developers By eoe.cn Community

Libraries-for-Android-Developers 0.0.1测试版

实现功能:
	启动apkplug框架 通过MyApplication中AutoStart()函数

设置当框架启动时自动启动的插件，达到启动插件的目的

bundles/ 目录下存放的是插件工程

一、工程目录结构

根目录

├ bundles -- 插件项目
├ libs -- 存放项目引用的第三方包
├ res -- 存放工程用到的图片,布局
├ src -- 存放工程的包及java源码文件

1、libs目录 
libs目录用于存放项目引用的第三方jar包。

libs目录里的jar包文件：

libs

├ armeabi-v7a-libndkoo.so --armv7a架构 apkplug的.so文件
├ armeabi -libndkoo.so --arm架构 apkplug的.so文件
├ x86 -libndkoo.so --x86架构 apkplug的.so文件
├ Bundle1.5.5.jar --apkplug 的jar包
├ android-support-v4.jar --android兼容的包

1、src目录 
src目录用于存放工程的包及java源码文件。

下面是src目录的子目录：

src 

├ cn.eoe.android.libraries --存放程序启动类
├ cn.eoe.app.adapter --存放适配器的实现类的包 
├ cn.eoe.app.adapter.base --存放适配器基类的包
