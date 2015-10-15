#include <iostream>
using namespace std;

//寻找一个数组中的逆序对
//逆序对:
//i < j时，有a[i] > a[j]

//算法思想:修改合并排序
#define LEN 5

void merge(int* a, int p, int q, int r, int &count)
{
	int* left = new int[q-p+1];
	int* right = new int[r-q];
	for(int i = 0; i < q-p+1; i++)
		left[i] = a[p+i];
	for(int i = 0; i < r-q; i++)
		right[i] = a[q+i+1];

	int m = 0;
	int n = 0;
	for(int i = p; i <= r; i++)
	{
		if(left[m] > right[n])
		{
			count += r - q - n;
			a[i] = left[m];
			if(m++ == q-p)
			{
				for(int j = i + 1; j <= r; j++)
					a[j] = right[n++];
				break;
			}
		}
		else
		{
			a[i] = right[n];
			
			if(n++ == r-q-1)
			{
				for(int j = i+1; j <= r; j++)
					a[j] = left[m++];
				break;
			}
		}
	}
	delete[] left;
	delete[] right;
}
void sort(int* a, int m, int n, int &count)
{
	if(m < n)
	{
		int middle = (m + n)/2;
		sort(a, m, middle, count);
		sort(a, middle+1, n, count);
		merge(a, m, middle, n, count);
	}
}

int main()
{
	int* a = new int[LEN];
	//for(int i = 0; i < LEN; i++)
	//	a[i] = 10 - i;
	a[0] = 2;
	a[1] = 3;
	a[2] = 8;
	a[3] = 6;
	a[4] = 1;
	int count = 0;

	sort(a, 0, LEN - 1, count);
	cout<<count<<endl;
	for(int i = 0; i < LEN; i++)
		cout<<a[i]<<endl;
	delete[] a;
	return 0;
}