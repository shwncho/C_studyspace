#include<stdio.h>
#include<stdlib.h>

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
    int tq; // timequantum
    struct PCB* next;
}PCB;
struct PCB list[PROCESS_NUM];

typedef struct readyQueue{
    struct PCB* head;
    struct PCB* tail;
}readyQueue;

FILE *inFile, *outFile;

readyQueue rq;
PCBpointer jobQueue[PROCESS_NUM];
PCBpointer runningProcess=NULL;
PCBpointer prevRunningProcess=NULL;
PCBpointer cloneJobQueue[PROCESS_NUM];


int ready_queue_num=0;
int clone_job_queue_num=0;
int time=0;

void read_process_list(const char* file_name);
void init_job_queue();
void insert_job_queue();
void sortByat(int n);
void insert_clone_job_queue();
void clear_time();
void add_waiting_time();
void FCFS();
void RR_timequantum(int timequantum);
void RR_simulator(int timequantum);
void apply_aging(float alpha);
void insert_by_priority(PCBpointer newProcess);
void swap_PCB (PCBpointer a, PCBpointer b);
void sort_ready_queue();
void Priority_algorithm(float alpha);
void Priority_simulator(float alpha);
void clear_clone_job_queue();
void clear_job_queue();



void read_process_list(const char* file_name){
    int i;
    int pid, priority, arrival_time,burst_time;

    inFile = fopen(file_name, "r");
    if(inFile==NULL){
        printf("\nFile Could not be opened");
        return;
    }
    else{
        for(int i=0; i<PROCESS_NUM; i++){
            fscanf(inFile, "%d %d %d %d", &list[i].pid, &list[i].priority, &list[i].arrival_time, &list[i].burst_time);
            list[i].wt=0;
            list[i].rt=-1;
            list[i].tt=0;
            list[i].tq=0;
            list[i].next=NULL;
        }
    }
    fclose(inFile);
}


void init_job_queue(){
    int i;
    for(i=0; i<PROCESS_NUM; i++){
        jobQueue[i]=NULL;
    }
}

void insert_job_queue(){
    
    for(int i=0; i<PROCESS_NUM; i++){
        jobQueue[i]=(PCBpointer)malloc(sizeof(struct PCB));
        jobQueue[i]->pid=list[i].pid;
        jobQueue[i]->priority=list[i].priority;
        jobQueue[i]->arrival_time=list[i].arrival_time;
        jobQueue[i]->burst_time=list[i].burst_time;
        jobQueue[i]->wt=list[i].wt;
        jobQueue[i]->rt=list[i].rt;
        jobQueue[i]->tt=list[i].tt;
        jobQueue[i]->tq=list[i].tq;
        jobQueue[i]->next=NULL;

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

//각 알고리즘마다 ready queue에서의 활동이 달라지므로 이것을 jobqueue에서 부터 보장해주기 위해 clone을 이용
void insert_clone_job_queue(){
    for(int i=0; i<PROCESS_NUM; i++){
        cloneJobQueue[i]=(PCBpointer)malloc(sizeof(struct PCB));
        cloneJobQueue[i]->pid=jobQueue[i]->pid;
        cloneJobQueue[i]->priority=jobQueue[i]->priority;
        cloneJobQueue[i]->arrival_time=jobQueue[i]->arrival_time;
        cloneJobQueue[i]->burst_time=jobQueue[i]->burst_time;
        cloneJobQueue[i]->wt=jobQueue[i]->wt;
        cloneJobQueue[i]->rt=jobQueue[i]->rt;
        cloneJobQueue[i]->tt=jobQueue[i]->tt;
        cloneJobQueue[i]->tq=jobQueue[i]->tq;
        cloneJobQueue[i]->next=NULL;
        clone_job_queue_num++;
    }
}

void clear_time(){
    time=0;
}

void add_waiting_time(){
    PCBpointer ptr=NULL;
    if(rq.head!=NULL){
        ptr=rq.head;
        while(ptr!=NULL){
            ptr->wt++;
            ptr=ptr->next;
        }
    }
}

void FCFS(){
    float avgwt=0.0;
    float avgrt=0.0;
    float avgtt=0.0;

    int sumwt=0,sumrt=0,sumtt=0;
    int idle=0;

    int i=0; //index


    rq.head=rq.tail=NULL;

    PCBpointer ptr=NULL;

    printf("Scheduling : FCFS\n");
    printf("=================================================\n");

    fprintf(outFile,"Scheduling : FCFS\n");
    fprintf(outFile,"=================================================\n");

    while(!(clone_job_queue_num==0 && ready_queue_num==0 && runningProcess==NULL)){
        //job queue에서 arriveTime이 time과 일치하면 readyQueue로 넣어주기
            //ready queue num이 0이면 PCB를 readyQueue의 head,tail로 연결
            // ready queue num이 0이 아니면 새로운 process를 PCB의 next로 추가, tail에도 추가 
            //job queue num --, ready queue num ++

        //도착시간이 같은 프로세스가 있을 수 있으므로 while문
        while(i<PROCESS_NUM && cloneJobQueue[i]->arrival_time==time){
            printf("<time %d> [new arrival] process %d\n",time,cloneJobQueue[i]->pid);
            fprintf(outFile,"<time %d> [new arrival] process %d\n",time,cloneJobQueue[i]->pid);
            
            if(ready_queue_num==0){
                rq.head=cloneJobQueue[i];
                rq.tail=cloneJobQueue[i];
            }
            else{
                
                rq.tail->next=cloneJobQueue[i];
                rq.tail=cloneJobQueue[i];
            }
            i++;
            clone_job_queue_num--;
            ready_queue_num++;
        }

        //runningProcess없다면 그리고 ready queue에 프로세스 있다면
            //->ready queue에 있는거 running으로 보내주기
            //running으로 가는 프로세스의 next를 ready queue의 head로 보내주고 next=null
            //ready queue num -- process is running 출력
            //burstTime --, turnaroundtime++
            //넣어줄게 없으면 system is idle 출력, idle++
        
        
        if(runningProcess==NULL){
            if(rq.head!=NULL){
                runningProcess=rq.head;
                if(runningProcess->rt==-1)   runningProcess->rt=runningProcess->wt;
                if(prevRunningProcess==NULL)    prevRunningProcess=runningProcess;
            

                if(rq.head->next!=NULL){
                    rq.head=rq.head->next;
                    runningProcess->next=NULL;
                }
                else{
                    rq.head=NULL;
                }

                if((prevRunningProcess!=NULL && runningProcess!=NULL)){
                    if(prevRunningProcess!=runningProcess){
                        printf("------------------------------------  (Context-Switch)\n");
                        fprintf(outFile,"------------------------------------  (Context-Switch)\n");
                    }
                }

                printf("<time %d> process %d is running\n",time,runningProcess->pid);
                fprintf(outFile,"<time %d> process %d is running\n",time,runningProcess->pid);
                
                ready_queue_num--;
                runningProcess->burst_time--;
                runningProcess->tt++;   //여기에서 tt는 running state일 때의 시간

                if(runningProcess->burst_time==0){
                    printf("<time %d> process %d is finished\n",time,runningProcess->pid);
                    fprintf(outFile,"<time %d> process %d is finished\n",time,runningProcess->pid);
                    runningProcess=NULL;
                }


                
            }
            else{
                printf("<time %d> ---- system is idle ----\n",time);
                fprintf(outFile,"<time %d> ---- system is idle ----\n",time);
                idle++;

                if(rq.head==NULL)    prevRunningProcess=NULL;
                
            }

            
        }

        //runningProcess 기존에 있다면
            // prorocess is running 출력
            // runningProcess의 burstTime --, turnaroundtime ++
            // 만약 runningProcess의 burstTime이 0이 되면 runningProcess=NULL, 

        else if(runningProcess!=NULL){
            printf("<time %d> process %d is running\n",time,runningProcess->pid);
            fprintf(outFile,"<time %d> process %d is running\n",time,runningProcess->pid);
            runningProcess->burst_time--;
            runningProcess->tt++;   //여기에서 tt는 running state일 때의 시간
            
            if(runningProcess->burst_time==0){
                printf("<time %d> process %d is finished\n",time,runningProcess->pid);
                fprintf(outFile,"<time %d> process %d is finished\n",time,runningProcess->pid);    
                runningProcess=NULL;
            }
        }

    
        add_waiting_time();
        //time++
        time++;
    }
    printf("<time %d> all processes finish\n",time);
    printf("=================================================\n");
    fprintf(outFile,"<time %d> all processes finish\n",time);
    fprintf(outFile,"=================================================\n");


    for(int j=0; j<PROCESS_NUM; j++){
        sumwt+=cloneJobQueue[j]->wt;
        sumrt+=cloneJobQueue[j]->rt;
        sumtt+=cloneJobQueue[j]->tt + cloneJobQueue[j]->wt; //tt는 running state일 때만 더해줬으므로 ready queue에 있을 때도 지금 더해줌
    }


    avgwt=(float)sumwt/PROCESS_NUM;
    avgrt=(float)sumrt/PROCESS_NUM;
    avgtt=(float)sumtt/PROCESS_NUM;


    printf("Average cpu usage : %.2f%%\n",((time-idle)/(float)time)*100);
    printf("Average waiting time : %.1f\n",avgwt);
    printf("Average response time : %.1f\n",avgrt);
    printf("Average turnaround time : %.1f\n",avgtt);

    fprintf(outFile,"Average cpu usage : %.2f%%\n",((time-idle)/(float)time)*100);
    fprintf(outFile,"Average waiting time : %.1f\n",avgwt);
    fprintf(outFile,"Average response time : %.1f\n",avgrt);
    fprintf(outFile,"Average turnaround time : %.1f\n",avgtt);

}
void RR_timequantum(int timequantum){

    if(runningProcess!=NULL){
        if(runningProcess->tq>=timequantum && runningProcess->tq%timequantum==0){
            if(rq.head!=NULL){
                rq.tail->next=runningProcess;
                rq.tail=runningProcess;
                
                ready_queue_num++;                    

                prevRunningProcess=runningProcess;

                runningProcess=NULL;
            
            }
                
        }        
    }

}
//Round Robin
void RR_simulator(int timequantum){
    float avgwt=0.0;
    float avgrt=0.0;
    float avgtt=0.0;

    int sumwt=0,sumrt=0,sumtt=0;
    int idle=0;

    int i=0; //index

    rq.head=rq.tail=NULL;
    
    
    //context switch를 비교하기위한 변수
    prevRunningProcess=NULL;

    printf("\nScheduling : RR\n");
    printf("=================================================\n");

    fprintf(outFile,"\nScheduling : RR\n");
    fprintf(outFile,"=================================================\n");

    while(!(clone_job_queue_num==0 && ready_queue_num==0 && runningProcess==NULL)){
        //job queue에서 arriveTime이 time과 일치하면 readyQueue로 넣어주기
            //ready queue num이 0이면 PCB를 readyQueue의 head,tail로 연결
            // ready queue num이 0이 아니면 새로운 process를 PCB의 next로 추가, tail에도 추가 
            //job queue num --, ready queue num ++
        

        //도착시간이 같은 프로세스가 있을 수 있으므로 while문
        while(i<PROCESS_NUM && cloneJobQueue[i]->arrival_time==time){
            printf("<time %d> [new arrival] process %d\n",time,cloneJobQueue[i]->pid);
            fprintf(outFile,"<time %d> [new arrival] process %d\n",time,cloneJobQueue[i]->pid);
            
            if(ready_queue_num==0){
                rq.head=cloneJobQueue[i];
                rq.tail=cloneJobQueue[i];
            }
            else{
                rq.tail->next=cloneJobQueue[i];
                rq.tail=cloneJobQueue[i];
            }
            i++;
            clone_job_queue_num--;
            ready_queue_num++;
        }

        RR_timequantum(timequantum);
                
        //runningProcess없다면 그리고 ready queue에 프로세스 있다면
            //->ready queue에 있는거 running으로 보내주기
            //running으로 가는 프로세스의 next를 ready queue의 head로 보내주고 next=null
            //ready queue num -- , process is running 출력
            //burstTime --, turnaroundtime++
            //넣어줄게 없으면 system is idle 출력, idle++
        
        
        if(runningProcess==NULL){
            if(rq.head!=NULL){
                runningProcess=rq.head;
                if(runningProcess->rt==-1)   runningProcess->rt=runningProcess->wt;
                //runningProcess가 NULL인데 prevRunningProcess도 NULL이라는 뜻은 runningProcess가 처음 들어온 시점이라는 의미
                if(prevRunningProcess==NULL)    prevRunningProcess=runningProcess; 

            
                if(rq.head->next!=NULL){
                    rq.head=rq.head->next;
                    runningProcess->next=NULL;
                }
                else{
                    rq.head=NULL;
                }

                if((prevRunningProcess!=NULL && runningProcess!=NULL)){
                    if(prevRunningProcess!=runningProcess){
                        printf("------------------------------------ (Context-Switch)\n");
                        fprintf(outFile,"------------------------------------ (Context-Switch)\n");
                    }
                }

                printf("<time %d> process %d is running\n",time,runningProcess->pid);
                fprintf(outFile,"<time %d> process %d is running\n",time,runningProcess->pid);

                ready_queue_num--;
                runningProcess->burst_time--;
                runningProcess->tt++;   //여기에서 tt는 running state일 때의 시간
                runningProcess->tq++;

                if(runningProcess->burst_time==0){
                    printf("<time %d> process %d is finished\n",time,runningProcess->pid);
                    fprintf(outFile,"<time %d> process %d is finished\n",time,runningProcess->pid);
                    runningProcess=NULL;
                }    
                
            }
            else{
                printf("<time %d> ---- system is idle ----\n",time);
                fprintf(outFile,"<time %d> ---- system is idle ----\n",time);
                idle++;

                //runningProcess가 없고, rq.head도 없는상태이므로 prevRunningProcess도 null로 초기화
                if(rq.head==NULL)    prevRunningProcess=NULL;
            }

            
        }

        //runningProcess 기존에 있다면
            // prorocess is running 출력
            // runningProcess의 burstTime --, turnaroundtime ++
            // 만약 runningProcess의 burstTime이 0이 되면 runningProcess=NULL, 

        else if(runningProcess!=NULL){
           
            runningProcess->tq++;
            
            printf("<time %d> process %d is running\n",time,runningProcess->pid);
            fprintf(outFile,"<time %d> process %d is running\n",time,runningProcess->pid);
            runningProcess->burst_time--;
            runningProcess->tt++;   //여기에서 tt는 running state일 때의 시간

            
            if(runningProcess->burst_time==0){
                printf("<time %d> process %d is finished\n",time,runningProcess->pid);    
                fprintf(outFile,"<time %d> process %d is finished\n",time,runningProcess->pid);
                runningProcess=NULL;
            }

            
        }

        

        //readyQueue에(프로세스가 있다면) 있는 프로세스들 watingTime++
        add_waiting_time();

        //time++
        time++;
    }
    printf("<time %d> all processes finish\n",time);
    printf("=================================================\n");

    fprintf(outFile,"<time %d> all processes finish\n",time);
    fprintf(outFile,"=================================================\n");

    for(int j=0; j<PROCESS_NUM; j++){
        sumwt+=cloneJobQueue[j]->wt;
        sumrt+=cloneJobQueue[j]->rt;
        sumtt+=cloneJobQueue[j]->tt + cloneJobQueue[j]->wt; //tt는 running state일 때만 더해줬으므로 ready queue에 있을 때도 지금 더해줌
    }


    avgwt=(float)sumwt/PROCESS_NUM;
    avgrt=(float)sumrt/PROCESS_NUM;
    avgtt=(float)sumtt/PROCESS_NUM;


    printf("Average cpu usage : %.2f%%\n",((time-idle)/(float)time)*100);
    printf("Average waiting time : %.1f\n",avgwt);
    printf("Average response time : %.1f\n",avgrt);
    printf("Average turnaround time : %.1f\n",avgtt);

    fprintf(outFile,"Average cpu usage : %.2f%%\n",((time-idle)/(float)time)*100);
    fprintf(outFile,"Average waiting time : %.1f\n",avgwt);
    fprintf(outFile,"Average response time : %.1f\n",avgrt);
    fprintf(outFile,"Average turnaround time : %.1f\n",avgtt);


}
void apply_aging(float alpha){
    PCBpointer ptr=NULL;
    if(rq.head!=NULL){
        ptr=rq.head;
        while(ptr!=NULL){
            //소수점 첫째 자리에서 반올림
            ptr->priority=ptr->priority +(int)((ptr->wt*alpha)+0.5);
            ptr=ptr->next;
        }
    }
    
}

void insert_by_priority(PCBpointer newProcess){
    PCBpointer ptr = rq.head;
    PCBpointer prev_ptr = NULL;
    while(ptr){
        if(newProcess->priority > ptr->priority){
            newProcess->next=ptr;
            if(prev_ptr==NULL){
                rq.head=newProcess;
            }
            else{
                prev_ptr->next=newProcess;
            }
            break;
        }
        else if(ptr->next==NULL){
            ptr->next=newProcess;
            rq.tail=newProcess;
            break;
        }
        prev_ptr=ptr;
        ptr=ptr->next;
    }
}
// PCB들의 next관계를 유지하기 위해 데이터들만 변경
void  swap_PCB (PCBpointer a, PCBpointer b)
{
    PCBpointer temp=(PCBpointer)malloc(sizeof(struct PCB));
    temp->pid = a->pid;
    temp->priority = a->priority;
    temp->arrival_time = a->arrival_time;
    temp->burst_time = a->burst_time;
    temp->wt = a->wt;
    temp->rt = a->rt;
    temp->tt = a->tt;
    temp->tq = a->tq;

    a->pid = b->pid;
    a->priority = b->priority;
    a->arrival_time = b->arrival_time;
    a->burst_time = b->burst_time;
    a->wt = b->wt;
    a->rt = b->rt;
    a->tt = b->tt;
    a->tq = b->tq;
    
    b->pid=temp->pid;
    b->priority = temp->priority;
    b->arrival_time = temp->arrival_time;
    b->burst_time = temp->burst_time;
    b->wt = temp->wt;
    b->rt = temp->rt;
    b->tt = temp->tt;
    b->tq = temp->tq;

}

void sort_ready_queue()
{
    PCBpointer auxiliary = rq.head;
    PCBpointer temp = NULL;
    PCBpointer node = NULL;

    while(auxiliary != NULL)
    {
        node = auxiliary;
        temp = auxiliary->next;
        while(temp != NULL)
        {
            if (node->priority < temp->priority)
            {
                node = temp;
            }
            temp = temp->next;
        }
        if (auxiliary->priority < node->priority)
        {
            
            swap_PCB(auxiliary, node);
        }
        auxiliary = auxiliary->next;
    }
}

void Priority_algorithm(float alpha){
    if(rq.head!=NULL){

        apply_aging(alpha);
    
        sort_ready_queue();
        //runningProcess와 importantProcess 우선순위 비교 로직
        if(runningProcess!=NULL){

            if(runningProcess->priority <= rq.head->priority){
                if(runningProcess->priority == rq.head->priority){
                    //priority가 같을경우 먼저 도착한 프로세스를 수행
                    if(runningProcess->arrival_time > rq.head->arrival_time){
                        insert_by_priority(runningProcess);
                        runningProcess=NULL;

                        ready_queue_num++;
                    }
                }
                else{
                    insert_by_priority(runningProcess);
                    runningProcess=NULL;
                    
                    ready_queue_num++;

                }
            
            
            }
        }
        

    }
    
    

}
void Priority_simulator(float alpha){
    
    float avgwt=0.0;
    float avgrt=0.0;
    float avgtt=0.0;

    int sumwt=0,sumrt=0,sumtt=0;
    int idle=0;

    int i=0; //index


    rq.head=rq.tail=NULL;
 
    //context switch를 비교하기위한 변수
    prevRunningProcess=NULL;

    printf("\nScheduling : Preemptive Priority Scheduling with Aging\n");
    printf("=================================================\n");

    fprintf(outFile,"\nScheduling : Preemptive Priority Scheduling with Aging\n");
    fprintf(outFile,"=================================================\n");

    while(!(clone_job_queue_num==0 && ready_queue_num==0 && runningProcess==NULL)){
        //job queue에서 arriveTime이 time과 일치하면 readyQueue로 넣어주기
            //ready queue num이 0이면 PCB를 readyQueue의 head,tail로 연결
            // ready queue num이 0이 아니면 새로운 process를 PCB의 next로 추가, tail에도 추가 
            //job queue num --, ready queue num ++
        

        //도착시간이 같은 프로세스가 있을 수 있으므로 while문
        while(i<PROCESS_NUM && cloneJobQueue[i]->arrival_time==time){
            printf("<time %d> [new arrival] process %d\n",time,cloneJobQueue[i]->pid);
            fprintf(outFile,"<time %d> [new arrival] process %d\n",time,cloneJobQueue[i]->pid);

            if(ready_queue_num==0){
                rq.head=cloneJobQueue[i];
                rq.tail=cloneJobQueue[i];
            }
            else{
                insert_by_priority(cloneJobQueue[i]);
                
            }
            i++;
            clone_job_queue_num--;
            ready_queue_num++;
        }

        Priority_algorithm(alpha);

        //runningProcess없다면 그리고 ready queue에 프로세스 있다면
            //->ready queue에 있는거 running으로 보내주기
            //running으로 가는 프로세스의 next를 ready queue의 head로 보내주고 next=null
            //ready queue num -- , process is running 출력
            //burstTime --, turnaroundtime++
            //넣어줄게 없으면 system is idle 출력, idle++
        
        
        if(runningProcess==NULL){
            if(rq.head!=NULL){
                runningProcess=rq.head;
                if(runningProcess->rt==-1)   runningProcess->rt=runningProcess->wt;
                //runningProcess가 NULL인데 prevRunningProcess도 NULL이라는 뜻은 runningProcess가 처음 들어온 시점이라는 의미
                if(prevRunningProcess==NULL)    prevRunningProcess=runningProcess; 

            
                if(rq.head->next!=NULL){
                    rq.head=rq.head->next;
                    runningProcess->next=NULL;
                }
                else{
                    rq.head=NULL;
                }

                if((prevRunningProcess!=NULL && runningProcess!=NULL)){
                    if(prevRunningProcess!=runningProcess){
                        printf("------------------------------------ (Context-Switch)\n");
                        fprintf(outFile,"------------------------------------ (Context-Switch)\n");
                    }
                }

                printf("<time %d> process %d is running\n",time,runningProcess->pid);
                fprintf(outFile,"<time %d> process %d is running\n",time,runningProcess->pid);
                
                ready_queue_num--;
                runningProcess->burst_time--;
                runningProcess->tt++;   //여기에서 tt는 running state일 때의 시간
                
            
                if(runningProcess->burst_time==0){
                    printf("<time %d> process %d is finished\n",time,runningProcess->pid);
                    fprintf(outFile,"<time %d> process %d is finished\n",time,runningProcess->pid);
                    runningProcess=NULL;
                }
                    
                
            }
            else{
                printf("<time %d> ---- system is idle ----\n",time);
                fprintf(outFile,"<time %d> ---- system is idle ----\n",time);
                idle++;

                //runningProcess가 없고, rq.head도 없는상태이므로 prevRunningProcess도 null로 초기화
                if(rq.head==NULL)    prevRunningProcess=NULL;
            }

            
        }

        //runningProcess 기존에 있다면
            // prorocess is running 출력
            // runningProcess의 burstTime --, turnaroundtime ++
            // 만약 runningProcess의 burstTime이 0이 되면 runningProcess=NULL, 

        else if(runningProcess!=NULL){
        
            
            printf("<time %d> process %d is running\n",time,runningProcess->pid);
            fprintf(outFile,"<time %d> process %d is running\n",time,runningProcess->pid);

            runningProcess->burst_time--;
            runningProcess->tt++;   //여기에서 tt는 running state일 때의 시간

            if(runningProcess->burst_time==0){
                printf("<time %d> process %d is finished\n",time,runningProcess->pid);
                fprintf(outFile,"<time %d> process %d is finished\n",time,runningProcess->pid);    
                runningProcess=NULL;
            }
            

            
        }

        //readyQueue에(프로세스가 있다면) 있는 프로세스들 watingTime++
        add_waiting_time();

        //time++
        time++;
    }
    printf("<time %d> all processes finish\n",time);
    printf("=================================================\n");

    fprintf(outFile,"<time %d> all processes finish\n",time);
    fprintf(outFile,"=================================================\n");

    for(int j=0; j<PROCESS_NUM; j++){
        sumwt+=cloneJobQueue[j]->wt;
        sumrt+=cloneJobQueue[j]->rt;
        sumtt+=cloneJobQueue[j]->tt + cloneJobQueue[j]->wt; //tt는 running state일 때만 더해줬으므로 ready queue에 있을 때도 지금 더해줌
    }


    avgwt=(float)sumwt/PROCESS_NUM;
    avgrt=(float)sumrt/PROCESS_NUM;
    avgtt=(float)sumtt/PROCESS_NUM;


    printf("Average cpu usage : %.2f%%\n",((time-idle)/(float)time)*100);
    printf("Average waiting time : %.1f\n",avgwt);
    printf("Average response time : %.1f\n",avgrt);
    printf("Average turnaround time : %.1f\n",avgtt);
    
    fprintf(outFile,"Average cpu usage : %.2f%%\n",((time-idle)/(float)time)*100);
    fprintf(outFile,"Average waiting time : %.1f\n",avgwt);
    fprintf(outFile,"Average response time : %.1f\n",avgrt);
    fprintf(outFile,"Average turnaround time : %.1f\n",avgtt);

}


void clear_clone_job_queue(){
    for(int i=0; i<PROCESS_NUM; i++){
        free(cloneJobQueue[i]);
        cloneJobQueue[i]=NULL;
    }
}

void clear_job_queue(){
    for(int i=0; i<PROCESS_NUM; i++){
        free(jobQueue[i]);
        jobQueue[i]=NULL;
    }
}


int main(int argc, char *argv[]){

    read_process_list(argv[1]);
    insert_job_queue();
    sortByat(PROCESS_NUM);
    insert_clone_job_queue();

    outFile = fopen(argv[2],"w");
    FCFS();

    clear_clone_job_queue();
    insert_clone_job_queue();


    clear_time();
    RR_simulator(atoi(argv[3]));

    clear_clone_job_queue();
    insert_clone_job_queue();


    clear_time();
    Priority_simulator(atof(argv[4]));
    
    fclose(outFile);

    //메모리 회수
    clear_clone_job_queue();
    clear_job_queue();


    return 0;
}





