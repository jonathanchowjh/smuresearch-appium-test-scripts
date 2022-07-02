#!/bin/zsh

export ANDROID_HOME="/Users/hartonotjakrawinata/Library/Android/sdk";
export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_333.jdk/Contents/Home";
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8.0_333)
export PATH=$PATH:$ANDROID_HOME/tools;
export PATH=$PATH:$ANDROID_HOME/platform-tools;
