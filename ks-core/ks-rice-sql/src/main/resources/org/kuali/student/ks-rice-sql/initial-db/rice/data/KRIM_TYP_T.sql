TRUNCATE TABLE KRIM_TYP_T DROP STORAGE
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '1', 'Default', 'KUALI', '5ADF18B6D4827954E0404F8189D85002', 1, null)
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10', 'Namespace or Component', 'KR-NS', '5ADF18B6D4D77954E0404F8189D85002', 1, 'namespaceOrComponentPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10000', 'KS Permission', 'KS-SYS', '1F81F05B5CF34104ACA44835D23F1B02', 1, 'permissionPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10001', 'Field Permission Type', 'KS-SYS', '6BB6E5FF4A84453EA1917E69FF0CF644', 1, null)
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10002', 'Derived Role: KS Non-Adhoc Action Request Role Type', 'KS-SYS', 'DA7BC49509DA446F89EA27EC3BBBBB33', 1, 'ksNonAdhocActionRequestDerivedRoleTypeServiceImpl')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10003', 'Derived Role: KS Route Log Role Type', 'KS-SYS', 'A5703EF5E89245BF8B9D53F2C92B52C0', 1, 'ksRouteLogDerivedRoleTypeServiceImpl')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10004', 'KS Use Screen', 'KS-SYS', 'BB5E34C754F7480E96D66EA4CEDAE1CC', 1, null)
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10005', 'Derived Role: KS Document Editor Role Type', 'KS-SYS', 'E11230E220D44F5F941A6E1F8FA58B9F', 1, 'ksEditDocumentRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10006', 'Derived Role: KS Document Commenter Role Type', 'KS-SYS', '6672DB350D2B4B10BC0576964C841EF7', 1, 'ksCommentOnDocumentRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10007', 'Derived Role: Affiliation Role Type', 'KS-SYS', '5770DDE11B57425EA4A1E9C680393AB7', 1, 'ksAffiliationService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10008', 'College Type', 'KS-CM', 'A33B989E890D4E0E957D46F4E7F393D0', 1, 'kimRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10009', 'Department Type', 'KS-CM', '8805C2ABC9B7424F9B6BC0D727D3587A', 1, 'kimRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10010', 'Division Type', 'KS-CM', 'F35A0985ED2C4993B309746F83CFFD45', 1, 'kimRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10011', 'Derived Role: Org Admin Type', 'KS-SYS', 'A20E7508989E4C7CBC06B5238CFEFBFD', 1, 'ksOrgAdminRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10012', 'Derived Role: Org Committee Type', 'KS-SYS', '74B9AA4080D340369E940F223A651CB0', 1, 'ksOrgCommitteeRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10013', 'Derived Role: KS Adhoc Action Request Role Type', 'KS-SYS', '41F8A3984A924E93A0EEBCB1567C769E', 1, 'ksAdhocActionRequestDerivedRoleTypeServiceImpl')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10014', 'Adhoc Permissions Type', 'KS-SYS', 'F7E28DC5594D4F1F8AF1C6F81D93F8B5', 1, 'kimRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10015', 'Derived Role: KS Completed Adhoc Action Request Role Type', 'KS-SYS', '64517DC4265D4F9A8DB4E4A1F3AE5899', 1, 'ksAdhocDoneActionRequestDerivedRoleTypeServiceImpl')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10016', 'Derived Role: KS Completed Non-Adhoc Action Request Role Type', 'KS-SYS', '25BB3A80078F4CCCB81E6733DDE21A5E', 1, 'ksNonAdhocDoneActionRequestDerivedRoleTypeServiceImpl')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '10017', 'Document Type and Route Node', 'KS-SYS', '7779A0C257B64A9A8D4AB628BF8B9FAE', 1, null)
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '101', 'College Type', 'KS-CM', 'COLLEGETYPE000000000000000000TYP', 1, 'kimRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '102', 'Department Type', 'KS-CM', 'DEPARTMTYPE000000000000000000TYP', 1, 'kimRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '103', 'Division Type', 'KS-CM', 'DIVISIONTYPE00000000000000000TYP', 1, 'kimRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '104', 'Reference Type Permission', 'KS-SYS', 'DIVISIONTYPE00000000000000000TYO', 1, 'kimPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '107', 'Derived Role: Org Admin Type', 'KS-SYS', 'ORGADMINDERIVED00000000000000TYP', 1, 'ksOrgAdminRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '108', 'Derived Role: Org Committee Type', 'KS-SYS', 'ORGCOMMITTEEDERIVED0000000000TYP', 1, 'ksOrgCommitteeRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '109', 'Derived Role: KS Non-Adhoc Action Request Role Type', 'KS-SYS', 'ORGADMINDERIVED000000T0000000TYP', 1, 'ksNonAdhocActionRequestDerivedRoleTypeServiceImpl')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '11', 'Component Field', 'KR-NS', '5ADF18B6D4DC7954E0404F8189D85002', 1, 'componentFieldPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '110', 'Derived Role: KS Route Log Role Type', 'KS-SYS', 'ORGCOMMITTEEDERIVED0000000T00TYP', 1, 'ksRouteLogDerivedRoleTypeServiceImpl')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '111', 'Derived Role: KS Document Editor', 'KS-SYS', 'ORGCOMMITTEEDERIVED3600100T00TYP', 1, 'ksEditDocumentRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '112', 'Derived Role: KS Document Commenter', 'KS-SYS', 'ORGCOMMITTEEDERIVED3600129T00TYP', 1, 'ksCommentOnDocumentRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '113', 'Derived Role: KS Adhoc Action Request Role Type', 'KS-SYS', 'ORGCOMMITTEEDERIVED3689129T00TYP', 1, 'ksAdhocActionRequestDerivedRoleTypeServiceImpl')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '114', 'Organization Role Type', 'KS-SYS', 'DEPARTMTYPE000930000000012300TYP', 1, 'ksOrganizationHierarchyRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '115', 'Derived Role: KS Completed Non-Adhoc Action Request Role Type', 'KS-SYS', 'ORGADMINDERIVEDED0000T0000000TYP', 1, 'ksNonAdhocDoneActionRequestDerivedRoleTypeServiceImpl')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '116', 'Derived Role: KS Completed Adhoc Action Request Role Type', 'KS-SYS', 'ORGCOMMWSXEEDEIVED3689129T00TYP1', 1, 'ksAdhocDoneActionRequestDerivedRoleTypeServiceImpl')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '12', 'Namespace or Action', 'KR-NS', '5ADF18B6D4E37954E0404F8189D85002', 1, 'namespaceOrActionPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '120', 'Field Permission Type', 'KS-SYS', 'FIELDPERMISSION0000000000000TYP', 1, null)
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '121', 'Section Maintenance Permission Type', 'KS-SYS', 'DEPARTMTYPE00093A106540012300TYP', 1, 'kimPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '13', 'Button', 'KR-NS', '5ADF18B6D4E77954E0404F8189D85002', 1, 'buttonPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '130', 'Adhoc Permissions Type', 'KS-SYS', 'DEPARUUUNTADMINREVIEWER000001237', 1, 'kimRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '131', 'View', 'KR-KRAD', 'D2F07FCB2D00FFC9E040760A4A45207E', 1, 'viewPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '132', 'View Edit Mode', 'KR-KRAD', 'D2F07FCB2D01FFC9E040760A4A45207E', 1, 'viewEditModePermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '133', 'View Field', 'KR-KRAD', 'D2F07FCB2D02FFC9E040760A4A45207E', 1, 'viewFieldPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '134', 'View Group', 'KR-KRAD', 'D2F07FCB2D03FFC9E040760A4A45207E', 1, 'viewGroupPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '135', 'View Widget', 'KR-KRAD', 'D2F07FCB2D04FFC9E040760A4A45207E', 1, 'viewWidgetPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '136', 'View Action', 'KR-KRAD', 'D2F07FCB2D05FFC9E040760A4A45207E', 1, 'viewActionPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '137', 'View Line Field', 'KR-KRAD', 'D2F07FCB2D06FFC9E040760A4A45207E', 1, 'viewLineFieldPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '138', 'View Line Action', 'KR-KRAD', 'D2F07FCB2D07FFC9E040760A4A45207E', 1, 'viewLineActionPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '139', 'Derived Role: Permission (Route Document)', 'KR-WKFLW', 'D2F07FCB2D35FFC9E040760A4A45207E', 1, 'documentRouterRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '14', 'Edit Mode & Document Type', 'KR-NS', '5ADF18B6D4EA7954E0404F8189D85002', 1, 'documentTypeAndEditModePermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '15', 'Batch Feed or Job', 'KR-NS', '5ADF18B6D4ED7954E0404F8189D85002', 1, 'batchFeedOrJobPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '16', 'Parameter', 'KR-NS', '5ADF18B6D4F27954E0404F8189D85002', 1, 'parameterPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '17', 'Campus', 'KR-NS', '5ADF18B6D4F77954E0404F8189D85002', 1, 'campusRoleService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '18', 'Role', 'KR-IDM', '5ADF18B6D4F97954E0404F8189D85002', 1, 'rolePermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '19', 'Permission', 'KR-IDM', '5ADF18B6D4FD7954E0404F8189D85002', 1, 'permissionPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '2', 'Derived Role: Principal', 'KR-IDM', '5ADF18B6D4837954E0404F8189D85002', 1, 'activePrincipalRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '20', 'Responsibility', 'KR-IDM', '5ADF18B6D5017954E0404F8189D85002', 1, 'responsibilityPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '21', 'Group', 'KR-IDM', '5ADF18B6D5057954E0404F8189D85002', 1, 'groupPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '3', 'Document Type (Permission)', 'KR-SYS', '5ADF18B6D4AC7954E0404F8189D85002', 1, 'documentTypePermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '4', 'Action Request Type', 'KR-WKFLW', '5ADF18B6D4B57954E0404F8189D85002', 1, 'actionRequestTypePermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '42', 'Derived Role: Action Request', 'KR-WKFLW', '5ADF18B6D53B7954E0404F8189D85002', 1, 'actionRequestDerivedRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '43', 'Derived Role: Route Log', 'KR-WKFLW', '5ADF18B6D53C7954E0404F8189D85002', 1, 'routeLogDerivedRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '45', 'Derived Role: Permission (Edit Document)', 'KR-NS', '5B6013B3AD131A9CE0404F8189D87094', 1, 'documentEditorRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '5', 'Ad Hoc Review', 'KR-WKFLW', '5ADF18B6D4B87954E0404F8189D85002', 1, 'adhocReviewPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '52', 'Document Type, Routing Node & Field(s)', 'KR-SYS', '5C997D14EAC2FE40E0404F8189D87DC5', 1, 'documentTypeAndNodeAndFieldsPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '54', 'Document Type (Responsibility)', 'KR-KEW', '60432A73A6A29F65E0404F8189D86AA4', 1, 'documentTypeResponsibilityTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '56', 'Document Type & Existing Records Only', 'KR-NS', '603B735FA6B9FE21E0404F8189D8083B', 1, 'documentTypeAndExistingRecordsOnlyPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '57', 'Component Section', 'KR-NS', '603B735FA6BDFE21E0404F8189D8083B', 1, 'componentSectionPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '59', 'Document Type & Relationship to Note Author', 'KR-NS', '606763510FBB96D3E0404F8189D857A2', 1, 'documentTypeAndRelationshipToNoteAuthorPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '60', 'Derived Role: Permission (Open Document)', 'KR-NS', '606763510FBC96D3E0404F8189D857A2', 1, 'documentOpenerRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '66', 'Derived Role: Permission (Initiate Document)', 'KR-SYS', '67F145466E8A9160E0404F8189D86771', 1, 'documentInitiatorRoleTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '67', 'Namespace', 'KR-NS', '67F145466E8F9160E0404F8189D86771', 1, 'namespacePermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '7', 'Document Type, Routing Node & Action Information', 'KR-WKFLW', '5ADF18B6D4C07954E0404F8189D85002', 1, 'reviewResponsibilityTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '8', 'Document Type & Routing Node or State', 'KR-SYS', '5ADF18B6D4C67954E0404F8189D85002', 1, 'documentTypeAndNodeOrStatePermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', '9', 'Document Type & Attachment Type', 'KR-NS', '5ADF18B6D4CD7954E0404F8189D85002', 1, 'documentTypeAndAttachmentTypePermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', 'KR1000', 'Document Type, Route Node, and Route Status', 'KR-SYS', 'D2F07FCB2D60FFC9E040760A4A45207E', 1, 'documentTypeAndNodeAndRouteStatusPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, VER_NBR, SRVC_NM)
  VALUES ('Y', 'KR1001', 'Backdoor Restriction', 'KR-SYS', 'D2F07FCB2D67FFC9E040760A4A45207E', 1, 'backdoorRestrictionPermissionTypeService')
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','68','View','KR-KRAD','B7DBFABEFD378CBFE0402E0AA9D757C9','viewPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','69','View Edit Mode','KR-KRAD','B7DBFABEFD388CBFE0402E0AA9D757C9','viewEditModePermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','70','View Field','KR-KRAD','B7DBFABEFD398CBFE0402E0AA9D757C9','viewFieldPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','71','View Group','KR-KRAD','B7DBFABEFD3A8CBFE0402E0AA9D757C9','viewGroupPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','72','View Widget','KR-KRAD','B7DBFABEFD3B8CBFE0402E0AA9D757C9','viewWidgetPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','73','View Action','KR-KRAD','B7DBFABEFD3C8CBFE0402E0AA9D757C9','viewActionPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','74','View Line Field','KR-KRAD','B7DBFABEFD3D8CBFE0402E0AA9D757C9','viewLineFieldPermissionTypeService',1)
/
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','75','View Line Action','KR-KRAD','B7DBFABEFD3E8CBFE0402E0AA9D757C9','viewLineActionPermissionTypeService',1)
/