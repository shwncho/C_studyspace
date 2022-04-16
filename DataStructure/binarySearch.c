#include<stdio.h>

int searchKey(int arr[], int key, int len){
    int location=0;
    int lt=0,rt=len;
    int mid;

    while(lt<=rt){
        mid=(lt+rt)/2;
        if(arr[mid]==key){
            location=mid;
            return location;
        }
        else if(arr[mid]>key){
            rt=mid-1;
        }
        else{
            lt=mid+1;
        }
    }


    return -1;  // 못찾았을경우
}






int main(void){

    int arr[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    int len = sizeof(arr) / sizeof(int);
    int founded;
    founded=searchKey(arr,12,len);
    if(founded!=-1) printf("key's index in arr[] is:%d ", founded);
    else{
        printf("Not founded key");
    }

}