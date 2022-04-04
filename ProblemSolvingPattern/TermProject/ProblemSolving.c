#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUFFER_LEN 256
#define MAX_PERSONAL 30

struct Person {
    int tag;
    char dateRegistered[11];
    char feePaid[4];
    char name[25];
    char organization[35];
    int age;
    char job[15];
};


struct Node {
    struct Person data;
    struct Node* next;
};

int g_count = 0;
struct Person* g_array = NULL;
struct Person* copy_array = NULL;
struct Node* g_list = NULL;
struct Person* c_array = NULL;
int count = 0;


void copy_personal(const struct Person*, struct Person*);
char* GetLastName(struct Person* person);
void ResetNode(struct Node* node);
void Array_To_LinkedList();
void decompose_by_age(struct Person original[], const int num, const int age_range, struct Person group[], int* num_group);
void selection_sort(struct Person p[], int n);
void write_Personal_group(FILE* pFile, const int age_range, const struct Person p[], const int num_personal);
unsigned int CheckSumArray(struct Person* c_arr);


void SetTag(struct Person* person, const char* text) {
    if (person && text)
        person->tag = atoi(text);
}

void SetDate(struct Person* person, const char* text) {
    if (person && text)
        strncpy(person->dateRegistered, text, sizeof(person->dateRegistered));
}

void SetFeePaid(struct Person* person, const char* text) {
    if (person && text)
        strncpy(person->feePaid, text, sizeof(person->feePaid));
}

void SetName(struct Person* person, const char* text) {
    if (person && text)
        strncpy(person->name, text, sizeof(person->name));
}

void SetOrganizations(struct Person* person, const char* text) {
    if (person && text)
        strncpy(person->organization, text, sizeof(person->organization));
}

void SetAge(struct Person* person, const char* text) {
    if (person && text)
        person->age = atoi(text);
}

void SetJob(struct Person* person, const char* text) {
    if (person && text)
        strncpy(person->job, text, sizeof(person->job));
}


void copy_personal(const struct Person* src, struct Person* dest) {
    dest->tag = src->tag;
    strcpy(dest->dateRegistered, src->dateRegistered);
    strcpy(dest->feePaid, src->feePaid);
    strcpy(dest->name, src->name);
    dest->age = src->age;
    strcpy(dest->organization, src->organization);
    strcpy(dest->job, src->job);
}

void PrintPerson(struct Person* person) {
    if (!person)
        return;

    printf("%d %s %s %s %d %s %s\n", person->tag, person->dateRegistered, person->feePaid, person->name, person->age, person->organization, person->job);
}

void ResetNode(struct Node* node) {
    if (node) {
        memset(node, 0, sizeof(struct Node));
    }
}

struct Node* AddNode(struct Node* node) {
    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
    ResetNode(newNode);

    if (node)
        node->next = newNode;

    return newNode;
}

char** ReadFile(const char* path) {
    char buffer[BUFFER_LEN];
    FILE* file = fopen(path, "r");
    count = 0;

    while (fgets(buffer, BUFFER_LEN, file)) {
        (count)++;
    }

    fseek(file, 0, SEEK_SET);

    char** lines = (char**)malloc(sizeof(char**) * (count));
    int index = 0;

    while (fgets(buffer, BUFFER_LEN, file)) {
        char* line = (char*)malloc(BUFFER_LEN);
        memcpy(line, buffer, BUFFER_LEN);
        lines[index] = line;

        index++;
    }

    fclose(file);

    return lines;
}

struct Person ReadLine(const char* text) {
    struct Person p;

    char tmp[BUFFER_LEN];
    strncpy(tmp, text, BUFFER_LEN);

    SetTag(&p, strtok(tmp, "/"));
    SetDate(&p, strtok(NULL, "/"));
    SetFeePaid(&p, strtok(NULL, "/"));
    SetName(&p, strtok(NULL, "/"));
    SetAge(&p, strtok(NULL, "/"));
    SetOrganizations(&p, strtok(NULL, "/"));
    SetJob(&p, strtok(NULL, "\n"));

    return p;
}

struct Person* SetUpArray(char** lines) {
    struct Person* persons = (struct Person*)malloc(sizeof(struct Person) * count);

    for (int i = 0; i < count; i++) {
        struct Person person = ReadLine(lines[i]);
        memcpy(&persons[i], &person, sizeof(struct Person));
    }

    return persons;
}

struct Node* SetUpLinkedList(char** lines) {
    struct Person* persons = (struct Person*)malloc(sizeof(struct Person) * count);

    struct Node* firstNode = AddNode(NULL);
    struct Node* node = firstNode;

    for (int i = 0; i < count; i++) {
        if (i != 0)
            node = AddNode(node);

        node->data = ReadLine(lines[i]);
    }
    return firstNode;
}

void SetUp(const char* path) {
    char** lines = ReadFile(path);

    g_count = count;
    g_array = SetUpArray(lines);
    g_list = SetUpLinkedList(lines);

    for (int i = 0; i < count; i++) {
        free(lines[i]);
    }

    free(lines);
}

void Clear() {
    if (g_array)
        free(g_array);

    if (g_list)
        free(g_list);

    g_count = 0;
}

void SearchNameFromArray(const char* text) {                            
    printf("(P1-1 Start)\n");
    for (int i = 0; i < g_count; i++) {
        char* lastName = GetLastName(&g_array[i]);
        if (strcmp(lastName, text) == 0)
        {
            PrintPerson(&(g_array[i]));
        }
        free(lastName);
    }
    printf("(P1-1 End)\n\n");
}

void SearchNameFromList(const char* text) {                             
    printf("(P1-2 Start)\n");
    struct Node* node = g_list;
    while (node) {
        char* lastName = GetLastName(&(node->data));
        if (strcmp(lastName, text) == 0) {
            PrintPerson(&(node->data));
        }
        free(lastName);

        node = node->next;
    }
    printf("(P1-2 End)\n\n");
}

void SearchOrganizationFromArray(const char* text) {                       
    printf("(P2-1 Start)\n");
    for (int i = 0; i < g_count; i++) {
        if (strcmp(g_array[i].organization, text) == 0) {
            PrintPerson(&(g_array[i]));
        }
    }
    printf("(P2-1 End)\n\n");
}

void SearchOrganizationFromList(const char* text) {                   
    printf("(P2-2 Start)\n");
    struct Node* node = g_list;
    while (node) {
        if (strcmp(node->data.organization, text) == 0) {
            PrintPerson(&(node->data));
        }

        node = node->next;
    }
    printf("(P2-2 End)\n\n");
}

void Search() {
    SearchNameFromArray("Choi");
    SearchNameFromList("Choi");

    SearchOrganizationFromArray("Gachon University");
    SearchOrganizationFromList("Gachon University");
}

void changearray(struct Person* dist, struct Person* src) {
    struct Person* temp = (struct Person*)malloc(sizeof(struct Person));

    temp->tag = src->tag;
    src->tag = dist->tag;
    dist->tag = temp->tag;

    strncpy(temp->dateRegistered, src->dateRegistered, sizeof(src->dateRegistered));
    strncpy(src->dateRegistered, dist->dateRegistered, sizeof(src->dateRegistered));
    strncpy(dist->dateRegistered, temp->dateRegistered, sizeof(src->dateRegistered));

    strncpy(temp->feePaid, src->feePaid, sizeof(src->feePaid));
    strncpy(src->feePaid, dist->feePaid, sizeof(src->feePaid));
    strncpy(dist->feePaid, temp->feePaid, sizeof(src->feePaid));

    strncpy(temp->name, src->name, sizeof(src->name));
    strncpy(src->name, dist->name, sizeof(src->name));
    strncpy(dist->name, temp->name, sizeof(src->name));

    strncpy(temp->organization, src->organization, sizeof(src->organization));
    strncpy(src->organization, dist->organization, sizeof(src->organization));
    strncpy(dist->organization, temp->organization, sizeof(src->organization));

    temp->age = src->age;
    src->age = dist->age;
    dist->age = temp->age;

    strncpy(temp->job, src->job, sizeof(src->job));
    strncpy(src->job, dist->job, sizeof(src->job));
    strncpy(dist->job, temp->job, sizeof(src->job));
}

void Sort_By_Tag() {                                                                       
    struct Person* temp = (struct Person*)malloc(sizeof(struct Person) * count);
    int i, j;
    copy_array = g_array;

    for (i = 0; i < count; i++) {
        for (j = 0; j < count - 1; j++) {
            if (copy_array[j].tag > copy_array[j + 1].tag) {
                changearray(&copy_array[j + 1], &copy_array[j]);
            }
        }
    }

    printf("(P3-1 Start)\n");
    for (i = 0; i < count; i++) {
        PrintPerson(&copy_array[i]);
    }
    printf("(P3-1 End)\n\n");
    free(temp);
    Array_To_LinkedList();
}

void Array_To_LinkedList() {                                         
    struct Person temp;
    int i;

    struct Node* head = AddNode(NULL);
    struct Node* node = AddNode(NULL);
    head->next = node;

    for (int i = 0; i < count; i++)
    {
        copy_personal(copy_array++, &temp);

        if (i != 0)
            node = AddNode(node);

        node->data = temp;
    }

    node = head->next;

    printf("(P4-1 Start)\n");
    while (node) {
        PrintPerson(&(node->data));
        node = node->next;
    }
    printf("(P4-1 End)\n\n");
}


void WriteSortedData() {                                                                            
    struct Person group_age[4][MAX_PERSONAL];
    int group_count[4];
    int age_range[] = { 10, 20, 30, 40 };

    printf("(P5-1 Start)\n");


    for (int i = 0; i < 4; i++) {
        decompose_by_age(g_array, MAX_PERSONAL, age_range[i], group_age[i], &group_count[i]);
    }

    for (int i = 0; i < 4; i++) {
        selection_sort(group_age[i], group_count[i]);
    }

    char fname[] = "P5-1.txt";
    FILE* pFile = NULL;
    pFile = fopen(fname, "w");

    if (pFile == NULL) {
        printf("Error: File Writing Failed: %s\n", fname);
        exit(1);
    }

    for (int i = 0; i < 4; i++) {
        write_Personal_group(pFile, age_range[i], group_age[i], group_count[i]);
        printf("'P5-1.txt' file is being written . . . (%d/4)\n", i+1);
    }

    printf("(P5-1 End)\n\n");

    fclose(pFile);
}

void decompose_by_age(struct Person original[], const int num, const int age_range, struct Person group[], int* num_group) {
    int index = 0;

    for (int i = 0; i < num; i++) {
        int generation = (int)(original[i].age / 10) * 10;

        if (generation == age_range) {
            copy_personal(&original[i], &group[index]);
            index++;
        }
    }
    *num_group = index;
}

void selection_sort(struct Person p[], int n) {
    int i, j, min;
    struct Person t;
    for (i = 0; i < n - 1; i++) {
        min = i;
        for (j = i + 1; j < n; j++) {
            if (p[min].age > p[j].age) {
                min = j;
            }
        }
        t = p[i];
        p[i] = p[min];
        p[min] = t;
    }
}

void write_Personal_group(FILE* pFile, const int age_range, const struct Person p[], const int num_personal) {
    fprintf(pFile, "\nAge from %d to %d\n", age_range, age_range + 9);
    fprintf(pFile, "---------------------------\n");

    for (int i = 0; i < num_personal; i++) {
        fprintf(pFile, "%d %s %s %s %d %s %s\n",
            p[i].tag,
            p[i].dateRegistered,
            p[i].feePaid,
            p[i].name,
            p[i].age,
            p[i].organization,
            p[i].job);
    }
}

void DisplayList(struct Node* head) {
    struct Node* ptr = head;

    printf("Print Linkedlist\n");
    
    while (ptr != NULL) {
        printf("%d %s %s %s %d %s %s\n", ptr->data.tag, ptr->data.dateRegistered, ptr->data.feePaid, ptr->data.name, ptr->data.age, ptr->data.organization, ptr->data.job);

        ptr = ptr->next;
    }

    printf("[NULL]\n\n");
}

void PrintList(struct Person* arr, int n) {
    printf("Print Array\n");

    for (int i = 0; i < n; i++) {
        printf("%d %s %s %s %d %s %s\n", arr[i].tag, arr[i].dateRegistered, arr[i].feePaid, arr[i].name, arr[i].age, arr[i].organization, arr[i].job);
    }

    printf("Print Array End\n\n");
}

char* GetLastName(struct Person* person) {
    char* name = (char*)malloc(sizeof(person->name) + 1);
    strncpy(name, person->name, sizeof(person->name));
    char* firstName = strtok(name, " ");
    char* lastName = strtok(NULL, " ");

    char* ret = (char*)malloc(strlen(lastName) + 1);
    strncpy(ret, lastName, strlen(lastName) + 1);

    free(name);

    return ret;
}

void ResetPerson(struct Person* person) {
    if (person) {
        memset(person, 0, sizeof(struct Person));
    }
}

void RemoveChoiArray() {                                            
    const char* name = "Choi";

    printf("(P6-1 Start)\n");

    for (int i = 0; i < MAX_PERSONAL; i++) {
        if (strcmp(name, GetLastName(g_array + i)) == 0) {
            ResetPerson(g_array + i);
        }
    }
   
    PrintList(g_array, MAX_PERSONAL);
    
    printf("(P6-1 End)\n\n");
}
void RemoveChoiList() {                                           
    const char* name = "Choi";
    struct Node* node = g_list;
    struct Node* firstnode = g_list;

    printf("(P6-2 Start)\n");

    while (node) {
        char* lastName = GetLastName(&(node->data));

        if (strcmp(lastName, name) == 0) {
            node->data.tag = 0;
            strcpy(node->data.dateRegistered, " ");
            strcpy(node->data.feePaid, " ");
            strcpy(node->data.name, " ");
            strcpy(node->data.organization, " ");
            node->data.age = 0;
            strcpy(node->data.job, " ");
        }
        free(lastName);

        node = node->next;
    }

    DisplayList(firstnode);


   printf("(P6-2 End)\n\n");
}

void SortArray(struct Person* arr) {
    int i, j;

    for (i = 0; i < count; i++) {
        for (j = 0; j < count - 1; j++) {
            if (arr[j].tag > arr[j + 1].tag) {
                changearray(&arr[j + 1], &arr[j]);
            }
        }
    }
}

void AddArray(struct Person* arr, int tag) {
    int i, j;

    for (i = 0; i < count; i++) {
        if (arr[i].tag == 0) {
            g_array[i].tag = tag;
            strcpy(g_array[i].dateRegistered, "2021-05-05");
            strcpy(g_array[i].feePaid, "yes");
            strcpy(g_array[i].name, "Gildong Paik");
            strcpy(g_array[i].organization, "Gachon University");
            g_array[i].age = 35;
            strcpy(g_array[i].job, "student"); 
            
            break;
        }
    }
}    

void AddPaikArray() {                                                                   
    int i, j;
    int flag = 0;
    int paik_tag = -1;

    SortArray(g_array);
    
    for (i = 0; i < count; i++) {
        for (j = 0; j < count; j++) {
            if (g_array[j].tag != i + 1)
                flag++;
        }
        if (flag == count) {
            paik_tag = i + 1;
            break;
        }
        flag = 0;
    }

    if (paik_tag != -1)
        AddArray(g_array, paik_tag);

    SortArray(g_array);

    printf("(P7-1 Start)\n");
    for (i = 0; i < count; i++) {
        PrintPerson(g_array + i);
    }
    printf("(P7-1 End)\n\n");
}

void swapNodedata(struct Node* cur, struct Node* nextcur) {
    struct Person temp;

    temp = cur->data;
    cur->data = nextcur->data;
    nextcur->data = temp;
}

void SortList(struct Node* list) {
    struct Node* base, * temp;
    struct Person data, nextdata;
    int i, j;
    base = list;

    for (i = 0; i < count; i++)  {
        if (base->next == NULL) break;

        for (j = 0; j < count - 1 - i; j++) {
            data = base->data;
            nextdata = base->next->data;

            if (data.tag > nextdata.tag) {
                swapNodedata(base, base->next);
            }
            base = base->next;
        }
        base = list->next;
    }
}

int FindPaikTag(struct Node* list) {
    int i = 1 , flag = 0, tag = -1;
    struct Node* base, *temp;
    struct Person data;
    base = list;
    temp = list;

    while (base->next != NULL) {
        while (temp->next != NULL) {
            data = temp->data;
            if (data.tag != i) {
                flag++;
            }
            temp = temp->next;
        }

        if (flag == count - 1) {
            tag = i;
            return tag;
        }
        base = base->next;
        flag = 0;
        i++;
    }
    return tag;
}

void AddList(struct Node* list, struct Person data) {
    struct Node* base;
    struct Person temp, nexttemp;
    base = list;
    int i;

    while (base->next != NULL) {
        temp = base->data;
        nexttemp = base->next->data;
    
        if (temp.tag < nexttemp.tag) {
            base->data = data;
            break;
        }
        base = base->next;
    }
}

void AddPaikList() {                                                                           
    int i, j;
    struct Person temp = { -1, "2021-05-05", "yes", "Gildong Paik", "Gachon university", 35, "student" };

    SortList(g_list);

    temp.tag = FindPaikTag(g_list);

    AddList(g_list, temp);
    
    SortList(g_list);

    printf("(P7-2 Start)\n");
    DisplayList(g_list);
    printf("(P7-2 End)\n\n");
}

void CheckSumArrayChecking() {                                                                  
    struct Person* c_array = (struct Person*)malloc(sizeof(struct Person) * g_count);

    printf("(P8-1 Start)\n");

    for (int i = 0; i < MAX_PERSONAL; i++) {
        copy_personal(g_array + i, c_array + i);
    }

    if (CheckSumArray(g_array) == CheckSumArray(c_array))
        printf("Same checksum\n");
    else
        printf("Different checksum\n");

        printf("(P8-1 End)\n\n");
}

unsigned int CheckSumArray(struct Person* c_arr) {
    char* name;
    unsigned int checksum[MAX_PERSONAL] = { 0, };
    unsigned int allchecksum = 0;
    unsigned int sum = 0;

    for (int i = 0; i < MAX_PERSONAL; i++) {
        name = GetLastName(c_arr + i);

        for (int j = 0; j < strlen(name); j++) {
            sum += *(name + j);
        }

        checksum[i] ^= sum;
    }

    for (int i = 0; i < MAX_PERSONAL; i++) {
        allchecksum += checksum[i];
    }

    return allchecksum;
}

unsigned int CheckSumList(struct Node* c_li) {
    char* name;
    unsigned int checksum[MAX_PERSONAL] = { 0, };
    unsigned int allchecksum = 0;
    unsigned int sum = 0;


    for (int i = 0; i < MAX_PERSONAL; i++) {
        name = GetLastName(&(c_li->data));

        for (int j = 0; j < strlen(name); j++) {
            sum += *(name + j);

        }

        checksum[i] ^= sum;
        c_li = c_li->next;
    }

    for (int i = 0; i < MAX_PERSONAL; i++) {
        allchecksum += checksum[i];
    }

    return allchecksum;

}

void CheckSumListChecking() {                                                                  
    struct Node* c_list = g_list;
    printf("(P8-2 Start)\n");

    if (CheckSumList(g_list) == CheckSumList(c_list))
        printf("Same checksum\n");
    else
        printf("Different checksum\n");

    printf("(P8-2 End)\n\n");
}

void CompareCheckSum() {                                                                      

    printf("(P8-3 Start)\n");


    if (CheckSumList(g_list) == CheckSumArray(g_array))
        printf("Same checksum\n");
    else
        printf("Different checksum\n");

    printf("(P8-3 End)\n\n");


}

int main() {
    const char* path = "registraion_data.txt";
    SetUp(path); 

    //Search();                     //P1-1, P1-2, P2-1, P2-2
    //Sort_By_Tag();                //P3-1, P4-1 
    //WriteSortedData();            //P5-1 

    //RemoveChoiArray();            //P6-1 
    //RemoveChoiList();             //P6-2 
    //AddPaikArray();               //P7-1 
    //AddPaikList();               //P7-2 

    //CheckSumArrayChecking();     //P8-1 
    //CheckSumListChecking();     //P8-2 
    //CompareCheckSum();          //P8-3 
    
    return 0;
}           