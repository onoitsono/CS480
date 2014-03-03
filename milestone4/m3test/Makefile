LEX=LexAn
PAR=Parser
TOK=Tokens
TRAN=Translator
STUTEST=stutest
PROFTEST=proftest

all: compiler ${STUTEST}.out

compiler: ${LEX}.java ${PAR}.java ${TOK}.java ${TRAN}.java 
	javac ${LEX}.java ${PAR}.java ${TOK}.java ${TRAN}.java

${STUTEST}.out: compiler ${STUTEST}.in
	java ${TRAN} stutest1 stutest2 stutest3 stutest4 stutest5 stutest6 stutest7 stutest8 stutest9 stutest10 stutest11 stutest12 stutest13 stutest14 stutest15 stutest16 stutest17 stutest18 stutest19 stutest20 stutest21 stutest22 stutest23 > ${STUTEST}.out

${PROFTEST}.out: compiler ${PROFTEST}.in
	java ${TRAN} ${PROFTEST}.in > ${PROFTEST}.out

clean:
	@rm -f *.class *.out

