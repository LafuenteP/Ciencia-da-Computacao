atividade = "06"
nome = "Lafuente Paulino da Silva"
matricula = "556275"

-- 01
-- crie uma função que determine se uma string é anagrama de outra
anagrama :: [Char] -> [Char] -> Bool 
anagrama str1 str2 = sort str1 == sort str2
    where 
        sort [] = []
        sort (x:xs) = sort [y | y <- xs, y <= x] ++ [x] ++ sort [y | y <- xs, y > x]
-- teste 
-- $ anagrama rau loa
-- true

-- 02
-- construa função que elimine repetções de uma dada string s
--  sem alterar a sequência original 
-- dos caracteres de s.
unique :: [Char] -> [Char]
unique str = uniqueAux str []
  where
    uniqueAux [] _ = []
    uniqueAux (x:xs) seen
      | x `elem` seen = uniqueAux xs seen
      | otherwise     = x : uniqueAux xs (x:seen)

-- teste
-- $ unique "aabbxa" 
-- $ "abx"

-- 03
-- implemente uma função que determine a string formada pelos 
-- caracteres comuns a duas strins de entrada a e b. A saida não 
-- deve ter duplicadas.
intersec :: [Char] -> [Char] -> [Char] 
intersec a b = intersecAux a b []
  where
    intersecAux [] _ _ = []
    intersecAux (x:xs) b seen
      | x `elem` b && x `notElem` seen = x : intersecAux xs b (x:seen)
      | otherwise                      = intersecAux xs b seen
-- teste
-- $ intersec "abcd" "cdef"
-- $ "cd"

-- 04
-- dado três listas zipálas numa lista de triplas de forma 
-- semelhante ao comando zip. 
zip'linha :: [a] -> [b] -> [c] -> [(a, b, c)]
zip'linha (x:xs) (y:ys) (z:zs) = (x, y, z) : zip'linha xs ys zs
zip'linha _ _ _ = []
-- teste 01
-- zip'linha
-- $ [1,2,3] "abc" [TRUE,FALSE,TRUE] 
-- $ [(1,"a", TRUE), (2, "b", FALSE), (3, "c", TRUE)] 
-- teste 02
-- $ zip'linha [1,2,3,4] "abc" [TRUE] 
-- $ [(1,"a",TRUE)]