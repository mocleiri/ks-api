set QA=https://test.kuali.org/svn/student/deploymentlab/branches/1.0.0.x/lum
set DEV=https://test.kuali.org/svn/student/ks-lum/branches/ks-lum-dev
call removeThenCopy %QA% %DEV% ks-lum-api
call removeThenCopy %QA% %DEV% ks-lum-impl
call removeThenCopy %QA% %DEV% ks-lum-rice
call removeThenCopy %QA% %DEV% ks-lum-ui
call removeThenCopy %QA% %DEV% ks-lum-web
