#include <iostream>
#include <sstream>
#include <string>

using namespace std;

typedef struct{
    string nome;
    int matricula;
    double media;
} Aluno;

Aluno ler_aluno(Aluno qualquer){
    cin >> qualquer.matricula;
    cin.ignore();
    getline(cin, qualquer.nome);
    cin >> qualquer.media;

    return qualquer;
}

int main(){

    int n=0, m;
    cin >> n;
    Aluno aluno[n];
    for(int i=0; i<n; i++){
        aluno[i] = ler_aluno(aluno[i]);
    }

    cin >> m;
    cout.precision(1);

    for(int i=0; i<n; i++){
        if(aluno[i].matricula == m){
            cout << aluno[i].nome << endl << fixed << aluno[i].media;
            return  0;
        }
    }

    cout << "NAO ENCONTRADA" << endl;

return 0;
}