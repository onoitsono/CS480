LEX=LexAn
PAR=Parser
TOK=Tokens
TRAN=Translator
NODE=Nodes
PARTR=ParseTree
STUTEST=stutest
PROFTEST=proftest

all: compiler ${STUTEST}.out

compiler: ${LEX}.java ${PAR}.java ${TOK}.java ${NODE}.java ${PARTR}.java ${TRAN}.java 
	javac ${LEX}.java ${PAR}.java ${TOK}.java ${NODE}.java ${PARTR}.java ${TRAN}.java

${STUTEST}.out: compiler ${STUTEST}.in
	java ${TRAN} ${STUTEST}.in > ${STUTEST}.out

${PROFTEST}.out: compiler ${PROFTEST}.in
	java ${TRAN} ${PROFTEST}.in > ${PROFTEST}.out

clean:
	@rm -f *.class *.out

