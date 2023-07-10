#include <omp.h>
#include <stdio.h>

#define NUM_END 200000

int isPrime(int x) {
	if (x <= 1) return 0;
	for (int i = 2; i < x; i++) {
		if (x % i == 0) return 0;
	}
	return 1;
}

int main(int argc, char* argv[]) {
	int i;
	int schedulingType = atoi(argv[1]);
	int NUM_THREADS = atoi(argv[2]);
	int counter = 0;

	double startTime = omp_get_wtime();
	switch (schedulingType) {
	case 1:
#pragma omp parallel for num_threads(NUM_THREADS) schedule(static)
		for (i = 0; i < NUM_END; i++) if (isPrime(i)) 
#pragma omp critical
		counter++;
		break;
	case 2:
#pragma omp parallel for num_threads(NUM_THREADS) schedule(dynamic)
		for (i = 0; i < NUM_END; i++) if (isPrime(i))
#pragma omp critical
			counter++;
		break;
	case 3:
#pragma omp parallel for num_threads(NUM_THREADS) schedule(static, 10)
		for (i = 0; i < NUM_END; i++) if (isPrime(i))
#pragma omp critical
			counter++;
		break;
	case 4:
#pragma omp parallel for num_threads(NUM_THREADS) schedule(dynamic, 10)
		for (i = 0; i < NUM_END; i++) if (isPrime(i))
#pragma omp critical
			counter++;
		break;
	}
	double endTime = omp_get_wtime();

	printf("Program Execution Time : %fms\n", (endTime - startTime) * 1000);
	printf("1...%d prime# counter=%d\n", NUM_END - 1, counter);

	return 0;
}