#include "Node.h"

Node::Node()
{
	next = NULL;
}

Node::Node(int d)
{
	data = d;
	next = NULL;
}

Node::operator Node() const
{
	return data;
}

bool Node::operator<(const Node& temp)
{
	if(data < temp.data)
		return true;
	return false;
}