#include <stdio.h>
#include <stdlib.h>

struct tnode {
    int data;
    struct tnode * left_child;
	struct tnode * right_child;
};



typedef struct tnode node;
typedef node *tree_ptr;


tree_ptr  newNode(int data){
    tree_ptr newNode = (tree_ptr)malloc(sizeof(node));
    newNode->left_child = newNode -> right_child = NULL;
    newNode->data = data;
    return newNode;
}

tree_ptr addSibling(tree_ptr t, int data){
    if (t == NULL)  return NULL;

    // Connecting siblings of a parent
    while(t->right_child)  t=t->right_child;

    return t->right_child = newNode(data);

}

tree_ptr addChild(tree_ptr t, int data){
    if(t==NULL) return NULL;

    /**
     * @brief If node has same parent node, create Sibling by 'addSibling'
     * For example, In Tree 1 node's data 200, 75, 300 have a same parent node(=100)
     */
    if(t->left_child)  return addSibling(t->left_child, data);
    
    //If a new parent node create a child node the first time, create a child node
    else return t->left_child=newNode(data);
}


void preorder(tree_ptr ptr) 
{
   if(ptr!=NULL) 
   {
      printf("[%d] ",ptr->data);
      preorder(ptr->left_child);
      preorder(ptr->right_child);
   }
}

int main()
{   
    //Convert Tree 1 to binarytree
    tree_ptr tree1 = newNode(100);
    tree_ptr tree1_1 = addChild(tree1,200);
    tree_ptr tree1_2 = addChild(tree1, 75);
    tree_ptr tree1_3 = addChild(tree1, 300);
    tree_ptr tree1_4 = addChild(tree1_2, 25);
    tree_ptr tree1_5 = addChild(tree1_2, 50);
    tree_ptr tree1_6 = addChild(tree1_2, 30);
    tree_ptr tree1_7 = addChild(tree1_2, 150);
    tree_ptr tree1_8 = addChild(tree1_6, 120);
    tree_ptr tree1_9 = addChild(tree1_6, 55);
    
    
    
    
    //Convert Tree 2 to binarytree
    tree_ptr tree2 = newNode(100);
    tree_ptr tree2_1 = addChild(tree2, 200);
    tree_ptr tree2_2 = addChild(tree2, 75);
    tree_ptr tree2_3 = addChild(tree2, 300);
    tree_ptr tree2_4 = addChild(tree2, 95);
    tree_ptr tree2_5 = addChild(tree2_2, 25);
    tree_ptr tree2_6 = addChild(tree2_2, 50);
    tree_ptr tree2_7 = addChild(tree2_2, 30);
    tree_ptr tree2_8 = addChild(tree2_4, 150);
    tree_ptr tree2_9 = addChild(tree2_7, 120);

    


    printf("Tree 1's preorder traversal result: ");
	preorder(tree1);
    printf("\n");
    printf("Tree 2's preorder traversal result: ");
    preorder(tree2);

}		    