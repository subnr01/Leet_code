#include "List.h"

List::List()
{
	first = NULL;
}

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