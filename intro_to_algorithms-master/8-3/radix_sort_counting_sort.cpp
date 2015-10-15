#include <iostream>
#include <fstream>
using namespace std;

void radix_sort(int* a, const int d, const int length, int radix);
int getBit(int m, int i, int radix);
int pow2(int a, int b);

int main()
{
	const int LEN = 50000;
	int a[LEN];
	for(int i = 0; i < LEN; i++)
	{
		a[i] = rand()%100000;
	}
	//一位数、二位数、三位数、四位数的个数
	int one = 0; 
	int ten = 0; 
	int hundred = 0; 
	int thousand = 0; 
	int ten_thousand = 0;
	//保存一位数、二位数、三位数、四位数相应的数组
	int* one_arr = new int[LEN];
	int* ten_arr = new int[LEN];
	int* hundred_arr = new int[LEN];
	int* thounsand_arr = new int[LEN];
	int* ten_thousand_arr = new int[LEN];


	for(int i = 0; i < LEN; i++)
	{
		if(a[i] >= 0 && a[i] < 10)
			one_arr[one++] = a[i];
		else if(a[i] >= 10 && a[i] < 100)
			ten_arr[ten++] = a[i];
		else if(a[i] >= 100 && a[i] < 1000)
			hundred_arr[hundred++] = a[i];
		else if(a[i] >= 1000 && a[i] < 10000)
			thounsand_arr[thousand++] = a[i];
		else
			ten_thousand_arr[ten_thousand++] = a[i];
	}

	

	radix_sort(one_arr, 1, one, 10);
	radix_sort(ten_arr, 2, ten, 10);
	radix_sort(hundred_arr, 3, hundred, 10);
	radix_sort(thounsand_arr, 4, thousand, 10);
	radix_sort(ten_thousand_arr, 5, ten_thousand, 10);

	int one_to_ten = one + ten;
	int one_to_hundred = one + ten + hundred;
	int one_to_thousand = one + ten + hundred + thousand;
	int one_to_ten_thousand = one + ten + hundred + thousand + ten_thousand;
	for(int i = 0; i < LEN; i++)
	{
		if(i < one)
			a[i] = one_arr[i];
		else if(i < one_to_ten)
			a[i] = ten_arr[i - one];
		else if(i < one_to_hundred)
			a[i] = hundred_arr[i - one_to_ten];
		else if(i < one_to_thousand)
			a[i] = thounsand_arr[i - one_to_hundred];
		else if(i < one_to_ten_thousand)
			a[i] = ten_thousand_arr[i - one_to_thousand];
	}
	const char* file_name = "8-3(a).txt";
	ofstream out_to_file;
	out_to_file.open(file_name);
	for(int i = 0; i < LEN; i++)
		out_to_file<<a[i]<<endl;
	out_to_file.close();
	delete one_arr;
	delete ten_arr;
	delete hundred_arr;
	delete thounsand_arr;
	delete ten_thousand_arr;
	return 0;
}

void radix_sort(int* a, const int d, const int length, int radix)
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