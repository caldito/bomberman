all: build

build: 
	mkdir -p bin
	find -name "*.java" > bin/sources.txt
	javac -cp lib/GameBoardV3.jar -d bin @bin/sources.txt
	cp -r images bin

run: build
	java -cp bin bomberman.Game

clean: 
	rm -rf bin
