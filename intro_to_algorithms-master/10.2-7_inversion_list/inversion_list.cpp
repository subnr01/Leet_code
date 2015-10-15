#include <iostream>
using namespace std;

//很简单的Node 和 List
class Node
{
public:
	Node* next;
	int data;

	Node();
	Node(int d);
};

class List
{
public:
	Node* first;

	List();
	void insert(int d);
};

int main()
{
	int K = 5;
	List* list  = new List();
	
	
	for(int i = 0; i < 5; i++)
	{
		list->insert(i);
	}

	Node* before = list->first;
	Node* after = before->next;
	before->next = NULL;
	while(after)
	{
		//交换指针
		Node* temp = after->next;
		after->next = before;
		before = after;
		after = temp;
	}
	//把最后的before赋给list->first
	list->first = before;

	Node* x = list->first;
	while(x)
	{
		cout<<x->data<<endl;
		x = x->next;
	}
	return 0;
}

Node::Node()
{
	next = NULL;
}

Node::Node(int d)
{
	data = d;
	next = NULL;
}

List::List()
{
	first = NULL;
}
//插入节点的操作
void List::insert(int d)
{
	Node* node = new Node(d);

	if(first == NULL)
		first = node;
	else
	{
		Node* temp = first->next;
		Node* prev = first;
		while(temp != NULL)
		{
			prev = temp;
			temp = temp->next;
		}
		prev->next = node;
	}
}