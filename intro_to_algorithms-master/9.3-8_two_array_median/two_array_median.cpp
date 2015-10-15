#include <iostream>
using namespace std;

/*
算法思想：

该算法类似于二分查找算法

1.两个数组中小于median的个数为(n - 1)个，假设该median为数组a中的第k个，k为数组下标，那么在数组a中已经存在k个值小于median，那么在数组b中必然有(n - 1) - k = (n-k-1)个数小于median，如果b[n - k - 2] <= median <= b[n - k - 1]，那么median就找到了，
  如果median >= b[n - k - 1]，则搜索数组a中[k + 1, n - 1]中的元素，如果median <= b[n - k - 2]，则搜索数组a中[0, k - 1]中的元素.
2.依次类推，类似二分查找，不断重新设置low，high，middle
3.如果在数组a中没找到，则在数组b中找
*/
int search_median(int* a, int* b, int n, int low, int high);

int main()
{
	const int LEN = 6;
	//两个已排序数组
	int a[LEN] = {1, 2, 3, 4, 7, 9};
	int b[LEN] = {2, 5, 6, 7, 10, 11};

	int goal;
	if((goal = search_median(a, b, LEN, 0, LEN - 1)) == -1)
		goal = search_median(b, a, LEN, 0, LEN - 1);
	cout<<goal<<endl;
	return 0;
}

int search_median(int* a, int* b, int n, int low, int high)
{
	while(low <= high)
	{
		int middle = (low + high)/2;
		//到达边界
		if(middle == n - 1 && a[middle] <= b[0])
			return a[middle];
		//非边界
		else if(middle < n - 1)
		{
			if(a[middle] <= b[n - middle - 1] && a[middle] >= b[n - middle -2])
			{
				return a[middle];
			}
			else if(a[middle] >= b[n - middle - 1])
			{
				high = middle - 1;
			}
			else
			{
				low = middle + 1;
			}
		}
	}
	return -1;
}