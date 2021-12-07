#include<stdio.h>

void main() {
	
	unsigned int a = 0x25;
	unsigned int b = 0x62;
	unsigned int c = 0x3F;
	unsigned int d = 0x52;

	unsigned int checksum = 0;

	checksum = a + b + c + d;
	checksum = 0 ^ checksum;
	printf("%0X", checksum);

}