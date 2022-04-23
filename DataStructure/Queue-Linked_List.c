#include<stdio.h>
#include<stdlib.h>

#define MAX_SIZE 10

struct node{
    int data;
    struct node* next;
};

struct node* front = NULL;
int num_queue = 0;

int queue_full(){
    if(num_queue == MAX_SIZE){
        return 1;
    }
    else{
        return 0;
    }
    
}

int queue_empty(){
    if(num_queue == 0){
        return 1;
    }
    else{
        return 0;
    }
}


int dequeue(){
    struct node* ptr = front;
    int value = ptr->data;
    front = ptr->next;
    free(ptr);
    num_queue--;
    return value;
}

void enqueue(int value){
    
    struct node* tmp = (struct node*)malloc(sizeof(struct node));
    if(tmp!=NULL){
        tmp->data = value;
        tmp->next = NULL;

        if(front ==NULL){
            front = tmp;
            num_queue++;
            return ;
        }
    //마지막 노드의 다음 링크 걸어주기
        struct node* ptr = front;
        for(int i=0; i<num_queue-1; i++){
            ptr=ptr->next;
        }
        ptr->next=tmp;
        num_queue++;

    }
    else {

        printf("failed to create a node.\n");

        exit(1);

    }
}   


void print_queue(){
    struct node* ptr = front;
    printf("Print data In Queue: ");
    while(ptr!=NULL){
        printf("[%d]-->",ptr->data);
        ptr=ptr->next;
    }
    printf("[NULL]\n");
    free(ptr);
}

void run_enqueues(int arr[], int n){
    int val;
    for(int i=0; i<n; i++){
        val=arr[i];
        if(queue_full()!=1){
            enqueue(val);
            printf("enqueue: %d\n", val);
        }
        else{
            printf("queue is full\n");
        }
    }

}

void run_dequeues(int n){
    int val;
    for(int i=0; i<n; i++){
        val = dequeue();
        printf("dequeue: %d\n", val);
    }
}

void main(){
    int val;
    int input_values[MAX_SIZE]={1,3,5,6,41,56,12,312,22,44};
    int enqueue_num=5;
    int dequeue_num=3;
    run_enqueues(input_values,enqueue_num);
    print_queue();
    run_dequeues(dequeue_num);
    print_queue();
}





