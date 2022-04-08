#include<stdio.h>
#include<stdlib.h>


struct node{
    int data;
    struct node* link;
};


struct node* head=NULL;
int n_nodes = 0;



void push(int x){
    struct node* temp = malloc(sizeof(struct node));
    temp -> data = x;
    temp -> link = head;
    head = temp;
    n_nodes++;
}

int pop(){
    int value;
    struct node* tmp;

    tmp = head;
    value = tmp -> data;
    head = tmp->link;
    n_nodes --;
    free(tmp);
    return value;
}

void run_pops(int n){
    for(int i=0; i<n; i++){
        int val=pop();
        printf("pop: %d ",val);
    }

}

void print_stack(){
    struct node *tmp = head;
    printf("Result of converting decimal number to binary number: ");
    while(tmp != NULL){
        printf("%d ", tmp->data);
        tmp=tmp->link;
    }
    free(tmp);

}

void decimalTobinary(int n){
    int value;
    printf("\nDecimal number: %d\n", n);
    while(n!=0){
        value = n%2;
        n=n/2;
        push(value);
    }
    print_stack();
    head=NULL;

}

int main(){
    
    for(int i=0; i<5; i++){
        int number[5]={85, 170, 233, 280, 424};
        decimalTobinary(number[i]);
    }


    return 0;
}