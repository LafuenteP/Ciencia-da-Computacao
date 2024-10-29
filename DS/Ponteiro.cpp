#include <iostream> 
#include <iomanip>

using namespace std;

struct aluno {
   float nota[3];
   float media;
};

void calcula_media(aluno *a)
{
   int sum = 0;
   for(int i=0; i<3; i++){
    sum += a->nota[i];
   }
   a->media = sum/3;
}

void calcula_media_turma(aluno *turma, int n)
{
    for(int i=0; i<n; i++){
        calcula_media(&turma[i]);
    }
}

int main()
{
   int n, i, j;
   
   cin >> n;
   aluno turma[n];
   
   for (j = 0; j < n; j++)
      for (i = 0; i < 3; i++)
         cin >> turma[j].nota[i];
   
    calcula_media_turma(turma, n);
   
   for (j = 0; j < n; j++) {
      cout << fixed;
      cout << setprecision(1) << turma[j].media << " ";
   }
   
   return 0;
}