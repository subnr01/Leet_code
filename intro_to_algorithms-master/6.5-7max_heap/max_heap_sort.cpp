#include <iostream>
using namespace std;

//修正i的位置，在此处已经假设i的子节点都是堆
void max_heapify(int* &a, int i, int length);
//建立数组的堆
void build_max_heap(int* &a, int length);
//利用堆对数组重新排序，总是拿第一个和最后一个对调，数组长度减一
void heap_sort(int* &a, int length);
//删除ith元素
void heap_delete(int* &a, int i, int length);
//插入x
void heap_insert(int* &a, int x, int length);
//将ith位置上的数增加到key
void increase_key(int* &a, int i, int key);

int main()
{
	const int LEN = 10;
	int b[10] = {12, 43, 0, -4, 98, 75, 64, 88, 5, 32};
	int* a = new int[LEN];
	for(int i = 0; i < LEN; i++)
		a[i] = b[i];

	build_max_heap(a, LEN);
	increase_key(a, 7, 100);
	heap_insert(a, 10, LEN);
	heap_delete(a, 1, LEN);
	heap_sort(a, LEN);
	for(int i = 0; i < LEN; i++)
		cout<<a[i]<<endl;
	return 0;
}

void max_heapify(int* &a, int i, int length)
{
	int largest = i;

	while(largest <= length - 1)
	{
		int left = 2*largest + 1;
		int right = 2*largest + 2;
		int temp = largest;
		if(left <= length - 1 && a[left] > a[largest])
		{
			largest = left;
		}

		if(right <= length - 1 && a[right] > a[largest])
		{
			largest = right;
		}

		if(largest != temp)
		{
			int exchange = a[largest];
			a[largest] = a[temp];
			a[temp] = exchange;
		}
		else
			break;
	}
}

void build_max_heap(int* &a, int length)
{
	int root = length/2 - 1;

	for(int i = root; i >= 0; i--)
		max_heapify(a, i, length);
}

void heap_sort(int* &a, int length)
{
	for(int i = length - 1; i >= 1; i--)
	{
		int temp = a[0];
		a[0] = a[i];
		a[i] = temp;
		max_heapify(a, 0, i);
	}
}

void heap_delete(int* &a, int i, int length)
{
	if(i != length - 1)
	{
		a[i] = a[length - 1];
		max_heapify(a, i, length);	
	}
}

void heap_insert(int* &a, int x, int length)
{
	int* temp = a;
	a = new int[length + 1];
	for(int i = 0; i < length; i++)
		a[i] = temp[i];
	delete temp;
	//用-10000000代替负无穷
	a[length] = -10000000;
	increase_key(a, length, x);
}

void increase_key(int* &a, int i, int key)
{
	if(a[i] > key)
		cout<<"key should be larger than a[i]"<<endl;
	else
	{
		int parent = (i - 1)/2;
		a[i] = key;
		while(parent >= 0 && a[parent] < key)
		{
			int temp = a[parent];
			a[parent] = key;
			a[i] = temp;
			i = parent;
			parent = (i - 1)/2;
		}
	}
}