set QA=https://test.kuali.org/svn/student/deploymentlab/branches/1.0.0.x/core
set DEV=https://test.kuali.org/svn/student/ks-core/branches/ks-core-dev
call removeThenCopy %QA% %DEV% ks-common-impl
call removeThenCopy %QA% %DEV% ks-common-test
call removeThenCopy %QA% %DEV% ks-common-ui
call removeThenCopy %QA% %DEV% ks-common-util
call removeThenCopy %QA% %DEV% ks-core-impl
call removeThenCopy %QA% %DEV% ks-core-ui
call removeThenCopy %QA% %DEV% ks-core-web
