webautomator
============
[![Build Status](https://travis-ci.org/gigony/webautomator.svg)](https://travis-ci.org/gigony/webautomator)

A framework for Web testing automation (in development).

### Introduction

### Requirements

- [Java SE](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [Gradle](http://www.gradle.org)
- [PhantomJS](http://phantomjs.org/)
- [FireFox](https://www.mozilla.org/en-US/firefox/new/)

#### Prerequisite for OSX
- Java Settings after install JDK 8 (in .bash_profile)

        # set java (http://www.jayway.com/2014/01/15/how-to-switch-jdk-version-on-mac-os-x-maverick/)
        function setjdk() {
          if [ $# -ne 0 ]; then
           removeFromPath '/System/Library/Frameworks/JavaVM.framework/Home/bin'
           if [ -n "${JAVA_HOME+x}" ]; then
            removeFromPath $JAVA_HOME
           fi
           export JAVA_HOME=`/usr/libexec/java_home -v $@`
           export PATH=$JAVA_HOME/bin:$PATH
          fi
         }
         function removeFromPath() {
          export PATH=$(echo $PATH | sed -E -e "s;:$1;;" -e "s;$1:?;;")
         }
        setjdk 1.8
- [Homebrew](http://brew.sh/) 
  - `ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`
- [PhantomJS](http://phantomjs.org/)
  - `brew install phantomjs`
- [Gradle](http://www.gradle.org)
  - `brew install gradle`


### Installation & Build

  `git clone https://github.com/gigony/webautomator.git`
  
  `cd webautomator`
  
  `gradle build`   or `./gradlew build`

### Usage

`TODO - describe usage` 


Refer to [test code](webautomator-core/src/test/java/edu/unl/qte/core) for further information.

License
-------
webautomator is distributed under the [Apache License](http://www.apache.org/licenses/LICENSE-2.0.html).

