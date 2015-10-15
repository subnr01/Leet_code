#include <iostream>
#include <vector>
using namespace std;

class Solution 
{
public:
	double findKth(int a[], int m, int b[], int n, int k)
	{
		// always assume m <= n
		if (m > n)
			return findKth(b, n, a, m, k);
		if (m == 0)
			return b[k-1];
		if (k == 1)
			return min(a[0], b[0]);
		
		int pa = min(m, k / 2);
		int pb = k - pa;
		if (a[pa - 1] < b[pb - 1])
			return findKth(a + pa, m - pa, b, n, k - pa);			
		else if (a[pa - 1] > b[pb - 1])
			return findKth(a, m, b + pb, n - pb, k - pb);
		else
			return a[pa-1];
			
	}
	
    double findMedianSortedArrays(int A[], int m, int B[], int n) 
    {
		int total = (m + n);
		if (total & 0x01) {
			return findKth(A, m, B, n, total / 2 + 1);
		} else {
			return (findKth(A, m, B, n, total / 2) + findKth(A, m, B, n, total / 2 + 1)) / 2.0;
		}
    }
};
