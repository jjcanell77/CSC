package CSC400.M8;
public class CustomQueue {
    private int queueLength = 3;
    private int[] items = new int[queueLength];
    private int front = -1; 
    private int back = -1;
    private int size = 0;

    boolean isFull() {
        return size == queueLength;
    }

    boolean isEmpty() {
        return size == 0;
    }

    void resize() {
        queueLength *= 2;
        int[] newItems = new int[queueLength];
        System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
    }

    void enQueue(int itemValue) {
        if (isFull()) {
            resize();
        }
        else if (front == -1 && back == -1){
            front = back = 0;
            items[back] = itemValue;
        } else {
            back++;
            items[back] = itemValue;
        }
        size++;
    }

    void deQueue() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Nothing to dequeue");
        } else if (front == back) {
            front = back = -1;
            size = 0;
        } else {
            front++;
            size--;
        }
    }

    void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
        } else {
            for (int i = front; i <= back; i++) {
                System.out.println(items[i]);
            }
        }
    }

    void peak() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
        } else {
            System.out.println("Front value is: " + items[front]);
        }
    }

    public static void main(String[] args) {
        CustomQueue myQueue = new CustomQueue();
        int[] array = {1, 2 , 3, 4};
        for (int i : array) {
            myQueue.enQueue(i);

        }
        myQueue.display();
        myQueue.peak();

        myQueue.deQueue();
        myQueue.display();
        myQueue.peak();
    }
}