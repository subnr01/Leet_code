#include <iostream>
using namespace std;

//计数排序的总时间为O(k+n)，在实践中，如果当k = O(n)时，我们常常采用计数排序，这时其运行时间为O(n)


//通过改编计数排序而来，因为有些部分需要注释掉
void counting_sort(int* &a, int length, int k, int* &b, int* &c);

int main()
{
	const int LEN = 100;
	int *a = new int[LEN];
	for(int i = 0; i < LEN; i++)
		a[i] = (i - 50)*(i - 50) + 4;
	int* b = new int[LEN];

	const int k = 2504;
	int* c = new int[k + 1];
	counting_sort(a, LEN, k, b, c);

	//这里需要注释掉
	//for(int i = 0; i < LEN; i++)
	//	cout<<b[i]<<endl;

	int m;
	int n;
	while(cin>>m>>n)
	{
		if(m >n)
			cout<<"区间输入不对"<<endl;
		else
		{
			if(n < 0)
				cout<<"个数为"<<0<<endl;
			else if(m <= 0 && n <= k)
				cout<<"个数为"<<c[n]<<endl;
			else if(n > k && m > 0)
				cout<<"个数为"<<c[k] - c[m - 1]<<endl;
			else if(n > k && m <= 0)
				cout<<"个数为"<<c[k]<<endl;
			else
				cout<<"个数为"<<c[n] - c[m - 1]<<endl;
		}
	}
	return 0;
}

void counting_sort(int* &a, int length, int k, int* &b, int* &c)
{
	for(int i = 0; i < k + 1; i++)
		c[i] = 0;
	for(int i = 0; i < length; i++)
		c[a[i]]++;
	for(int i = 1; i < k + 1; i++)
		c[i] = c[i] + c[i-1];

	//这里需注释，因为对c数组内的元素进行减减操作会使其改变
	/*for(int i = length - 1; i >= 0; i--)
	{
	b[c[a[i]] - 1] = a[i];
	c[a[i]]--;
	}*/
}