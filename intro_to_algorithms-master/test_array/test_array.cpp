#include <iostream>
using namespace std;

int main()
{
	int arr[3] = {1,4,7};
	cout<<sizeof(arr)/sizeof(arr[0])<<endl;

	int* arr1 = new int[8];
	cout<<sizeof(arr1)/sizeof(arr[0])<<endl;
	return 0;
}