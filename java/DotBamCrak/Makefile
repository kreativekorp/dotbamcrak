SRCFILES = src/com/kreative/dotbamcrak/*/*.java

all: clean bin osxclean DotBamCrak.jar DotBamCrak.app DotBamCrak.exe

eclipseall: eclipseclean osxclean DotBamCrak.jar DotBamCrak.app DotBamCrak.exe

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
	javac -target 1.5 -sourcepath src $(SRCFILES) -d bin

osxclean:
	rm -f src/.DS_Store
	rm -f src/*/.DS_Store
	rm -f src/*/*/.DS_Store
	rm -f src/*/*/*/.DS_Store
	rm -f src/*/*/*/*/.DS_Store
	rm -f src/*/*/*/*/*/.DS_Store
	rm -f src/*/*/*/*/*/*/.DS_Store
	rm -f src/*/*/*/*/*/*/*/.DS_Store
	rm -f src/*/*/*/*/*/*/*/*/.DS_Store
	rm -f bin/.DS_Store
	rm -f bin/*/.DS_Store
	rm -f bin/*/*/.DS_Store
	rm -f bin/*/*/*/.DS_Store
	rm -f bin/*/*/*/*/.DS_Store
	rm -f bin/*/*/*/*/*/.DS_Store
	rm -f bin/*/*/*/*/*/*/.DS_Store
	rm -f bin/*/*/*/*/*/*/*/.DS_Store
	rm -f bin/*/*/*/*/*/*/*/*/.DS_Store

DotBamCrak.jar: osxclean
	jar cmf dep/MANIFEST.MF DotBamCrak.jar -C bin com/kreative/dotbamcrak

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

.PHONY: all eclipseall clean eclipseclean osxclean
