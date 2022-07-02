#!/bin/bash
#file getActivity.sh
package_name=$1
#launch app by package name
adb shell monkey -p ${package_name} -c android.intent.category.LAUNCHER 1;
sleep 1;
#get Activity name
adb shell logcat -d | grep 'START u0' | tail -n 1 | sed 's/.*cmp=\(.*\)} .*/\1/g'