#include <iostream>
#include <sstream>

using namespace std;

long fib(long n){
    if(n<2){
        return n;
    }

    return fib(n-1)+ fib(n-2);
}

int main(){
    cout << fib(3);
}