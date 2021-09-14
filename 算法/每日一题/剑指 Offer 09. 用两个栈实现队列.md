## [剑指 Offer 09. 用两个栈实现队列](https://leetcode-cn.com/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof/)



### 题目描述

用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )

 

示例 1：

输入：

```
["CQueue","appendTail","deleteHead","deleteHead"]
[[],[3],[],[]]
输出：[null,null,3,-1]

```

示例 2：

输入：

```
["CQueue","deleteHead","appendTail","appendTail","deleteHead","deleteHead"]
[[],[],[5],[2],[],[]]
输出：[null,-1,null,null,5,2]
```


提示：

* 1 <= values <= 10000
* 最多会对 appendTail、deleteHead 进行 10000 次调用



### 思路

栈的特点：先进后出

队列的特点：先进先出

因此使用栈实现队列的要点是**如何取得先放入的元素**。

定义两个栈：A和B，将元素先放入栈A，然后放入栈B，再从栈B拿出元素，这样可以获取元素的倒序。

插入操作：将元素放入栈A。

删除操作：1. 判断栈B是否为空，如果为空，则将栈A的元素一个个放入栈B。

​					2. 进行完第一步操作后，此时栈A已经为空，所有的元素都在栈B中。先判断栈B是否为空，为空则返						回-1，否则返回栈B的栈顶元素。



### 代码

```java
class CQueue {

    Deque<Integer> s1;
    Deque<Integer> s2;

    public CQueue() {
        s1 = new LinkedList<>();
        s2 = new LinkedList<>();
    }
    
    public void appendTail(int value) {
        s1.push(value);
    }
    
    public int deleteHead() {
        if(s2.isEmpty()){
            while(!s1.isEmpty()){  //需要将所有元素放入栈s1，所以用while而不是if
                s2.push(s1.pop());
            }
        }
        if(s2.isEmpty()){
            return -1;
        }else{
            int a = s2.pop();
            return a;
        }

    }
}
```





