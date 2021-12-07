#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUFFER_LEN 256

typedef struct Person
{
	int tag;
	char dateRegistered[11];
	int feePaid;
	char name[25];
	char organization[35];
	int age;
	char job[15];
} Person;

typedef struct Node
{
	struct Person data;
	struct Node* next;
} Node;

int g_count = 0;
struct Person* g_array = NULL;
struct Node* g_list = NULL;

void ResetPerson(Person* person)
{
	if (person)
	{
		memset(person, 0, sizeof(Person));
	}
}

void SetTag(Person* person, const char* text)
{
	if (person && text)
		person->tag = atoi(text);
}

void SetDate(Person* person, const char* text)
{
	if (person && text)
		strncpy(person->dateRegistered, text, sizeof(person->dateRegistered));
}

void SetFeePaid(Person* person, const char* text)
{
	if (person && text)
		person->feePaid = (strcmp(text, "yes") == 0);
}

void SetName(Person* person, const char* text)
{
	if (person && text)
		strncpy(person->name, text, sizeof(person->name));
}

void SetOrganization(Person* person, const char* text)
{
	if (person && text)
		strncpy(person->organization, text, sizeof(person->organization));
}

void SetAge(Person* person, const char* text)
{
	if (person && text)
		person->age = atoi(text);
}

void SetJob(Person* person, const char* text)
{
	if (person && text)
		strncpy(person->job, text, sizeof(person->job));
}

char* GetLastName(Person* person)
{
	char* name = (char *)malloc(sizeof(person->name) + 1);
	strncpy(name, person->name, sizeof(person->name));
	char* firstName = strtok(name, " ");
	char* lastName = strtok(NULL, " ");

	char* ret = (char*)malloc(strlen(lastName) + 1);
	strncpy(ret, lastName, strlen(lastName) + 1);

	free(name);

	return ret;
}

void PrintPerson(Person* person)
{
	if (!person)
		return;

	printf("%d %s %s %s %d %s %s\n", person->tag, person->dateRegistered, person->feePaid?"yes":"no", person->name, person->age, person->organization, person->job);
}

void ResetNode(Node* node)
{
	if (node)
	{
		memset(node, 0, sizeof(Node));
	}
}

Node* AddNode(Node* node)
{
	Node* newNode = (Node*)malloc(sizeof(Node));
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

Person ReadLine(const char* text)
{
	Person p;

	char tmp[BUFFER_LEN];
	strncpy(tmp, text, BUFFER_LEN);

	SetTag(&p, strtok(tmp, "/"));
	SetDate(&p, strtok(NULL, "/"));
	SetFeePaid(&p, strtok(NULL, "/"));
	SetName(&p, strtok(NULL, "/"));
	SetAge(&p, strtok(NULL, "/"));
	SetOrganization(&p, strtok(NULL, "/"));
	SetJob(&p, strtok(NULL, "\n"));

	return p;
}

Person* SetUpArray(char** lines, int count)
{
	Person* persons = (Person*)malloc(sizeof(Person) * count);

	for (int i = 0; i < count; i++)
	{
		Person person = ReadLine(lines[i]);
		memcpy(&persons[i], &person, sizeof(Person));
	}

	return persons;
}

Node* SetUpLinkedList(char** lines, int count)
{
	Person* persons = (Person*)malloc(sizeof(Person) * count);

	Node* firstNode = AddNode(NULL);
	Node* node = firstNode;

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
	Node* node = g_list;
	while(node)
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
	Node* node = g_list;
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
	Search();
	Clear();
	return 0;
}
