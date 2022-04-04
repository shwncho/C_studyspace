#include<stdio.h>

#define MAX_SIZE 10

int stack[MAX_SIZE] = {0,};
int top = -1;

void push(int value){
    stack[++top] = value;
}

int pop(){
    return stack[top--];
}

int stack_full(){
    if((top + 1) == MAX_SIZE) return 1;
    else return 0;
}

int stack_empty(){
    if(top == -1) return 1;
    else return 0;
}


void print_stack(){
    printf("Print a stack: ");
    for(int i=0; i<=top; i++){
        printf("%d ", stack[i]);
    }
    printf("\n");
}

void main(){
    int val;
    int input_values[] = {1,2,3,27,49,10,26,19};

    int len = sizeof(input_values) / sizeof(int);

    for(int i=0; i< len; i++){
        val = input_values[i];
        if(stack_full() != 1){
            push(val);
            printf("Push: %d\n", val);
        }
        else{
            printf("stack is full.\n");
        }
    }

    print_stack();

    while(stack_empty() != 1){
        val = pop();
        printf("Pop: %d\n", val);
    }
    printf("stack is empty.\n");
}