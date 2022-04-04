#include<stdio.h>

#define MAX_SIZE 10

int front = -1;
int rear = -1;
int queue[MAX_SIZE];

void enqueue(int value){
    if(front == -1){
        front=rear=0;
    }
    else{   
        rear=rear+1;
    }
    queue[rear]=value;
}

void repacking_queue(){
    for(int i=front; i<rear; i++){
        queue[i] = queue[i+1];
    }
    front =0;
    rear = rear -1;
}

int dequeue(){
    int value = queue[front];
    if(front == rear){
        front = -1;
        rear = -1;
    }
    else{
        repacking_queue();
    }
    return value;
}

int queue_full(){
    if(rear + 1 == MAX_SIZE) return 1;
    else return 0;
}

int queue_empty(){
    if(front == -1) return 1;
    else return 0;
}

void print_queue(){
    printf("Print a queue: ");

    for(int i=front; i<=rear; i++){
        printf("%d ", queue[i]);
    }
    printf("\n");
}

void main(){
    int val;
    int input_values[] = {1,2,3,4,5,6,7,8,9,10};

    int len = sizeof(input_values) / sizeof(int);

    for (int i=0; i<len; i++){
        val = input_values[i];
        if(queue_full()!=1){
            enqueue(val);
            printf("Enqueue: %d\n", val);
        }
        else{
            printf("Queue is full.\n");
        }
    }

    print_queue();

    while(queue_empty() != 1){
        val = dequeue();
        printf("Dequeue: %d\n", val);
    }
    printf("Queue is empty.\n");
}
