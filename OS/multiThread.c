#include<pthread.h>
#include<stdio.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<unistd.h>

#define _CRT_SECURE_NO_WARNINGS


int value = 0;
double avg=0.0;
int arr[100];
int n=0; // n is to be entered by user 

int max_value;
int min_value;

void *avgFunc(void *param); //calculate average
void *maxFunc(void *param); //search max_value 
void *minFunc(void *param); //search min_value

int main(int argc, char *argv[]){
	pthread_t tid1,tid2,tid3;
	pthread_attr_t attr;
    
	printf("Enter the number of values you want to enter: ");
	scanf("%d",&n);
	
	printf("\nEnter %d number:\n", n);
	for(int i=0; i<n; i++){
		scanf("%d", &arr[i]);
	}

	pthread_attr_init(&attr);
	pthread_create(&tid1,&attr,avgFunc,NULL);
	pthread_join(tid1,NULL);
	pthread_create(&tid2,&attr,maxFunc,NULL);
    	pthread_join(tid2,NULL);
	pthread_create(&tid3,&attr,minFunc,NULL);
    	pthread_join(tid3,NULL);

    	printf("The average value is %0.lf\n",avg);
    	printf("The minimum value is %d\n", min_value);
    	printf("The maximum value is %d\n",max_value);
    
	
}

void *avgFunc(void *param){
    int sum=0;
    for(int i=0; i<n; i++){
        sum=sum+arr[i];
    }
    avg=sum/n;
    pthread_exit(0);
}

void *maxFunc(void *param){
    max_value=arr[0];
    for(int i=0; i<n; i++){
        if(arr[i]>max_value)    max_value=arr[i];
    }
    pthread_exit(0);

}

void *minFunc(void *param){
    min_value=arr[0];
    for(int i=0; i<n; i++){
        if(arr[i]<min_value)    min_value=arr[i];
    }
    pthread_exit(0);
}

