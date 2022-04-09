#include<pthread.h>
#include<stdio.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<unistd.h>

#define _CRT_SECURE_NO_WARNINGS


int value = 0;
double avg=0.0;
int arr[7];

int max_value;
int min_value;

void *runner1(void *param);
void *runner2(void *param);
void *runner3(void *param);

int main(int argc, char *argv[])
{
pthread_t tid1,tid2,tid3;
pthread_attr_t attr;
    
    scanf("%d %d %d %d %d %d %d", &arr[0], &arr[1], &arr[2], &arr[3], &arr[4], &arr[5], &arr[6]);

	pthread_attr_init(&attr);
	pthread_create(&tid1,&attr,runner1,NULL);
	pthread_join(tid1,NULL);
	pthread_create(&tid2,&attr,runner2,NULL);
    pthread_join(tid2,NULL);
	pthread_create(&tid3,&attr,runner3,NULL);
    pthread_join(tid3,NULL);

    printf("The average value is %0.lf\n",avg);
    printf("The minimum value is %d\n", min_value);
    printf("The maximum value is %d\n",max_value);
    
	
}

void *runner1(void *param){
    int len = sizeof(arr)/sizeof(int);
    int sum=0;
    for(int i=0; i<len; i++){
        sum=sum+arr[i];
    }
    avg=sum/len;
	pthread_exit(0);
}

void *runner2(void *param){
    int len = sizeof(arr)/sizeof(int);
    max_value=arr[0];
    for(int i=0; i<len; i++){
        if(arr[i]>max_value)    max_value=arr[i];
    }
    pthread_exit(0);

}

void *runner3(void *param){
    int len = sizeof(arr)/sizeof(int);
    min_value=arr[0];
    for(int i=0; i<len; i++){
        if(arr[i]<min_value)    min_value=arr[i];
    }
    pthread_exit(0);
}

