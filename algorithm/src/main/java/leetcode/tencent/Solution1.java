package leetcode.tencent;

import lombok.val;

/**
 * @Description
 * @Date 2023/11/24 10:56
 * @Created by hugh
 */
public class Solution1 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode();
        ListNode head = result;
        int x = 0;
        while (l1 != null || l2 != null) {
            int a = l1 == null ? 0 : l1.val;
            int b = l2 == null ? 0 : l2.val;
            int sum = a + b;
            ListNode node = new ListNode(sum % 10 + x);
            result.next = node;
            x = sum/10;

            l1 = l1 == null? l1.next : null;
            l2 = l2 == null? l2.next : null;
        }
        return result;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

}
