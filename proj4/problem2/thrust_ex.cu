#include <thrust/host_vector.h>
#include <thrust/device_vector.h>

#include <thrust/transform.h>
#include <thrust/sequence.h>

#include <stdio.h>
#include <time.h>
#include <iostream>

long num_steps = 200000; 
double step = 1.0/(double) num_steps;

template<typename T>
struct integral
{
    double step;

    integral(double step) : step(step){}

    __host__ __device__
        T operator()(const T &i) const {
            double x = (i+0.5)*step;
            return 4.0/(1.0+x*x);
        }
};

int main(){
    clock_t start_time = clock();
    thrust::device_vector<int> index(num_steps);

    // make new sequence
    thrust::sequence(index.begin(), index.end());

    integral<double> unary_op(step);
    thrust::plus<double> binary_op;
    double init = 0.0;
    
    // transform (using function unary_op, set by 'integral') and reduction (reduce to a single value, set by summation)
    double sum = thrust::transform_reduce(index.begin(), index.end(), unary_op, init, binary_op);
    double pi = step * sum;
    printf("pi=%.10lf\n",pi);

    clock_t end_time = clock();

    clock_t diff_time = end_time - start_time;
	  printf("execution time: %.3lf sec. \n", (double)diff_time/CLOCKS_PER_SEC);

    return 0;
}
