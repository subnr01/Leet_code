#ifndef LIST_H
#define LIST_H
#include "Node.h"
#include <iostream>

class List
{
public:
	Node* first;

	List();
	void insert(int d);
};

#endif