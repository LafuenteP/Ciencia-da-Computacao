-- Atividade 08
-- Nome: [Lafuente Paulino da Silva]
-- Matrícula: [556275]

-- árvore binária de busca (BST)
data BST a = Null
           | Node a (BST a) (BST a)
           deriving (Show, Eq)

-- Questão 1: Retorna a versão parentizada de uma BST dada
parentize :: BST Int -> String
parentize Null = ""
parentize (Node val Null Null) = "(" ++ show val ++ ")"
parentize (Node val left right) = "(" ++ show val ++ " " ++ parentize left ++ " " ++ parentize right ++ ")"

-- Questão 2: Constrói uma BST a partir da inserção consecutiva das chaves de uma lista de inteiros dada
addFromVec :: [Int] -> BST Int
addFromVec = foldr insert Null

insert :: Int -> BST Int -> BST Int
insert x Null = Node x Null Null
insert x (Node val left right)
  | x < val   = Node val (insert x left) right
  | x > val   = Node val left (insert x right)
  | otherwise = Node val left right  -- Evita inserir duplicatas

-- Questão 3: Calcula a altura de uma árvore binária de entrada
height :: BST a -> Int
height Null = 0
height (Node _ left right) = 1 + max (height left) (height right)
