#include <iostream>
using namespace std;
void match(int a[], int b[], int len);

#define MAX 1000000
int main()
{
	//两个数组内部是不能相互进行比较排序的
	int a[] = {2, 3, 7, 5, 1, 9, 6, 8, 4};
	int b[] = {4, 7, 9, 5, 1, 3, 8, 2, 6};
	match(a, b, 9);
	return 0;
}

void match(int a[], int b[], int len)
{
	if(len < 1)
		return;

	int middle = len/2;
	int* b_min_temp = new int[len];
	int* b_max_temp = new int[len];
	for(int i = 0; i < len; i++)
	{
		b_min_temp[i] = MAX;
		b_max_temp[i] = MAX;
	}

	int min_count = 0;
	int max_count = 0;
	for(int i = 0; i < len; i++)
	{
		if(b[i] < a[middle])
		{
			b_min_temp[min_count] = b[i];
			min_count++;
		}
		else if(b[i] > a[middle])
		{
			b_max_temp[max_count] = b[i];
			max_count++;
		}
		else
		{
			cout<<"["<<a[middle]<<", "<<b[i]<<"]"<<endl;
		}
	}
	//b_min保存小于a[middle]的数
	//b_max保存大于a[middle]的数
	int* b_min = new int[min_count];
	for(int i = 0; i < min_count; i++)
		b_min[i] = b_min_temp[i];

	int* b_max = new int[max_count];
	for(int i = 0; i < max_count; i++)
		b_max[i] = b_max_temp[i];
	delete[] b_min_temp;
	delete[] b_max_temp;

	/**************************************************************************************************************************/
	//这两段代码相同，可以用一个函数来包含这段代码，在这就省了。
	int* a_min_temp = new int[len];
	int* a_max_temp = new int[len];
	for(int i = 0; i < len; i++)
	{
		a_min_temp[i] = MAX;
		a_max_temp[i] = MAX;
	}

	min_count = 0;
	max_count = 0;
	//注意这里用的是a[middle]，并不是拿红瓶和红瓶比，其实这里的a[middle]指代的是找到的b[i]这个蓝瓶，然后用这个蓝瓶来跟a数组比较
	for(int i = 0; i < len; i++)
	{
		if(a[i] < a[middle])
		{
			a_min_temp[min_count] = a[i];
			min_count++;
		}
		else if(a[i] > a[middle])
		{
			a_max_temp[max_count] = a[i];
			max_count++;
		}
	}

	//a_min保存小于a[middle]的数
	//a_max保存大于a[middle]的数
	int* a_min = new int[min_count];
	for(int i = 0; i < min_count; i++)
		a_min[i] = a_min_temp[i];

	int* a_max = new int[max_count];
	for(int i = 0; i < max_count; i++)
		a_max[i] = a_max_temp[i];
	delete[] a_min_temp;
	delete[] a_max_temp;

	match(a_min, b_min, min_count);
	delete[] a_min;
	delete[] b_min;
	match(b_max, b_max, max_count);
	delete[] a_max;
	delete[] b_max;
}