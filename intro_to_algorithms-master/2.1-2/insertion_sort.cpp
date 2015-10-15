#include <iostream>
using namespace std;
#define LEN 17
//最坏的情况O(n2)
int main()
{
	int* a = new int[LEN];
	for(int i = 0; i < LEN; i++)
		a[i] = i - 5;

	int temp = a[0];
	for(int i = 1; i < LEN; i++)
	{
		int j = i;
		int key = a[i];
		while(j > 0 && key > a[j-1])
		{
			a[j] = a[j-1];
			j--;
		}
		a[j] = key;
	}

	for(int i = 0; i < LEN; i++)
		cout<<a[i]<<endl;

	delete[] a;
	return 0;
}