#include<stdio.h>
#include<stdlib.h>
#include<time.h>

#define PROCESS_NUM 10

typedef struct PCB* PCBpointer;
typedef struct PCB{
    int pid;
    int priority;
    int arrival_time;
    int burst_time;
    int wt; // waiting time
    int rt; // response time
    int tt; // turnaround time
    struct PCB* next;
}PCB;

typedef struct readyQueue{
    struct PCB* head;
    struct PCB* tail;
}readyQueue;



int ready_queue_num=0;

PCB list[4]={{1,1,0,4,0,-1,0},
            {2,10,2,3,0,-1,0},
            {3,2,5,3,0,-1,0},
            {4,20,12,2,0,-1,0}};

// job queue에 프로세스들 넣기
PCBpointer jobQueue[4];

PCBpointer runningProcess=NULL;

void init_job_queue(){
    int i;
    for(i=0; i<4; i++){
        jobQueue[i]=NULL;
    }
}

void insert_job_queue(){
    
    for(int i=0; i<4; i++){
        jobQueue[i]=(PCBpointer)malloc(sizeof(struct PCB));
        jobQueue[i]->pid=list[i].pid;
        jobQueue[i]->priority=list[i].priority;
        jobQueue[i]->arrival_time=list[i].arrival_time;
        jobQueue[i]->burst_time=list[i].burst_time;
        jobQueue[i]->wt=list[i].wt;
        jobQueue[i]->rt=list[i].rt;
        jobQueue[i]->tt=list[i].tt;

    }
}

//도착순으로 job queue에 있는 프로세스들 정렬
void sortByat(int n){
    PCBpointer t;
    //정렬
    for(int i=0; i<n-1; i++){
        int minIdx=i;
        for(int j=i+1; j<n; j++){
            if(jobQueue[j]->arrival_time<jobQueue[minIdx]->arrival_time){
                minIdx=j;
            }
        }
        t=jobQueue[i];
        jobQueue[i]=jobQueue[minIdx];
        jobQueue[minIdx]=t;
    }
}

PCBpointer cloneJobQueue[4];
int clone_job_queue_num=0;
void clone_job_queue(){
    for(int i=0; i<4; i++){
        cloneJobQueue[i]=(PCBpointer)malloc(sizeof(struct PCB));
        cloneJobQueue[i]=jobQueue[i];
        clone_job_queue_num++;
    }
}


void FCFS(){
    float avgwt=0.0;
    float avgrt=0.0;
    float avgtt=0.0;

    int sumwt=0,sumrt=0,sumtt=0;
    int idle=0;

    int time=0;
    int i=0; //index

    readyQueue rq;
    PCBpointer t=NULL;
    PCBpointer ptr=NULL;

    printf("Scheduling : FCFS\n");
    printf("=================================================\n");

    while(!(clone_job_queue_num==0 && ready_queue_num==0 && runningProcess==NULL)){
        //job queue에서 arriveTime이 time과 일치하면 readyQueue로 넣어주기
            //ready queue num이 0이면 PCB를 readyQueue의 head,tail로 연결
            // ready queue num이 0이 아니면 새로운 process를 PCB의 next로 추가, tail에도 추가 
            //job queue num --, ready queue num ++
        
        if(cloneJobQueue[i]->arrival_time==time){
            printf("<time %d> [new arrival] process %d\n",time,cloneJobQueue[i]->pid);
            
            if(ready_queue_num==0){
                rq.head=cloneJobQueue[i];
                rq.tail=cloneJobQueue[i];
            }
            else{
                cloneJobQueue[i-1]->next=cloneJobQueue[i];
                rq.tail=cloneJobQueue[i];
            }
            i++;
            clone_job_queue_num--;
            ready_queue_num++;
        }

        //runningProcess없다면 그리고 ready queue에 프로세스 있다면
            //->ready queue에 있는거 running으로 보내주기
            //running으로 가는 프로세스의 next를 ready queue의 head로 보내주고 next=null
            //ready queue num -- , new arrival 출력, process is running 출력
            //burstTime --, turnaroundtime++
            //넣어줄게 없으면 system is idle 출력, idle++
        if(runningProcess==NULL){
            if(rq.head!=NULL){
                runningProcess=rq.head;
                if(runningProcess->rt==-1)   runningProcess->rt=runningProcess->wt;

                t=rq.head;
                rq.head=t->next;
                t->next=NULL;

                printf("<time %d> process %d is running\n",time,runningProcess->pid);
                
                ready_queue_num--;
                runningProcess->burst_time--;
                runningProcess->tt++;   //여기에서 tt는 running state일 때의 시간

                
            }
            else{
                printf("<time %d> ---- system is idle ----\n",time);
                idle++;
            }
        }

        //runningProcess 기존에 있다면
            // prorocess is running 출력
            // runningProcess의 burstTime --, turnaroundtime ++
            // 만약 runningProcess의 burstTime이 0이 되면 runningProcess=NULL, 

        else if(runningProcess!=NULL){
            printf("<time %d> process %d is running\n",time,runningProcess->pid);
            runningProcess->burst_time--;
            runningProcess->tt++;   //여기에서 tt는 running state일 때의 시간
            
            if(runningProcess->burst_time==0){
                runningProcess=NULL;
            }
        }

        //readyQueue에(프로세스가 있다면) 있는 프로세스들 watingTime++
        if(rq.head!=NULL){
            ptr=rq.head;
            while(ptr!=NULL){
                ptr->wt++;
                ptr=ptr->next;
            }
        }

        //time++
        time++;
    }
    printf("<time %d> all processes finish\n",time);
    printf("=================================================\n");

    for(int j=0; j<4; j++){
        sumwt+=cloneJobQueue[j]->wt;
        sumrt+=cloneJobQueue[j]->rt;
        sumtt+=cloneJobQueue[j]->tt + cloneJobQueue[j]->wt; //tt는 running state일 때만 더해줬으므로 ready queue에 있을 때도 지금 더해줌
    }

    // printf("sumwt%d\n",sumwt);
    // printf("sumrt%d\n",sumrt);
    // printf("sumtt%d\n",sumtt);

    avgwt=(float)sumwt/4;
    avgrt=(float)sumrt/4;
    avgtt=(float)sumtt/4;


    printf("Average cpu usage : %.2lf%%\n",((float)idle/time)*100);
    printf("Average waiting time : %.1lf\n",avgwt);
    printf("Average response time : %.1lf\n",avgrt);
    printf("Average turnaround time : %.1lf\n",avgtt);



}

//알고리즘 수행 뒤 job_queue는 초기화, ready queue는 비우기
int main(int argc, char *argv[]){

    insert_job_queue();
    sortByat(4);
    clone_job_queue();
    FCFS();
    



    return 0;
}




