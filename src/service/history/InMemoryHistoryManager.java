package service.history;

import service.task.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> customLinkedList = new HashMap<>();

    private Node head;
    private Node tail;

    public void linkLast(Task task) {
        Node oldTail = tail;
        Node newNode = new Node(oldTail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        customLinkedList.put(task.getId(), newNode);
    }

    public List<Task> getTasks(Node node) {
        List<Task> history = new ArrayList<>();
        if (node == null) return history;
        while (node != null) {
            history.add(node.data);
            node = node.next;
        }
        return history;
    }

    public void removeNode(Node node) {
        if (node == head) {
            if (node.next != null) {
                head = node.next;
            }
            if (node.next == null && node.prev == null) {
                head = null;
                tail = null;
            }
        }
        if (node.prev != null) {
            node.prev.next = node.next;

        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        node.next = null;
    }

    @Override
    public void add(Task task) {
        if (task == null) return;
        int id = task.getId();
        if (customLinkedList.containsKey(id)) {
            removeNode(customLinkedList.get(id));
        }
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (customLinkedList.containsKey(id)) {
            removeNode(customLinkedList.get(id));
            customLinkedList.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks(head);
    }

    @Override
    public String toString() {
        return "InMemoryHistoryManager{" +
                "customLinkedList=" + customLinkedList +
                ", head=" + head +
                ", tail=" + tail +
                '}';
    }


    public static class Node {
        public Task data;
        public Node next;
        public Node prev;

        public Node(Node prev, Task data, Node next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }


        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }
}
