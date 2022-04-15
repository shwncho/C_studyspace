#include<stdio.h>
#include<stdlib.h>
#define MAX_SIZE 5

int arr[MAX_SIZE]={0,};
int front=0;
int rear=0;

void enqueue(int value){
    if(front==0 && rear==0){
        front=(front+1)%MAX_SIZE;
        rear=(rear+1)%MAX_SIZE;
    }
    else{
        rear=(rear+1)%MAX_SIZE;
    }
    arr[rear]=value;


}

int dequeue(){
    return arr[front++];
}

int queue_full(){
    if(front==rear+1)    return 1;
    else    return 0;
}

int queue_empty(){
    if(front==rear)  return 1;
    else    return 0;
}

void print_queue(){
    printf("print queue:");
    int i=front;
    do{
        printf("%d ",arr[i]);
        i=(i+1)%MAX_SIZE;
    }while(i!=rear+1);
    printf("\n");

}





int main(void){
    int input_value[]={10,50,20,70,100};
    int len= sizeof(input_value) / sizeof(int);
    int value;
    for(int i=0; i<len; i++){
        value=input_value[i];
        if(queue_full()!=1){
            enqueue(value);
            printf("enqueue:%d ",value);
        }
        else{
            printf("queue is full");
        }
    }

    printf("\n");
    print_queue();

    for(int i=0; i<3; i++){
        value=dequeue();
        printf("dequeue:%d ",value);
    }

    printf("\n");
    print_queue();

    
}

