#include <iostream>
using namespace std;

class Node
{
public:
	Node* left;
	Node* right;
	int data;

	Node();
	Node(int d);
};

class BinaryTree
{
public:
	Node* root;

	//创建树
	void create_tree(Node** node, int len);
	//求最小值
	int min(int a, int b);
	//寻找第二小值
	int search_second_small();

	BinaryTree();
};

int main()
{
	const int LEN = 5;
	int arr[LEN] = {2,1,4,3,5};
	
	//叶子节点
	Node** node = new Node*[10];
	for(int i = 0; i < LEN; i++)
		node[i] = new Node(arr[i]);
	BinaryTree* bi_tree = new BinaryTree();

	bi_tree->create_tree(node, LEN);
	cout<<bi_tree->root->data<<endl;
	cout<<bi_tree->search_second_small()<<endl;
	return 0;
}

Node::Node()
{
	left = right = NULL;
}
Node::Node(int d)
{
	data = d;
	left = right = NULL;
}

void BinaryTree::create_tree(Node** node, int len)
{
	//len == 2时，就剩下两个元素进行比较了，得到最后一个元素为root节点，即最小值节点
	if(len == 2)
	{
		root->left = node[0];
		root->right = node[1];
		root->data = min(node[0]->data, node[1]->data);
	}
	else
	{
		int new_len = (len%2) ? (len/2 + 1) : len/2;
		Node** new_node = new Node*[new_len];

		//new_node元素个数为奇数
		if(len%2)
		{
			for(int i = 0; i < new_len - 1; i++)
			{
				//构建父亲节点
				new_node[i] = new Node(min(node[2*i]->data, node[2*i+1]->data));
				new_node[i]->left = node[2*i];
				new_node[i]->right = node[2*i+1];
			}
			new_node[new_len - 1] = node[len - 1];
		}
		//new_node元素个数为偶数
		else
		{
			for(int i = 0; i < new_len; i++)
			{
				//构建父亲节点
				new_node[i] = new Node(min(node[2*i]->data, node[2*i+1]->data));
				new_node[i]->left = node[2*i];
				new_node[i]->right = node[2*i+1];
			}
		}

		create_tree(new_node, new_len);
		delete[] new_node;
	}
}

int BinaryTree::min(int a, int b)
{
	return a < b ? a : b;
}

int BinaryTree::search_second_small()
{
	int second = 1000000;
	Node* p = root;
	while(p->left != NULL && p->right != NULL)
	{
		if(p->data == p->left->data && second >  p->right->data)
		{
			second = p->right->data;
			p = p->left;
		}
		else if(p->data == p->right->data && second >  p->left->data)
		{
			second = p->left->data;
			p = p->right;
		}
		else
			return second;
	}
	return second;
}
BinaryTree::BinaryTree()
{
	root = new Node();
}