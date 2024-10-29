#include <iostream>

using namespace std;

void ordenas(int* vec, int n) {
    for(int i = 0; i < n-1; i++) {
        for(int j = 0; j < n-i-1; j++) {
            if(vec[j] > vec[j+1]) {
                int aux = vec[j];
                vec[j] = vec[j+1];
                vec[j+1] = aux;
            }
        }
    }
}

int main() {
   int n;
   cin>> n;
   int vet[n];
   for(int i=0; i<n;i++){
    cin >> vet[i];
   }

    ordenas(vet, n);

    for(int i = 0; i < n; i++)
        cout << vet[i] << " ";
    cout << endl;

    return 0;
}
