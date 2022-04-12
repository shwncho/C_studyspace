#include <stdio.h>
#include <stdlib.h>

struct tnode {
    int data;
    struct tnode * left_child;
	struct tnode * right_child;
};
typedef struct tnode node;
typedef node *tree_ptr;

tree_ptr insert(tree_ptr head, int number)
{	
	tree_ptr temp=NULL;
	tree_ptr insertpoint = NULL;

	/* head 값이 NULL 이면 빈 트리이므로 노드를 만들고 head 값을 반환 */
	if(! head){ 
        temp = (tree_ptr)malloc(sizeof(node));
	    temp->data = number;
	    temp->left_child = temp->right_child = NULL;
        return temp;
 	}

	/* head 값이 NULL 이 아니면 */ 
	insertpoint = head;	
	while(insertpoint != NULL){
	   //printf("%d ", insertpoint->data);
	    if ((insertpoint->data > number) && (insertpoint->left_child != NULL)) insertpoint = insertpoint->left_child;
	    else if (insertpoint->data == number) return head; 
	    else if ((insertpoint->data < number) && (insertpoint->right_child != NULL)) insertpoint = insertpoint->right_child;
	    else break;
	}

	temp = (tree_ptr)malloc(sizeof(node));
	temp->data = number;
	temp->left_child = temp->right_child = NULL;
	if (insertpoint->data < number) insertpoint->right_child = temp;
	else insertpoint->left_child=temp;
        return head;
 	
}

tree_ptr iter_search(tree_ptr tree, int key) 
{
    while(tree) {
        if(key == tree->data) return tree;  /*찾음*/
        if(key < tree->data)
              tree = tree->left_child;      /*왼쪽 트리에서 검색 */
        else
              tree = tree->right_child;     /* 오른쪽 트리에서 검색 */
        }
    return NULL;
}

void inorder(tree_ptr ptr) 
{
   if(ptr) 
   {
      inorder(ptr->left_child);
      printf("%d ",ptr->data);
      inorder(ptr->right_child);
   }
}

int main()
{
	int i, number[10] = {23, 24, 42, 13, 28, 56, 32, 14, 31, 17};
	tree_ptr head = NULL, findnode=NULL;
	/* 데이터를 순서대로 삽입하여 이진탐색트리(bst)를 구성한다 */
	for(i=0; i < 10; i++)
	{ 	
	  head = insert(head,number[i]); 
	}; 
        
   	/* 데이터 31을 찾아본다 */
	findnode=iter_search(head, 31);
	if(findnode) printf("(Success) find a node : %d \n", findnode->data);
	else	printf("searchKey is not in a tree");

	/* inorder 탐색을 해본다 */
	printf("inorder 탐색 \n");
	inorder(head);
}	    