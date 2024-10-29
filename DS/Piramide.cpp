#include <iostream>
#include <string>

using namespace std;

void fazVet(int* vet1, int* vet2, int n, int i=0){
    if(i== n-1) return;
    vet2[i] = vet1[i] + vet1[i+1];
    return fazVet(vet1, vet2, n, i+1);
}

void ImprimiVet(int* vet, int comeco, int final){
    if(comeco==0) cout << "[";
    if(comeco>0)  cout << ", ";
    if(comeco==final-1){
     cout << vet[comeco] << "]" << endl;
     return;
    }

    cout << vet[comeco];
    ImprimiVet(vet, comeco+1, final);
}

void Piras(int* vet, int n){
    if(n==1){
        cout << "[" << vet[0] << "]" << endl;
        return;
    }

    int* NewV = new int [n - 1];

    fazVet(vet, NewV, n);
    Piras(NewV, n-1);
    ImprimiVet(vet, 0, n);

    delete[] NewV;
    
}

int main(){
    int n;
    cin >> n;
    int vet[n];
    for(int i=0; i<n; i++) cin >> vet[i];
    

    Piras(vet, n);

    return 0;
}