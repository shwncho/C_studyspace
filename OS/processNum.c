#include<stdio.h>
#include<sys/types.h>
#include<unistd.h>

int main(){
	int i;
	pid_t pid1;

	for(i=0; i<4; i++){
		pid1=fork();
		printf("pid%d: %d\n",i+1,pid1);
	}

	return 0;
}
