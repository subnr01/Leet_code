#include <iostream>
using namespace std;

//冒泡排序
#define LEN 35
int main()
{
	int* a = new int[LEN];
	for(int i = 0; i < LEN; i++)
		a[i] = i - 5;

	for(int i = 0; i < LEN; i++)
		for(int j = LEN - 1; j > i; j--)
		{
			if(a[j] > a[j-1])
			{
				int temp = a[j];
				a[j] = a[j-1];
				a[j-1] = temp;
			}
		}

	for(int i = 0; i < LEN; i++)
		cout<<a[i]<<endl;
	return 0;
}