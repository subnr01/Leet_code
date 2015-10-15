#include <iostream>
using namespace std;

template <class T>
class Deque
{
public:
	void push_front(T t);
	void push_back(T t);
	T pop_front();
	T pop_back();
	Deque(int m);
	~Deque();
private:
	T* arr;
	int max;
	int count;
	int head;
	int tail;
};

int main()
{
	Deque<int> d(3);
	d.push_front(0);
	d.push_back(2);
	d.push_front(4);
	
	cout<<d.pop_front()<<endl;
	d.push_back(3);
	cout<<d.pop_back()<<endl;
	return 0;
}

template <class T>
void Deque<T>::push_front(T t)
{
	if(count == max)
	{
		cout<<"deque is full"<<endl;
		return;
	}
	//保证新插入的元素总在前面
	head = (head == 0) ? max - 1 : head - 1;
	arr[head] = t;
	count++;
}

template <class T>
void Deque<T>::push_back(T t)
{
	if(count == max)
	{
		cout<<"deque is full"<<endl;
		return;
	}
	//保证新插入的元素总在后面
	tail = (tail == max - 1) ? 0 : tail + 1;
	arr[tail] = t;
	count++;
}

template <class T>
T Deque<T>::pop_front()
{
	if(count == 0)
	{
		cout<<"deque is empty"<<endl;
		return NULL;
	}
	int temp = head;
	head = (head == max - 1) ? 0 : head + 1;
	count--;
	return arr[temp];
}

template <class T>
T Deque<T>::pop_back()
{
	if(count == 0)
	{
		cout<<"deque is empty"<<endl;
		return NULL;
	}
	int temp = tail;
	tail = (tail == 0) ? max  -1 : tail - 1;
	count--;
	return arr[temp];
}

template <class T>
Deque<T>::Deque(int m)
{
	max = m;
	count = 0;
	head = 0;
	tail = m - 1;
	arr = new T[m];
}

template <class T>
Deque<T>::~Deque()
{
	delete[] arr;
}