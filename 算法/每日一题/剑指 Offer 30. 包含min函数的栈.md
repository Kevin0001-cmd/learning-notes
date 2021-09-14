## [剑指 Offer 30. 包含min函数的栈](https://leetcode-cn.com/problems/bao-han-minhan-shu-de-zhan-lcof/)



### 题目

定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，调用 min、push 及 pop 的时间复杂度都是 O(1)。

 

示例:

```
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.min();   --> 返回 -3.
minStack.pop();
minStack.top();      --> 返回 0.
minStack.min();   --> 返回 -2.
```




提示：

各函数的调用总次数不超过 20000 次



### 思路

定义两个栈，一个栈A用来保存，另一个栈B用来排序。

push：将值放入栈A和栈B

在向栈A中添加值时，对于栈B有两种情况

1. 栈B为空，直接将当前值同时放入栈A和栈B
2. 栈B不为空，为了保持栈B的值从栈底至栈顶数值按照从大到小排序，将当前值与栈B的栈顶值比较，若小于栈顶值，则将当前值插入到栈B，否则什么也不做

pop：删除栈A的栈顶元素，且将栈A的栈顶元素和栈B的栈顶元素比较，如果相等，则删除栈B的栈顶元素。

top：获取栈A的栈顶元素

min：栈B中栈顶元素即最小的值，直接获取即可



### 代码

```java
class MinStack {

    Stack<Integer> A,B;


    /** initialize your data structure here. */
    public MinStack() {
        A = new Stack<>();
        B = new Stack<>();
    }
    
    public void push(int x) {
        A.add(x);
        if(B.empty() || B.peek() >=x){
            B.push(x);
        }
    }
    
    public void pop() {
        if(A.pop().equals(B.peek())){  //A.pop()操作会删掉A的栈顶元素
            B.pop();
        }
    }
    
    public int top() {
        return A.peek();
    }
    
    public int min() {
        return B.peek();
    }
}
```



### 注意点

1. Stack中`pop()`和`peek()`的差别

   pop()：删除栈顶元素并返回

   peek()：获取栈顶元素而不进行删除操作

2. `pop()`测试

   代码

   ```
    @Test
       public void test(){
           Stack<Integer> stack = new Stack<Integer>();
           stack.push(1);
           stack.push(2);
           stack.push(3);
           stack.push(4);
   
           System.out.println("======栈修改之前======"+stack);
           if(stack.pop().equals(4)){
               System.out.println("======栈修改之后======"+stack);
           }
       }
   ```

   结果

   ![](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202109141526532.png)

