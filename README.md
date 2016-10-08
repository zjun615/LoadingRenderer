Readme
===
LoadingRenderer —— 此开源项目为加载动画

---
##目录
* [更新](#更新)
* [1-Win10加载动画](#1-win10加载动画)
 * [1_1-效果图](#1_1-效果图)
 * [1_2-使用](#1_2-使用)
 * [1_3-原理分析](#1_3-原理分析)
 
 ##更新
 v1.1：优化圆点的起始位置，使其与原生更接近  
   ![win10_v1.1](img/win10_v1.1.gif)
  
 v1：  添加Win10加载动画Win10LoadingRenderer
 
 ##1-Win10加载动画
 ###1_1-效果图
 ![Win10LoadingRenderer](img/Win10LoadingRenderer.gif)
 
 ###1_2-使用
 ```xml
<!--先在基布局中添加命名空间：xmlns:zjun="http://schemas.android.com/apk/res-auto"-->

 <com.zjun.loadingrenderer.Win10LoadingRenderer
        android:layout_width="50dp"
        android:layout_height="100dp"
        zjun:dotColor="#00F" />
 ```
 
 ###1_3-原理分析
 [参考CSDN博客](http://blog.csdn.net/a10615/article/details/52745963)
 
---
[回到顶部](#readme)
