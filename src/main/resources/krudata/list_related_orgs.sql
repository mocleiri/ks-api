select
A.ID AS ID1,
A.SHRT_NAME AS NAME1,
A.TYPE AS TYPE1,
A.ST AS STATE1,
R.TYPE AS RELATION_TYPE,
B.ID AS ID2,
B.SHRT_NAME AS NAME2,
B.TYPE AS TYPE2,
B.ST AS STATE2
FROM KSOR_ORG A,
KSOR_ORG B,
KSOR_ORG_ORG_RELTN R
WHERE A.ID = R.ORG
AND B.ID = R.RELATED_ORG













