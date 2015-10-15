#include "List.h"
using namespace std;

//修正i的位置，在此处已经假设i的子节点都是堆
void min_heapify(Node* &a, int i, int length);
//建立数组的堆
void build_min_heap(Node* &a, int length);
//利用堆对数组重新排序，总是拿第一个和最后一个对调，数组长度减一
void heap_sort(Node* &a, int length);


int main()
{
	int K = 5;
	List* list_one  = new List();
	List* list_two = new List();
	List* list_three = new List();
	List* list_four = new List();
	List* list_five = new List();
	
	for(int i = 0; i < 5; i++)
	{
		list_one->insert(i);
		list_two->insert(i - 3);
		list_three->insert(i + 5);
		list_four->insert(2*i);
		list_five->insert(i - 10);
	}

	Node* arr = new Node[K];
	arr[0] = *(list_one->first);
	arr[1] = *(list_two->first);
	arr[2] = *(list_three->first);
	arr[3] = *(list_four->first);
	arr[4] = *(list_five->first);

	//先对这K个排序
	build_min_heap(arr, K);

	List* list = new List();

	//每次取arr[0]的下一个Node，如果Node为空的话，则把堆末尾的元素补到arr[0],由于有--K,所以堆的大小减一
	while(K > 0)
	{
		list->insert(arr[0].data);
		if(arr[0].next != NULL)
			arr[0] = *(arr[0].next);
		else 
			arr[0] = arr[--K];
		min_heapify(arr, 0, K);
	}
	Node* begin = list->first;

	while(begin != NULL)
	{
		cout<<begin->data<<endl;
		begin = begin->next;
	}
	return 0;
}

void min_heapify(Node* &a, int i, int length)
{
	int smallest = i;

	while(smallest <= length - 1)
	{
		int left = 2*smallest + 1;
		int right = 2*smallest + 2;
		int temp = smallest;
		if(left <= length - 1 && a[left] < a[smallest])
		{
			smallest = left;
		}

		if(right <= length - 1 && a[right] < a[smallest])
		{
			smallest = right;
		}

		if(smallest != temp)
		{
			Node exchange = a[smallest];
			a[smallest] = a[temp];
			a[temp] = exchange;
		}
		else
			break;
	}
}

void build_min_heap(Node* &a, int length)
{
	int root = length/2 - 1;

	for(int i = root; i >= 0; i--)
		min_heapify(a, i, length);
}

void heap_sort(Node* &a, int length)
{
	for(int i = length - 1; i >= 1; i--)
	{
		Node temp = a[0];
		a[0] = a[i];
		a[i] = temp;
		min_heapify(a, 0, i);
	}
}