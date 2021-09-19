## [剑指 Offer 24. 反转链表](https://leetcode-cn.com/problems/fan-zhuan-lian-biao-lcof/)



### 题目内容

定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。

 

示例:

```
输入: 1->2->3->4->5->NULL
输出: 5->4->3->2->1->NULL
```




限制：

0 <= 节点个数 <= 5000



### 思路

先记录当前节点的next节点，然后定义一个空节点，将当前节点指向空节点，然后将当前节点赋给空节点，接着将当前节点指向当前节点的next.



### 代码

#### 链表反转

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode next = null;
        ListNode pre = null;
        if(head == null || head.next == null){
            return head;
        }
        while(head!=null){
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }
}
```

**链表的变化过程**

假设链表为1→2→3→∅

当前节点为1，当前节点的next为2

next=head.next：由于要将头节点1和第二个节点2之间的链断开，因此需要先定义一个空节点记录新的头节点2。

head.next=pre：pre是定义的一个空节点，这一步实际上是将1指向2的链断开，将1指向了pre节点。

pre=head：pre节点是一个空节点，这一步是将节点1的值赋给了pre。

以上三部完成后，实际上已经将当前链表变成了两个链表，一个是1->NULL，另一个是2->3->NULL。

head=next：为了继续将剩下的节点反转，将头节点设置为原来头节点的next，next节点即第一步记录的节点。

**链表变化过程如下：**

![](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202109181452629.png)

#### 递归法

```java
public Node reverse(Node head) {
    if (head == null || head.next == null)
        return head;
    Node temp = head.next;
    Node newHead = reverse(head.next);
    temp.next = head;
    head.next = null;
    return newHead;
}
```

递归法相当于将链表依次压入栈，然后弹栈的过程。

将head.next看作一个整体

Node temp = head.next：记录head的next节点

Node newHead = reverse(head.next)：将head.next反转并记录为一个新节点，

temp.next = head：将temp指向head，即将head和temp之间的指针反转了

head.next = null：将head指向head.next的链断掉



假设head=3，那么

第一步：temp=3.next，即4节点，

第二步：Node newHead =  reverse(4)，newHead即4节点

第三步：temp.next = head，即4.next=3，链表变成4->3

第四步：3.next=null，即3不再指向4

重复上述过程，最后可以就可以得到反转的链表

**链表变化过程如下：**

![image-20210919100311438](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202109191003544.png)

