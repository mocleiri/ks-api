cd ..\..\ks-security-dev
svn update
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties clean install

cd ..\ks-core-dev
svn update
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties clean install

cd ..\brms-dev
svn update
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties clean install

cd ..\ks-lum-dev
svn update
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties clean install

cd ..\ks-web-dev
svn update
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties clean install

cd ..\maven\resources
