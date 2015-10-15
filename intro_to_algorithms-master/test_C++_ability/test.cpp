#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <Windows.h>

int main()
{
	int busy_time = 10;
	int idle_time = busy_time;

	__int64 start_time = 0;
	while (true)
	{
		start_time = GetTickCount();
		while ((GetTickCount() - start_time) <= busy_time);

		Sleep(idle_time);
	}
}