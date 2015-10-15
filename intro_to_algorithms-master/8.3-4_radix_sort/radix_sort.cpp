#include <iostream>
#include <math.h>
using namespace std;

void radix_sort(int a[], const int d, const int length, int radix);

int getBit(int m, int i, int radix);

int pow2(int a, int b);

int main()
{
	//数组长度
	const int LEN = 15;
	int a[LEN] = {35, 48, 0, 8, 15, 80, 99, 3, 24, 168, 195, 224, 63, 120, 143};
	//多少位
	const int d = 2;
	//基数
	const int radix = 15;
	//在这调用的是通用的基数排序算法，可以任意改变基数radix，
	//但记得改变d，因为radix改变的话，数字的位数会改变，
	//最简单的是十进制改成二进制，位数激增
	radix_sort(a, d, LEN, radix);
	for(int i = 0; i < LEN; i++)
		cout<<a[i]<<endl;
}

void radix_sort(int a[], const int d, const int length, int radix)
{
	//存储a中每个元素的余数
	int* remainder = new int[length];
	//统计余数(等同于计数排序中的c)
	int* c = new int[radix];
	//保存排序之后的a
	int* b = new int[length];

	for(int i = 0; i < radix; i++)
		c[i] = 0;

	for(int i = 0; i < d; i++)
	{
		for(int j = 0; j < length; j++)
		{
			int temp = getBit(a[j], i, radix);
			remainder[j] = temp;
			c[temp]++;
		}
		for(int k = 1; k < radix; k++)
			c[k] = c[k] + c[k - 1];
		for(int k = length - 1; k >= 0; k--)
		{
			b[--c[remainder[k]]] = a[k];
			//c[remainder[k]]--;
		}
		for(int k = 0; k < radix; k++)
			c[k] = 0;
		for(int i = 0; i < length; i++)
			a[i] = b[i];
	}
	delete remainder;
	delete c;
	delete b;
}

//得到相应位上的数值
int getBit(int m, int i, int radix)
{
	return (m%(pow2(radix, i + 1)))/pow2(radix, i);
}

//只处理指数b>=0
int pow2(int a, int b)
{
	if(b > 0)
	{
		int result = a;
		for(int i = 0; i < b - 1; i++)
			result = result*a;
		return result;
	}
	else if(b == 0)
		return 1;
	else
	{
		cout<<"不处理指数小于零的情况"<<endl;
		return -1;
	}
}