#include <iostream>
#include <cstring>
using namespace std;
class B
{
public:
	char* str;

	//设置str为NULL
	B()
	{
		str = NULL;
	}

	//普通构造函数
	B(char* s)
	{
		cout<<"调用了普通构造函数"<<endl;
		if (s == NULL)
		{
			str = new char[1];
			str[0] = '\0';
		}
		else
		{
			str = new char[strlen(s) + 1];
			strcpy(str, s);
		}
	}

	//拷贝构造函数
	B(const B& temp)
	{
		cout<<"调用拷贝构造函数"<<endl;
		str = new char[strlen(temp.str) + 1];
		strcpy(str, temp.str);
	}

	//赋值构造函数
	B & operator = (const B& x)
	{
		cout<<"调用赋值运算符重载"<<endl;
		if(this == &x)
			return *this;

		delete[] str;

		str = new char [strlen(x.str) + 1];
		strcpy(str, x.str);

		return *this;
	}

	~B()
	{
		printf("~B\n");
		delete[] str;
		str=NULL;
	}
};
class A
{
public:
	B b;

	A(){}
	A(B bb)
	{
		cout<<"A()构造函数"<<endl;
		b = bb;
	}

	~A()
	{
		printf("~A\n");
	}
};


int main()
{
	char* str = new char[5];
	for(int i = 0; i < 4; i++)
		str[i] = 'a';
	str[4] = '\0';

	//这是第一个B
	B* b = new B(str);
	printf("%s\n", b->str);

	//括弧里的*objectB先调用B的拷贝构造函数
	// 然后在A内调用了B的=运算符重载。
	A* a = new A(*b);

	printf("%s\n", b->str);

	//delete A，然后A里的B也被析构了。 
	delete a;

	//delete 这是第一个B。
	delete b;
	return 0;
}
