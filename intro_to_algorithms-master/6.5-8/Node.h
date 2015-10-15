#ifndef NODE_H
#define NODE_H

#include <iostream>
class Node
{
public:
	Node* next;
	int data;

	Node();
	Node(int d);

	operator Node() const;
	//只需用到"<"号
	bool operator < (const Node& temp);
};

#endif