package main.structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import main.utils.SortMode;

public class PriorityQueue<Type extends Comparable<Type>> {

    private List<Type> heap = new ArrayList<>();

    public final SortMode sortMode;

    public static final SortMode DEFAULT_SORT_MODE = SortMode.ASCENDING;

    public PriorityQueue() {
        this.sortMode = DEFAULT_SORT_MODE;
    }

    public PriorityQueue(SortMode sortMode) {
        this.sortMode = sortMode;
    }

    public PriorityQueue(List<Type> heap) {
        this.setHeap(heap);
        this.sortMode = DEFAULT_SORT_MODE;
    }

    public PriorityQueue(List<Type> heap, SortMode sortMode) {
        this.sortMode = sortMode;
        this.setHeap(heap);
    }

    public void setHeap(List<Type> heap) {
        this.heap = heap;
        this.sort();
    }

    public int size() {
        return this.heap.size();
    }

    private boolean checkIfShouldInvert(Type bigger, Type smaller) {
        if (this.sortMode == SortMode.DESCENDING) {
            return bigger.compareTo(smaller) < 0;
        } else {
            return bigger.compareTo(smaller) > 0;
        }
    }

    public boolean elementExists(int order) {
        order -= 1;
        return (order < this.heap.size()) && order >= 0;
    }

    public void assertThatElementExists(int order) throws IndexOutOfBoundsException {
        if (!this.elementExists(order)) {
            throw new IndexOutOfBoundsException("Não existe um " + order + "º elemento nessa lista.");
        }
    }

    public Type getElement(int order) throws IndexOutOfBoundsException {
        this.assertThatElementExists(order);

        int index = order - 1;
        return this.heap.get(index);
    }

    public void setElement(int order, Type element) throws IndexOutOfBoundsException {
        this.assertThatElementExists(order);

        int index = order - 1;
        this.heap.set(index, element);
    }

    public void add(Type item) throws RuntimeException {
        this.heap.add(item);
        try {
            this.moveUp(this.heap.size());
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Ocorreu um erro ao inserir o item.");
        }
    }

    public void addAll(Collection<Type> items) {
        for (Type item : items) {
            this.add(item);
        }
    }

    public Type remove() throws IndexOutOfBoundsException {
        if (this.heap.size() > 0) {
            try {
                Type toRemove = this.heap.get(0);

                this.setElement(1, this.heap.get(this.heap.size() - 1));
                this.heap.remove(this.heap.size() - 1);

                if (this.heap.size() > 0) {
                    this.moveDown(1);
                }

                return toRemove;
            } catch (IndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException("Não foi possível remover o item. Talvez a lista já esteja vazia.");
            }
        } else {
            throw new IndexOutOfBoundsException("A lista já está vazia.");
        }
    }

    private void moveUp(int order) throws IndexOutOfBoundsException {
        this.assertThatElementExists(order);

        Type current = this.getElement(order);
        int parentOrder = order / 2;

        if (this.elementExists(parentOrder)) {
            Type parent = this.getElement(parentOrder);

            if (this.checkIfShouldInvert(current, parent)) {
                this.setElement(order, parent);
                this.setElement(parentOrder, current);

                this.moveUp(parentOrder);
            }
        }
    }

    private void moveDown(int order) throws IndexOutOfBoundsException {
        this.assertThatElementExists(order);

        Type current = this.getElement(order);

        int firstChildOrder = order * 2;
        int secondChildOrder = order * 2 + 1;

        if (!this.elementExists(firstChildOrder)) {
            return;
        }

        Type firstChild = this.getElement(firstChildOrder);
        Type childToSwitch = firstChild;
        int childToSwitchOrder = firstChildOrder;

        if (this.elementExists(secondChildOrder)) {
            Type secondChild = this.getElement(secondChildOrder);

            if (this.checkIfShouldInvert(secondChild, firstChild)) {
                childToSwitch = secondChild;
                childToSwitchOrder = secondChildOrder;
            }
        }

        if (this.checkIfShouldInvert(childToSwitch, current)) {
            this.setElement(order, childToSwitch);
            this.setElement(childToSwitchOrder, current);

            this.moveDown(childToSwitchOrder);
        }
    }

    private void sort() {
        try {
            for (int i = this.heap.size() / 2; i > 0; i--) {
                this.moveDown(i);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Ocorreu um erro ao ordenar a lista.");
        }
    }

    public List<Type> toList() {
        return new ArrayList<>(this.heap);
    }
}
