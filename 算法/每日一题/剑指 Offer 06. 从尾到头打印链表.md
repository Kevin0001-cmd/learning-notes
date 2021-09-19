## [剑指 Offer 06. 从尾到头打印链表](https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof/)



### 题目描述

输入一个链表的头节点，从尾到头反过来返回每个节点的值（**用数组返回**）。



示例 1：

```
输入：head = [1,3,2]
输出：[2,3,1]
```




限制：

0 <= 链表长度 <= 10000



### 思路

链表反转可以联想到栈，将链表中的元素先压入栈，然后依次弹出，就可以得到倒叙的链表。



### 代码

```java
class Solution {
    public int[] reversePrint(ListNode head) {
        ListNode cur = head;
        Stack<Integer> stack = new Stack<>();
        //如果链表为null，则返回空数组
         if(head == null){
            return new int[0];
        }
        int length = 0;
        //将链表中的元素依次压入栈
        while(cur!=null){
            stack.push(cur.val);
            length++;  //记录元素个数
            cur = cur.next;
        }
        int[] a = new int[length];
        //将栈中的元素依次弹出，放入数组中
        for(int i = 0;i<length;i++){
            //此处判断栈中是否还有元素，不可以用stack.pop()，因为会直接将栈顶元素删除
            if(stack.peek() != null){  
                int b = stack.pop();
                a[i]=b;
            }
        }
        return a;
    }
}
```

复杂性分析

时间复杂度：O(n)O(n)。正向遍历一遍链表，然后从栈弹出全部节点，等于又反向遍历一遍链表。
空间复杂度：O(n)O(n)。额外使用一个栈存储链表中的每个节点。

