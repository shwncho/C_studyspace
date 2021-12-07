#define _CRT_SECURE_NO_WARNINGS
#define MAX_PERSONAL 30

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<stdbool.h>

struct PERSON {
	int tag;
	char date[11];
	char fee[4];
	char name[25];
	int age;
	char org[35];
	char job[15];
};

struct Node {
	struct PERSON data;
	struct Node* next;

	
};

void copy_personal(const struct PERSON* src, struct PERSON* dest)
{
	dest->tag = src->tag;
	strcpy(dest->date, src->date);
	strcpy(dest->fee, src->fee);
	strcpy(dest->name,src->name);
	dest->age = src->age;
	strcpy(dest->org, src->org);
	strcpy(dest->job, src->job);

}

//bool read_Personal_File(const char* fname,struct PERSON p[],int* num_personal)
//{
//	FILE* pFile = NULL;
//
//	pFile = fopen(fname, "r");
//	if (pFile == NULL)
//	{
//		printf("Error: File Reading Failed: %s\n", fname);
//		*num_personal = 0;
//		return false;
//	}
//
//	int num = 0;
//	struct PERSON person;
//	while (fscanf(pFile, "%d %s %s %s %d %s %s",
//		&person.tag, &person.date, &person.fee, &person.name, &person.age, &person.org, &person.job) == 7)
//	{
//		copy_personal(&person, &p[num]);
//		num++;
//	}
//
//	fclose(pFile);
//
//	*num_personal = num;
//	return true;
//}

void decompose_by_age(
	struct PERSON original[],
	const int num,
	const int age_range,
	struct PERSON group[],
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


void write_Personal_group(
	FILE* pFile,
	const int age_range,
	const struct PERSON p[],
	const int num_personal)
{

	fprintf(pFile, "\nAge from %d to %d\n", age_range, age_range + 9);
	fprintf(pFile, "---------------------------\n");
	for (int i = 0; i < num_personal; i++)
	{
		fprintf(pFile, "%d %s %s %s %d %s %s\n",
			p[i].tag,
			p[i].date,
			p[i].fee,
			p[i].name,
			p[i].age,
			p[i].org,
			p[i].job);
	}
}

void displayList(struct Node* head)
{
	struct Node* ptr = head;
	while (ptr != NULL)
	{
		printf("%d %s %s %s %d %s %s\n", ptr->data.tag, ptr->data.date, ptr->data.fee, ptr->data.name, ptr->data.age, ptr->data.org, ptr->data.job);

		ptr = ptr->next;
	}
	printf("[NULL]\n");
}

void selection_sort(struct PERSON p[], int n) {
	int i, j,min;
	struct PERSON t;
	for (i = 0; i < n-1; i++) {
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



struct Node* AddNode(struct Node* node)
{
	struct	Node* newNode = (struct Node*)malloc(sizeof(struct Node));
	if (node)
		node->next = newNode;

	return newNode;
}


struct Node* converter(struct PERSON p[], int n) {

	struct Node* firstNode = AddNode(NULL);
	struct Node* node = firstNode;

	for (int i = 0; i < n; i++)
	{
		if (i != 0)
			node = AddNode(node);

		node->data = p[i];
	}
	return firstNode;
}

int main(void) {
	struct PERSON p[MAX_PERSONAL];
	struct Node* list;
	FILE* fp;
	char str[100];
	char* ptr;

	fp = fopen("registraion_data.txt", "r");
	if (fp == NULL) {
		printf("File could not be opened");
		exit(1);
	}
	for (int i = 0; i < 30; i++) {
		
		fgets(str, 100, fp);
		ptr = strtok(str, "/");
		
		p[i].tag= atoi(ptr);

		ptr = strtok(NULL, "/");
		strcpy(p[i].date, ptr);
		
		ptr = strtok(NULL, "/");
		strcpy(p[i].fee, ptr);
		
		ptr = strtok(NULL, "/");
		strcpy(p[i].name, ptr);
		
		ptr = strtok(NULL, "/");
		p[i].age = atoi(ptr);
		
		ptr = strtok(NULL, "/");
		strcpy(p[i].org, ptr);

		ptr = strtok(NULL, "/");
		strcpy(p[i].job, ptr);

		
	}

	fclose(fp);

	//printf("========================Print Array==============================\n\n");
	//for (int i = 0; i < 30; i++) {
	//	printf("%d %s %s %s %d %s %s\n", p[i].tag, p[i].date, p[i].fee, p[i].name, p[i].age, p[i].org, p[i].job);
	//}

	//printf("========================Print Linked list==============================\n\n");


	//list = converter(p, 30);	// Array -> Linked list
	//displayList(list);
	struct PERSON personal[MAX_PERSONAL];
	int num_personal = 30;
	/*if (!read_Personal_File(
		"registraion_data.txt",
		personal,
		&num_personal))
	{
		return -1;
	}*/

	struct PERSON group_age[4][MAX_PERSONAL];
	int group_count[4];
	int age_range[] = { 10, 20, 30, 40 };
	for (int i = 0; i < 4; i++)
	{
		decompose_by_age(p, num_personal, age_range[i], group_age[i], &group_count[i]);
	}
	for (int i = 0; i < 4; i++) {
		selection_sort(group_age[i],group_count[i]);
	}

	char fname[] = "P5-1.txt";
	FILE* pFile = NULL;
	pFile = fopen(fname, "w");
	if (pFile == NULL)
	{
		printf("Error: File Writing Failed: %s\n", fname);
		return false;
	}
	
	for (int i = 0; i < 4; i++)
	{
		write_Personal_group(pFile, age_range[i], group_age[i], group_count[i]);
	}



	fclose(pFile);
	return 0;

}