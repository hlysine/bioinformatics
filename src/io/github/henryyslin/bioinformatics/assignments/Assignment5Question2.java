package io.github.henryyslin.bioinformatics.assignments;

// CSCI3220 2021-22 First Term Assignment 5
//
// I declare that the assignment here submitted is original except for source material
// explicitly acknowledged, and that the same or closely related material has not been
// previously submitted for another course. I also acknowledge that I am aware of
// University policy and regulations on honesty in academic work, and of the
// disciplinary guidelines and procedures applicable to breaches of such policy and
// regulations, as contained in the following websites.
//
// University Guideline on Academic Honesty:
// http://www.cuhk.edu.hk/policy/academichonesty/
//
// Student Name: Lin Yik Shun
// Student ID: 1155157489

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Assignment5Question2 {

    public static void main(String[] args) {
        // Read inputs
        Scanner console = new Scanner(System.in);

        int n = console.nextInt();
        List<Node> nodes = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                nodes.add(new Node(console.nextInt(), i, j));
            }
        }

        // Keep track of clusters that haven't been merged yet
        List<Integer> clusters = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            clusters.add(i);
        }

        // Construct the min-heap
        MinHeap<Node> minHeap = new MinHeap<>(nodes.toArray(new Node[0]));
        System.out.println(minHeap);

        // Merge clusters until there is only one cluster left
        while (minHeap.size() > 1) {
            // Remove the node between the two clusters to be merged
            Node node = minHeap.remove(0);
            System.out.println(minHeap);

            // Update nodes related to the merged clusters
            for (int i : clusters) {
                if (i == node.cluster1 || i == node.cluster2) continue;

                int idx1 = minHeap.find(e -> e.cluster1 == i && e.cluster2 == node.cluster2 || e.cluster1 == node.cluster2 && e.cluster2 == i);
                Node node1 = minHeap.remove(idx1);
                System.out.println(minHeap);

                int idx2 = minHeap.find(e -> e.cluster1 == i && e.cluster2 == node.cluster1 || e.cluster1 == node.cluster1 && e.cluster2 == i);
                Node node2 = minHeap.remove(idx2);
                System.out.println(minHeap);

                minHeap.insert(new Node(Math.min(node1.value, node2.value), Math.max(i, node.cluster2), Math.min(i, node.cluster2)));
                System.out.println(minHeap);
            }

            // Remove the merged cluster
            clusters.remove(Integer.valueOf(node.cluster1));
        }
    }

    /**
     * A node in the min heap with additional cluster information.
     */
    static class Node implements Comparable<Node> {
        int value;
        int cluster1;
        int cluster2;

        public Node(int value, int cluster1, int cluster2) {
            this.value = value;
            this.cluster1 = cluster1;
            this.cluster2 = cluster2;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(value, o.value);
        }

        public String toString() {
            return Integer.toString(value);
        }
    }

    /**
     * A generic min-heap implementation.
     *
     * @param <E> The type of elements in the heap.
     */
    static class MinHeap<E extends Comparable<E>> {
        private final E[] heap;
        private int size;

        public MinHeap(E[] heap) {
            this.heap = heap;
            this.size = heap.length;
            buildHeap();
        }

        /**
         * Heapify the array.
         */
        private void buildHeap() {
            for (int i = size / 2; i >= 0; i--) {
                heapifyDown(i);
            }
        }

        /**
         * Return the index of the first element that matches the given predicate.
         *
         * @param predicate The predicate to match.
         * @return The index of the first element that matches the given predicate.
         */
        public int find(Predicate<E> predicate) {
            for (int i = 0; i < size; i++) {
                if (predicate.test(heap[i])) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Insert an element into the heap.
         *
         * @param e The element to insert.
         */
        public void insert(E e) {
            if (size == heap.length) {
                throw new IllegalStateException("Heap is full");
            }
            heap[size] = e;
            size++;
            heapifyUp(size - 1);
        }

        /**
         * Remove an element by index and return the removed element.
         *
         * @param i The index of the element to remove.
         * @return The removed element.
         */
        public E remove(int i) {
            if (i < 0 || i >= size) {
                throw new IllegalArgumentException("Index out of bounds (" + i + ")");
            }
            E temp = heap[i];
            swap(i, size - 1);
            size--;
            heapifyDown(i);
            heapifyUp(i);
            return temp;
        }

        /**
         * Get the current size of the heap.
         *
         * @return The current size of the heap.
         */
        public int size() {
            return size;
        }

        /**
         * Bubble up the element at the given index recursively until it is no longer smaller than its parent.
         *
         * @param i The index of the element to bubble up.
         */
        private void heapifyUp(int i) {
            if (i == 0) {
                return;
            }
            int parent = (i - 1) / 2;
            if (heap[i].compareTo(heap[parent]) < 0) {
                swap(i, parent);
                heapifyUp(parent);
            }
        }

        /**
         * Bubble down the element at the given index recursively until it is no longer larger than its children.
         *
         * @param i The index of the element to bubble down.
         */
        private void heapifyDown(int i) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;
            if (left < size && heap[left].compareTo(heap[i]) < 0) {
                smallest = left;
            }
            if (right < size && heap[right].compareTo(heap[smallest]) < 0) {
                smallest = right;
            }
            if (smallest != i) {
                swap(i, smallest);
                heapifyDown(smallest);
            }
        }

        /**
         * Swap the elements at the given indices.
         *
         * @param i The index of the first element.
         * @param j The index of the second element.
         */
        private void swap(int i, int j) {
            E temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }

        /**
         * Return a string representation of the heap.
         *
         * @return A string representation of the heap.
         */
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(heap[i]);
                if (i != size - 1)
                    sb.append(",");
            }
            return sb.toString();
        }
    }
}
