#include <iostream>
#include <string>
#include <sstream>

using namespace std;

typedef struct{
    string nome;
    int idade;
} Pessoa;

void preencheArray(Pessoa *A, int n){
    for(int i=0; i<n; i++){
        cout << "Digite o nome: ";
        cin.ignore();
        getline(cin, A[i].nome);
        cout << "Digite a idade: ";
        cin >> A[i].idade;
    }
}

void imprimeArray(Pessoa *A, int n){
    for(int i=0; i<n; i++){
        cout << "Nome do individuo: " << A[i].nome << endl << "Idade do individuo: " << A[i].idade << endl << endl;
    }
}

int main(){
    int n;
    cin >> n;
    Pessoa a;

    Pessoa *ptr = new Pessoa[n];
    preencheArray(ptr, n);
    imprimeArray(ptr, n);

    delete[] ptr;
}