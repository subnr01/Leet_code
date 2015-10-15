#include <iostream>
using namespace std;

void quick_sort(int* &a, int p, int r);
int partition(int* &a, int p, int r);
int main()
{
	const int LEN = 20;
	int b[LEN] = {12, 43, 0, -4, 98, 75, 64, 88, 5, 32, 11, 12, 13, 84, 34, 27, -5, -244, 49, 345};
	int* a = new int[LEN];
	for(int i = 0; i < LEN; i++)
		a[i] = b[i];
	quick_sort(a, 0, LEN - 1);
	for(int i = 0; i < LEN; i++)
		cout<<a[i]<<endl;
	return 0;
}

void quick_sort(int* &a, int p, int r)
{
	if(p < r)
	{
		int q = partition(a, p, r);
		quick_sort(a, p, q - 1);
		quick_sort(a, q + 1, r);
	}
}
int partition(int* &a, int p, int r)
{
	int j = p;
	for(int i = p; i < r; i++)
	{
		if(a[i] >= a[r])
		{
			if(i != j)
			{
				int temp = a[i];
				a[i] = a[j];
				a[j] = temp;
			}
			j++;
		}
	}
	int ex = a[j];
	a[j] = a[r];
	a[r] = ex;
	
	return j;
}