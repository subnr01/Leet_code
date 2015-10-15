#include <iostream>
#include <time.h>
using namespace std;

//随机化分割
int randomized_partition(int* a, int p, int r);
int randomized_select(int* a, int p, int r, int i);

int main()
{
	const int LEN = 12;
	int arr[LEN] = { 5, 7, 10, 3, 6, 2,  8, 9, 4, 1, 11 , 12};

	int median = randomized_select(arr, 0, LEN - 1, (LEN - 1)/2);

	int distance[LEN - 1];
	int distance_cpy[LEN - 1];
	
	int temp = 0;
	int middle;
	for(int i = 0; i < LEN; i++)
	{
		if(arr[i] != median)
			distance[temp++] = abs(arr[i] - median);
		else
			middle = i;
	}
	for(int i = 0; i < LEN - 1; i++)
		distance_cpy[i] = distance[i];

	int k;
	while(true)
	{
		cout<<"输入k："<<endl;
		cin>>k;
		cout<<"k邻近值为："<<endl;
		int kth_value = randomized_select(distance_cpy, 0, LEN - 2, k - 1);
		int* k_arr = new int[k];
		int j = 0;
		//这里要注意，比如中间值为6，那么k = 3，有5,7明显符合要求，他们最近,4,8也符合要求，那么在这选择谁
		if(k%2)
		{
			bool flag = true;
			for(int i = 0; i < LEN - 1; i++)
			{
				if(distance[i] < kth_value)
				{
					if(i < middle)
						k_arr[j++] =  median - distance[i];
					else 
						k_arr[j++] =  median + distance[i];
				}
				else if(distance[i] == kth_value && flag)
				{
					if(i < middle)
						k_arr[j++] =  median - distance[i];
					else 
						k_arr[j++] =  median + distance[i];
					flag = false;
				}
			}
		}
		else
		{
			for(int i = 0; i < LEN - 1; i++)
			{
				if(distance[i] <= kth_value)
				{
					if(i < middle)
						k_arr[j++] =  median - distance[i];
					else 
						k_arr[j++] =  median + distance[i];
				}
			}
		}
		for(int i = 0; i < k; i++)
			cout<<k_arr[i]<<"\t";
		cout<<endl;

		delete[] k_arr;
	}
}

//下标为[p, r]之间的元素
int randomized_partition(int* a, int p, int r)
{
	srand(time(NULL));
	int q = rand()%(r - p + 1) + p;
	int temp = a[q];
	a[q] = a[r];
	a[r] = temp;

	int j = p;
	for(int i = p; i < r; i++)
	{
		if(a[i] < a[r])
		{
			if(i != j)
			{
				int temp2 = a[i];
				a[i] = a[j];
				a[j] = temp2;
			}
			j++;
		}
	}
	
	temp = a[j];
	a[j] = a[r];
	a[r] = temp;

	return j;
}

//迭代版本
int randomized_select(int* a, int p, int r, int i)
{
	int q = randomized_partition(a, p, r);
	while(p != r)
	{
		if(i == q)
			return a[q];
		else if(i < q)
		{
			r = q - 1;
			q = randomized_partition(a, p, r);
		}
		else
		{
			p = q + 1;
			q = randomized_partition(a, p, r);
		}
	}
	return a[p];
}