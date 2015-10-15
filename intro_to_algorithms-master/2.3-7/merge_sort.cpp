#include <iostream>
using namespace std;

void sort(int* &a, int p, int q, int r)
{
	int* left = new int[q - p + 1];
	int* right = new int[r - q];
	for(int i = 0; i < q - p + 1; i++)
		left[i] = a[p + i];
	for(int i = 0; i < r - q; i++)
		right[i] = a[q + i + 1];

	int m = 0;
	int n = 0;
	//书上在这里用的是在数组末尾添加一个数，即"哨兵牌"
	//作者同时让我们不用"哨兵牌"来实现
	//我在这里采取的是，如果到达一个数组的末尾，
	//那么另外一个数组的剩余元素就不用比较了，然后可以完全复制到a，见27行.
	//时间复杂度未变，因为27行的循环紧跟20行的循环而已，你可以看到结束条件都是i<=r,j<=r，然后break
	for(int i = p; i <= r; i++)
	{
		if(left[m] <= right[n])
		{
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
				for(int j = i + 1; j <= r; j++)
					a[j] = left[m++];
				break;
			}
		}
	}

	delete[] left;
	delete[] right;
}
void merge(int* &a, int m, int n)
{
	if(m < n)
	{
		int q = (m + n)/2;
		merge(a, m, q);
		merge(a, q+1, n);
		sort(a, m, q, n);
	}
}

//二分查找
bool binary_search(int* a, int n, int goal)
{
	int middle = (n - 1)/2;
	int high = n - 1;
	int low = 0;
	while(low <= high)
	{
		if(a[middle] == goal)
			return true;
		else if(a[middle] >= goal)
			high = middle - 1;
		else
			low = middle + 1;
		middle = (low + high)/2;
	}
	return false;
}
int main()
{
	const int LEN = 1000000;
	//用max来代表最大数，你可以任意选取
	int max = 100000000;
	int* a = new int[LEN];
	for(int i = 0; i < LEN; i++)
		a[i] = LEN - i;
	merge(a, 0, LEN - 1);

	int x;
	cin>>x;
	if(!(x%2))
	{
		for(int i = 0; i < LEN; i++)
			if(a[i] == x/2)
			{
				a[i] = max;
				break;
			}
	}
		
	for(int i = 0; i < LEN; i++)
		if(binary_search(a, LEN, x - a[i]))
		{
			cout<<"YES"<<endl;
			break;
		}

	delete a;
	return 0;
}