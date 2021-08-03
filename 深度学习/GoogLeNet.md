# GoogLeNet

## 简介

GoogLeNet在2014年由Google团队提出（与VGG网络同年，注意GoogLeNet中的L大写是为了致敬LeNet），斩获当年ImageNet竞赛中Classification Task (分类任务) 第一名。

论文地址：[Going deeper with convolutions](https://arxiv.org/abs/1409.4842)

GoogLeNet的创新点

- 引入了 **Inception** 结构（融合不同尺度的特征信息）
- 使用1x1的卷积核进行降维以及映射处理 （虽然VGG网络中也有，但该论文介绍的更详细）
- 添加两个辅助分类器帮助训练
- 丢弃全连接层，使用平均池化层（大大减少模型参数，除去两个辅助分类器，网络大小只有vgg的1/20）

## Inception结构

传统的CNN结构（如AlexNet、VggNet）都是采用串行结构，而GoogLeNet采用的是并联结构。

![image-20210803020545942](img\image-20210803020545942.png)

**Inception原始结构**：通过四个分支得到不同尺度的特征矩阵，但是每个特征举证的高纬度和宽维度必须相同，否则无法按照深度方向进行拼接。具体结构如图(a)。

**Inception结构+降维**：增加了三个1×1的卷积层。具体结构如图(b)。

**降维功能**

![image-20210803022948118](img\image-20210803022948118.png)

对一个深度为512的特征矩阵使用5×5的卷积核进行卷积，不使用1×1的卷积核降维，需要819200个参数，使用1×1的卷积核降维，需要50688个参数。明显使用1×1的卷积核降维更好。

tips:

1. CNN参数个数 = 输入特征矩阵深度×卷积核尺寸×输出特征矩阵深度 = 卷积核尺寸×卷积核深度 × 卷积核组数
2. 输出特征矩阵的深度是由卷积核的个数所决定的。



## 辅助分类器

GoogLeNet中有三个输出层，其中两个是辅助分类器。

![20200717161450737](img\20200717161450737.png)

如上图所示，被黄色方框圈起来的两处就是辅助分类器。两个辅助分类器的结构是一样的。

The exact structure of the extra network on the side, including the auxiliary classifier, is as follows:

* An ayerage pooling layer with 5×5 filter size and stride 3, resulting in an 4×4×512 output for the(4a), and 4×4×528 for the (4d) stage.
*  A 1×1 convolution with 128 filters for dimension reduction and rectified linear activation.
* A fully connected layer with 1024 units and rectified linear activation.
* A dropout layer with 70% ratio of dropped outputs.
* A linear layer with softmax loss as the classifier(predicting the same 1000 classes as the main classifier, but removed at inference time).

译文：

多出来的网络结构是辅助分类器，它的具体结构是：

* 一个具有5×5过滤器大小和步长3的平均池化层，第一个辅助分类器（Inception(4a)输出）输出的特征矩阵为4×4×512，而第二个辅助分类器（Inception(4d)输出）输出的特征矩阵为4×4×528。

  tips:outsize=(insize-Fsize+2P)/S+1——计算输出矩阵的size

* 采用128个1×1的卷积层进行卷积处理，目的是降低维度，使用了ReLU函数
* 采用结点个数为1024的全连接层，使用了ReLU函数
* 在全连接层之间使用dropout函数，以70%的比例随机缩神经元
* 一个线性层，使用softmax激活函数作为分类器（预测与主分类器相同的1000个类，但在推理时移除）

## GoogLeNet网络参数

![image-20210803033522323](img\image-20210803033522323.png)

参数的对应关系

![image-20210803033745553](img\image-20210803033745553.png)

从左至右边，从上至下，依次的对应关系：

#1×1：1×1 convolutions的个数

#3×3：3×3 convolutions的个数

#3×3 reduce：1×1 convolutions的个数

#5×5：5×5 convolutions的个数

#5×5 reduce：1×1 convolutions的个数

#5pool proj：1×1 convolutions的个数



以上是GoogLeNet的基本概念，未完待续。。。
