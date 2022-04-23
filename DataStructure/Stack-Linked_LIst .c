#include<stdio.h>
#include<stdlib.h>

#define MAX_SIZE 10

struct node{
    int data;
    struct node* link;
};


struct node* head=NULL;
int n_nodes = 0;

int stack_full(){
    if(n_nodes >= MAX_SIZE) return 1;
    return 0;
}

int stack_empty(){
    if (n_nodes ==0)    return 1;
    return 0;
}


void push(int x){
    struct node* temp = malloc(sizeof(struct node));
    temp -> data = x;
    temp -> link = head;    // 이전 노드를 link 시켜주는 것
    head = temp;    //여기서 head는 새로생긴 노드를 할당
    n_nodes++;
}

int pop(){
    int value;
    struct node* tmp;

    tmp = head;
    value = tmp -> data;
    head = tmp->link;   // 원래 stack의 head는 pop됐으므로 비어있는 null을 할당해줘야 한다.
    n_nodes --;
    free(tmp);
    return value;
}

void run_pushed(int arr[], int n){
    for(int i=0; i<n; i++){
       int value = arr[i];
       if(stack_full()!=1){
           push(value);
           printf("push: %d\n",value);
       } 
       else{
           printf("stack is full\n");
       }
    }

}

void run_pops(int n){
    for(int i=0; i<n; i++){
	if(stack_empty()!=1){
        	int val=pop();
        	printf("pop: %d\n",val);
    	}
	else{
		printf("stack is empty");
	}
    }

}

void print_stack(){
    struct node *tmp = head;
    printf("Print data In Stack: ");
    while(tmp != NULL){
        printf("[%d]-->", tmp->data);
        tmp=tmp->link;
    }
    printf("[NULL]\n");
    free(tmp);

}

int main(){
    int numbers[MAX_SIZE] = {1,3,5,6,41,56,12,312,22,44};
    int push_num = 5;
    int pop_num = 3;
    
    run_pushed(numbers, push_num);
    print_stack();
    run_pops(pop_num);
    print_stack();


   



    return 0;
}
