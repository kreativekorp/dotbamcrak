SRCFILES := $(shell find src -name \*.java)
RESFILES := $(shell find src -type f \! -name .\* \! -name \*.java | sed s/^src\\///)
PACKAGES := $(shell find src -name \*.java | sed -E s/^src\\/\|\\/[^\\/]+$$//g | grep -v \\.java$$ | sort | uniq | tr / .)

all: clean bin doc DotBamCrak.jar DotBamCrak.app DotBamCrak.exe

eclipseall: eclipseclean DotBamCrak.jar DotBamCrak.app DotBamCrak.exe

clean:
	rm -rf bin
	rm -rf doc
	rm -rf DotBamCrak*.jar
	rm -rf DotBamCrak*.app
	rm -rf DotBamCrak*.exe

eclipseclean:
	rm -rf DotBamCrak*.jar
	rm -rf DotBamCrak*.app
	rm -rf DotBamCrak*.exe

bin:
	mkdir -p bin
ifeq ($(origin JAVACFLAGS), undefined)
	$(eval JAVACFLAGS = $(shell dep/minJavaVerOpts))
endif
	javac $(JAVACFLAGS) -sourcepath src $(SRCFILES) -d bin
	$(foreach res,$(RESFILES),cp src/$(res) bin/$(res);)

doc: src
	mkdir -p doc
	javadoc $(JAVADOCFLAGS) -sourcepath src $(PACKAGES) -d doc

DotBamCrak.jar: bin
	export COPYFILE_DISABLE=true
	find bin -name .DS_Store -delete
	jar cmf dep/MANIFEST.MF DotBamCrak.jar -C bin com/kreative/dotbamcrak
	chmod +x DotBamCrak.jar

DotBamCrak.app: DotBamCrak-Pre10.15.app DotBamCrak-MacOS10.15.app DotBamCrak-MacOS11.0.app

DotBamCrak-Pre10.15.app: dep DotBamCrak.jar
	mkdir -p DotBamCrak-Pre10.15.app/Contents/MacOS
	mkdir -p DotBamCrak-Pre10.15.app/Contents/Resources/Java
	cp dep/PkgInfo DotBamCrak-Pre10.15.app/Contents
	cp dep/Info.plist DotBamCrak-Pre10.15.app/Contents
	cp dep/universalJavaApplicationStub-Pre10.15 DotBamCrak-Pre10.15.app/Contents/MacOS/DotBamCrak
	cp dep/DotBamCrak.icns DotBamCrak-Pre10.15.app/Contents/Resources
	cp DotBamCrak.jar DotBamCrak-Pre10.15.app/Contents/Resources/Java

DotBamCrak-MacOS10.15.app: dep DotBamCrak.jar
	mkdir -p DotBamCrak-MacOS10.15.app/Contents/MacOS
	mkdir -p DotBamCrak-MacOS10.15.app/Contents/Resources/Java
	cp dep/PkgInfo DotBamCrak-MacOS10.15.app/Contents
	cp dep/Info.plist DotBamCrak-MacOS10.15.app/Contents
	cp dep/universalJavaApplicationStub-MacOS10.15 DotBamCrak-MacOS10.15.app/Contents/MacOS/DotBamCrak
	cp dep/DotBamCrak.icns DotBamCrak-MacOS10.15.app/Contents/Resources
	cp DotBamCrak.jar DotBamCrak-MacOS10.15.app/Contents/Resources/Java

DotBamCrak-MacOS11.0.app: dep DotBamCrak.jar
	mkdir -p DotBamCrak-MacOS11.0.app/Contents/MacOS
	mkdir -p DotBamCrak-MacOS11.0.app/Contents/Resources/Java
	cp dep/PkgInfo DotBamCrak-MacOS11.0.app/Contents
	cp dep/Info.plist DotBamCrak-MacOS11.0.app/Contents
	cp dep/universalJavaApplicationStub-MacOS11.0 DotBamCrak-MacOS11.0.app/Contents/MacOS/DotBamCrak
	cp dep/DotBamCrak.icns DotBamCrak-MacOS11.0.app/Contents/Resources
	cp DotBamCrak.jar DotBamCrak-MacOS11.0.app/Contents/Resources/Java

DotBamCrak.exe: dep DotBamCrak.jar
ifeq ($(LAUNCH4J_HOME),)
	echo 'Please set $$LAUNCH4J_HOME environment variable to compile exe.'
else
	cd "$(LAUNCH4J_HOME)" ; java -Dlaunch4j.tmpdir=/tmp/ -jar "$(LAUNCH4J_HOME)/launch4j.jar" "$(PWD)/dep/DotBamCrak.xml"
endif

install: dep DotBamCrak.jar
	sudo mkdir -p /usr/local/lib/
	sudo cp -f DotBamCrak.jar /usr/local/lib/dotbamcrak.jar
	sudo chmod -f 644 /usr/local/lib/dotbamcrak.jar
	sudo mkdir -p /usr/local/bin/
	sudo cp -f dep/dotbamcrak /usr/local/bin/dotbamcrak
	sudo chmod -f 755 /usr/local/bin/dotbamcrak
	sudo mkdir -p /usr/share/icons/hicolor/128x128/apps/
	sudo mkdir -p /usr/share/icons/hicolor/256x256/apps/
	sudo mkdir -p /usr/share/icons/hicolor/512x512/apps/
	sudo cp -f dep/DotBamCrak128.png /usr/share/icons/hicolor/128x128/apps/dotbamcrak.png
	sudo cp -f dep/DotBamCrak256.png /usr/share/icons/hicolor/256x256/apps/dotbamcrak.png
	sudo cp -f dep/DotBamCrak512.png /usr/share/icons/hicolor/512x512/apps/dotbamcrak.png
	sudo chmod -f 644 /usr/share/icons/hicolor/128x128/apps/dotbamcrak.png
	sudo chmod -f 644 /usr/share/icons/hicolor/256x256/apps/dotbamcrak.png
	sudo chmod -f 644 /usr/share/icons/hicolor/512x512/apps/dotbamcrak.png
	sudo mkdir -p /usr/share/applications/
	sudo cp -f dep/dotbamcrak.desktop /usr/share/applications/dotbamcrak.desktop
	sudo chmod -f 644 /usr/share/applications/dotbamcrak.desktop
	sudo mkdir -p /usr/local/share/dotbamcrak/
	sudo cp -Rf Fortunes Layouts Tile\ Sets /usr/local/share/dotbamcrak/

.PHONY: all eclipseall clean eclipseclean DotBamCrak.app install
