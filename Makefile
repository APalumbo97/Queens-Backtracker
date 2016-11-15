# Makefile for Queens-Backtracking project.

compile: src/findSolution.java src/backtracker.java src/findSolution.java
	javac src/*.java -d out

clean:
	rm out/*.class

run: out/findSolution.class
	java -cp out findSolution input-files/empty.txt PTUI
