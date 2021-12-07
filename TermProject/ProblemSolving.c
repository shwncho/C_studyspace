#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUFFER_LEN 256
#define MAX_PERSONAL 30

struct Person
{
	int tag;
	char dateRegistered[11];
	char feePaid[4];
	char name[25];
	char organization[35];
	int age;
	char job[15];
};


struct Node
{
	struct Person data;
	struct Node* next;
};
void ResetNode(struct Node* node);
char* GetLastName(struct Person* person);
int g_count = 0;
struct Person* g_array = NULL;
struct Node* g_list = NULL;

void CheckSumArray()
{
	char* name;
	unsigned int checksum=0;
	unsigned int sum=0;
	unsigned int arr[10];
	for (int i = 0; i < MAX_PERSONAL; i++) {
		name = GetLastName(g_array + i);
		for (int j = 0; j < strlen(name); j++) {
			sum += *(name + j);
		}
		checksum ^= sum;
	}


}


void DisplayList(struct Node* head)
{
	struct Node* ptr = head;
	while (ptr != NULL)
	{
		printf("%d %s %s %s %d %s %s\n", ptr->data.tag, ptr->data.dateRegistered, ptr->data.feePaid, ptr->data.name, ptr->data.age, ptr->data.organization, ptr->data.job);

		ptr = ptr->next;
	}
	printf("[NULL]\n");
}
void PrintList(struct Person* arr, int n) 
{

	printf("Print Array\n");
	for (int i = 0; i < n; i++) {
		printf("%d %s %s %s %d %s %s\n", arr[i].tag, arr[i].dateRegistered, arr[i].feePaid, arr[i].name, arr[i].age, arr[i].organization, arr[i].job);

	}

}



void ResetPerson(struct Person* person)
{
	if (person)
	{
		memset(person, 0, sizeof(struct Person));
	}
}

void RemoveChoiArray() 
{
	const char* name = "Choi";
	for (int i = 0; i < MAX_PERSONAL; i++) {
		if (strcmp(name, GetLastName(g_array+i)) == 0) {
			ResetPerson(g_array+i);
		}
	}
	
	
}
void RemoveChoiList() 
{
	const char* name = "Choi";
	struct Node* node = g_list;
	while (node)
	{
		char* lastName = GetLastName(&(node->data));
		if (strcmp(lastName, name) == 0)
		{
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
}

void SetTag(struct Person* person, const char* text)
{
	if (person && text)
		person->tag = atoi(text);
}

void SetDate(struct Person* person, const char* text)
{
	if (person && text)
		strncpy(person->dateRegistered, text, sizeof(person->dateRegistered));
}

void SetFeePaid(struct Person* person, const char* text)
{
	if (person && text)
		strncpy(person->feePaid, text, sizeof(person->feePaid));
}

void SetName(struct Person* person, const char* text)
{
	if (person && text)
		strncpy(person->name, text, sizeof(person->name));
}

void SetOrganizations(struct Person* person, const char* text)
{
	if (person && text)
		strncpy(person->organization, text, sizeof(person->organization));
}

void SetAge(struct Person* person, const char* text)
{
	if (person && text)
		person->age = atoi(text);
}

void SetJob(struct Person* person, const char* text)
{
	if (person && text)
		strncpy(person->job, text, sizeof(person->job));
}


void copy_personal(const struct Person* src, struct Person* dest)
{
	dest->tag = src->tag;
	strcpy(dest->dateRegistered, src->dateRegistered);
	strcpy(dest->feePaid, src->feePaid);
	strcpy(dest->name, src->name);
	dest->age = src->age;
	strcpy(dest->organization, src->organization);
	strcpy(dest->job, src->job);

	

}
char* GetLastName(struct Person* person)
{
	char* name = (char*)malloc(sizeof(person->name) + 1);
	strncpy(name, person->name, sizeof(person->name));
	char* firstName = strtok(name, " ");
	char* lastName = strtok(NULL, " ");

	char* ret = (char*)malloc(strlen(lastName) + 1);
	strncpy(ret, lastName, strlen(lastName) + 1);

	free(name);

	return ret;
}

void PrintPerson(struct Person* person)
{
	if (!person)
		return;

	printf("%d %s %s %s %d %s %s\n", person->tag, person->dateRegistered, person->feePaid ? "yes" : "no", person->name, person->age, person->organization, person->job);
}

void ResetNode(struct Node* node)
{
	if (node)
	{
		memset(node, 0, sizeof(struct Node));
	}
}

struct Node* AddNode(struct Node* node)
{
	struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
	ResetNode(newNode);
	if (node)
		node->next = newNode;

	return newNode;
}

char** ReadFile(const char* path, int* count)
{
	char buffer[BUFFER_LEN];
	FILE* file = fopen(path, "r");
	*count = 0;
	while (fgets(buffer, BUFFER_LEN, file)) {
		(*count)++;
	}

	fseek(file, 0, SEEK_SET);

	char** lines = (char**)malloc(sizeof(char**) * (*count));
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

struct Person ReadLine(const char* text)
{
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

struct Person* SetUpArray(char** lines, int count)
{
	struct Person* persons = (struct Person*)malloc(sizeof(struct Person) * count);

	for (int i = 0; i < count; i++)
	{
		struct Person person = ReadLine(lines[i]);
		memcpy(&persons[i], &person, sizeof(struct Person));
	}

	return persons;
}

struct Node* SetUpLinkedList(char** lines, int count)
{
	struct Person* persons = (struct Person*)malloc(sizeof(struct Person) * count);

	struct Node* firstNode = AddNode(NULL);
	struct Node* node = firstNode;

	for (int i = 0; i < count; i++)
	{
		if (i != 0)
			node = AddNode(node);

		node->data = ReadLine(lines[i]);
	}
	return firstNode;
}

void SetUp(const char* path)
{
	int count = 0;
	char** lines = ReadFile(path, &count);

	g_count = count;
	g_array = SetUpArray(lines, count);
	g_list = SetUpLinkedList(lines, count);

	for (int i = 0; i < count; i++)
	{
		free(lines[i]);
	}

	free(lines);
}

void Clear()
{
	if (g_array)
		free(g_array);

	if (g_list)
		free(g_list);

	g_count = 0;
}

void SearchNameFromArray(const char* text)								//P1-1
{
	printf("(P1-1 Start)\n");
	for (int i = 0; i < g_count; i++)
	{
		char* lastName = GetLastName(&g_array[i]);
		if (strcmp(lastName, text) == 0)
		{
			PrintPerson(&(g_array[i]));
		}
		free(lastName);
	}
	printf("(P1-1 End)\n\n");
}

void SearchNameFromList(const char* text)								//P1-2
{
	printf("(P1-2 Start)\n");
	struct Node* node = g_list;
	while (node)
	{
		char* lastName = GetLastName(&(node->data));
		if (strcmp(lastName, text) == 0)
		{
			PrintPerson(&(node->data));
		}
		free(lastName);

		node = node->next;
	}
	printf("(P1-2 End)\n\n");
}

void SearchOrganizationFromArray(const char* text)						//P2-1
{
	printf("(P2-1 Start)\n");
	for (int i = 0; i < g_count; i++)
	{
		if (strcmp(g_array[i].organization, text) == 0)
		{
			PrintPerson(&(g_array[i]));
		}
	}
	printf("(P2-1 End)\n\n");
}

void SearchOrganizationFromList(const char* text)						//P2-2
{
	printf("(P2-2 Start)\n");
	struct Node* node = g_list;
	while (node)
	{
		if (strcmp(node->data.organization, text) == 0)
		{
			PrintPerson(&(node->data));
		}

		node = node->next;
	}
	printf("(P2-2 End)\n\n");
}
void decompose_by_age(
	struct Person original[],
	const int num,
	const int age_range,
	struct Person group[],
	int* num_group)
{
	int index = 0;

	for (int i = 0; i < num; i++)
	{
		int generation = (int)(original[i].age / 10) * 10;
		if (generation == age_range)
		{
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
void write_Personal_group(
	FILE* pFile,
	const int age_range,
	const struct Person p[],
	const int num_personal)
{

	fprintf(pFile, "\nAge from %d to %d\n", age_range, age_range + 9);
	fprintf(pFile, "---------------------------\n");
	for (int i = 0; i < num_personal; i++)
	{
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

void WriteSortedData() 
{
	struct Person group_age[4][MAX_PERSONAL];
	int group_count[4];
	int age_range[] = { 10, 20, 30, 40 };
	for (int i = 0; i < 4; i++)
	{
		decompose_by_age(g_array, MAX_PERSONAL, age_range[i], group_age[i], &group_count[i]);
	}
	for (int i = 0; i < 4; i++) {
		selection_sort(group_age[i], group_count[i]);
	}

	char fname[] = "P5-1.txt";
	FILE* pFile = NULL;
	pFile = fopen(fname, "w");
	if (pFile == NULL)
	{
		printf("Error: File Writing Failed: %s\n", fname);
		exit(1);
	}

	for (int i = 0; i < 4; i++)
	{
		write_Personal_group(pFile, age_range[i], group_age[i], group_count[i]);
	}



	fclose(pFile);
}

void Search()
{
	SearchNameFromArray("Choi");
	SearchNameFromList("Choi");

	SearchOrganizationFromArray("Gachon University");
	SearchOrganizationFromList("Gachon University");
}

int main()
{
	const char* path = "registraion_data.txt";
	SetUp(path);
	//Search();
	//WriteSortedData();
	//RemoveChoiArray();
	//RemoveChoiList();
	DisplayList(g_list);

	return 0;
}
