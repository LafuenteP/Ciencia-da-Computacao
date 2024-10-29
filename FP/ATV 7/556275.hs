-- Atividade 07

atividade = "07"
nome = "Lafuente Paulino da Silva"
matricula = "556275"

data Stack a = Empty | Value a (Stack a) deriving Show

push :: Stack a -> a -> Stack a
push stack x = Value x stack

pop :: Stack a -> Stack a
pop Empty = error "Pilha vazia!"
pop (Value _ stack) = stack

top :: Stack a -> Maybe a
top Empty = Nothing
top (Value x _) = Just x

is'empty :: Stack a -> Bool
is'empty Empty = True
is'empty _ = False

precedence :: Char -> Int
precedence '^' = 3
precedence '*' = 2
precedence '/' = 2
precedence '+' = 1
precedence '-' = 1
precedence _   = 0

pos'fixa :: [Char] -> [Char]
pos'fixa expr = pos'fixaAux expr Empty ""

pos'fixaAux :: [Char] -> Stack Char -> [Char] -> [Char]
pos'fixaAux [] stack output = output ++ dumpStack stack
pos'fixaAux (x:xs) stack output
    | x >= 'a' && x <= 'z' = pos'fixaAux xs stack (output ++ [x])
    | x >= 'A' && x <= 'Z' = pos'fixaAux xs stack (output ++ [x])
    | x == '(' = pos'fixaAux xs (push stack x) output
    | x == ')' = let (newStack, newOutput) = popTillOpenParen stack output in pos'fixaAux xs newStack newOutput
    | otherwise = let (newStack, newOutput) = pushOperator x stack output in pos'fixaAux xs newStack newOutput

popTillOpenParen :: Stack Char -> [Char] -> (Stack Char, [Char])
popTillOpenParen Empty output = (Empty, output)
popTillOpenParen (Value '(' stack) output = (stack, output)
popTillOpenParen (Value x stack) output = popTillOpenParen stack (output ++ [x])

pushOperator :: Char -> Stack Char -> [Char] -> (Stack Char, [Char])
pushOperator op Empty output = (push Empty op, output)
pushOperator op (Value topOp stack) output
    | precedence op > precedence topOp = (push (Value topOp stack) op, output)
    | otherwise = let (newStack, newOutput) = pushOperator op stack (output ++ [topOp]) in (newStack, newOutput)

dumpStack :: Stack Char -> [Char]
dumpStack Empty = ""
dumpStack (Value x stack) = x : dumpStack stack
