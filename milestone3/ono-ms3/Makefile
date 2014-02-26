CCC=LexAn
STUTEST=stutest
PROFTEST=proftest

all: compiler ${STUTEST}.out

compiler: ${CCC}.java
	javac ${CCC}.java

${STUTEST}.out: compiler ${STUTEST}.in
	java ${CCC} ${STUTEST}.in > ${STUTEST}.out

${PROFTEST}.out: compiler ${PROFTEST}.in
	java ${CCC} ${PROFTEST}.in > ${PROFTEST}.out

clean:
	@rm -f *.class *.out

