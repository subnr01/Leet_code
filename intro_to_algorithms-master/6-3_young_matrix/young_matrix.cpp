#include <iostream>
using namespace std;

//假设MAX为无穷大
#define MAX 1000000

int extract_min(int** a, int m, int n);
void young_matrixify(int** a, int i, int j, int m, int n);
bool find(int** a, int goal, int m, int n);
int main()
{
	int a[4][4] = {2, 4, 9, 12, 3, 5, 14, MAX, 8, 16, MAX, MAX, MAX, MAX, MAX, MAX};
	//打印数组a,可以看出它符合young氏矩阵的定义
	for(int i = 0; i < 4; i++)
	{
		for(int j = 0; j < 4; j++)
			cout<<a[i][j]<<"\t";
		cout<<endl;
	}

	
	int** b = new int*[4];
	for(int i = 0; i < 4; i++)
		b[i] = new int[4];
	for(int i = 0; i < 4; i++)
	{
		for(int j = 0; j < 4; j++)
			b[i][j] = a[i][j];
	}
	if(find(b, 16, 4 ,4))
		cout<<"存在"<<endl;
	else
		cout<<"不存在"<<endl;
	//第一次取出最小值
	extract_min(b, 4, 4);
	//打印取出最小值之后的young氏矩阵
	for(int i = 0; i < 4; i++)
	{
		for(int j = 0; j < 4; j++)
			cout<<b[i][j]<<"\t";
		cout<<endl;
	}
	//再次取出最小值
	extract_min(b, 4, 4);
	//打印取出最小值之后的young氏矩阵
	for(int i = 0; i < 4; i++)
	{
		for(int j = 0; j < 4; j++)
			cout<<b[i][j]<<"\t";
		cout<<endl;
	}
	return 0;
}

int extract_min(int** a, int m, int n)
{
	int temp = a[0][0];
	a[0][0] = a[m-1][n-1];
	a[m-1][n-1] = MAX;

	young_matrixify(a, 0, 0, m, n);
	return temp;
}

//分了四种情况进行讨论，以i,j是否到达边界为区分点
void young_matrixify(int** a, int i, int j, int m, int n)
{
	if(i < m && j < n)
	{
		int ii = i;
		int jj = j;
		if(i+1 < m && j+1 < n && a[i+1][j] > a[i][j+1])
		{
			int temp = a[i][j];
			a[i][j] = a[i][j+1];
			a[i][j+1] = temp;
			jj = j + 1;
		}
		else if(i+1 < m && j+1 < n && a[i+1][j] < a[i][j+1])
		{
			int temp = a[i][j];
			a[i][j] = a[i+1][j];
			a[i+1][j] = temp;
			ii = i + 1;
		}
		else if(i+1 < m && j+1 == n && a[i+1][j] < a[i][j])
		{
			int temp = a[i][j];
			a[i][j] = a[i+1][j];
			a[i+1][j] = temp;
			ii = i + 1;
		}
		else if(j+1 < n && i+1 == m && a[i][j+1] < a[i][j])
		{
			int temp = a[i][j];
			a[i][j] = a[i][j+1];
			a[i][j+1] = temp;
			jj = j + 1;
		}

		if(ii != i || jj != j)
			young_matrixify(a, ii, jj, m, n);
	}
}

//goal为寻找的数字，m,n为维数
bool find(int** a, int goal, int m, int n)
{
	int mm = m;
	int nn = n;
	while(m>0 && n > 0)
	{
		if(a[m-1][nn - n] == goal)
		{
			return true;
		}
		else if(a[m-1][nn - n] > goal)
			 --m;
		else if(a[m-1][nn - n] < goal)
			--n;
	}
	return false;
}