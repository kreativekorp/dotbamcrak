SRCFILES = src/com/kreative/dotbamcrak/*/*.java

all: clean bin DotBamCrak.jar DotBamCrak.app DotBamCrak.exe

eclipseall: eclipseclean DotBamCrak.jar DotBamCrak.app DotBamCrak.exe

clean:
	rm -rf bin
	rm -rf DotBamCrak.jar
	rm -rf DotBamCrak.app
	rm -rf DotBamCrak.exe

eclipseclean:
	rm -rf DotBamCrak.jar
	rm -rf DotBamCrak.app
	rm -rf DotBamCrak.exe

bin:
	mkdir -p bin
ifeq ($(origin JAVACFLAGS), undefined)
	$(eval JAVACFLAGS = $(shell dep/minJavaVerOpts))
endif
	javac $(JAVACFLAGS) -sourcepath src $(SRCFILES) -d bin

DotBamCrak.jar: bin
	export COPYFILE_DISABLE=true
	find bin -name .DS_Store -delete
	jar cmf dep/MANIFEST.MF DotBamCrak.jar -C bin com/kreative/dotbamcrak
	chmod +x DotBamCrak.jar

DotBamCrak.app: DotBamCrak.jar
	mkdir -p DotBamCrak.app/Contents/MacOS
	mkdir -p DotBamCrak.app/Contents/Resources/Java
	cp dep/JavaApplicationStub DotBamCrak.app/Contents/MacOS/DotBamCrak
	cp DotBamCrak.jar DotBamCrak.app/Contents/Resources/Java
	cp dep/DotBamCrak.icns DotBamCrak.app/Contents/Resources
	cp dep/Info.plist DotBamCrak.app/Contents
	cp dep/PkgInfo DotBamCrak.app/Contents

DotBamCrak.exe: DotBamCrak.jar
ifeq ($(LAUNCH4J_HOME),)
	echo 'Please set $$LAUNCH4J_HOME environment variable to compile exe.'
else
	cd "$(LAUNCH4J_HOME)" ; java -Dlaunch4j.tmpdir=/tmp/ -jar "$(LAUNCH4J_HOME)/launch4j.jar" "$(PWD)/dep/DotBamCrak.xml"
endif

.PHONY: all eclipseall clean eclipseclean
