---
-- Delete any unique constraints (and their matching index) with auto-generated names and replace them with properly named constraints
---

BEGIN
  EXECUTE IMMEDIATE 'ALTER TABLE KSCO_REFERENCE DROP CONSTRAINT SYS_C0033779'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -02443 THEN RAISE; END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'DROP INDEX SYS_C0033779'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -01418 THEN RAISE; END IF;
END;
/

ALTER TABLE KSCO_REFERENCE ADD CONSTRAINT KSCO_REFERENCE_U1 UNIQUE (REFERENCE_ID, REFERENCE_TYPE)
/

BEGIN
  EXECUTE IMMEDIATE 'ALTER TABLE KSOR_ORG_PERS_RELTN DROP CONSTRAINT SYS_C0011691'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -02443 THEN RAISE; END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'DROP INDEX SYS_C0011691'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -01418 THEN RAISE; END IF;
END;
/

ALTER TABLE KSOR_ORG_PERS_RELTN ADD CONSTRAINT KSOR_ORG_PERS_RELTN_U1 UNIQUE (ORG_PERS_RELTN_TYPE, ORG, PERS_ID)
/

BEGIN
  EXECUTE IMMEDIATE 'ALTER TABLE KSOR_ORG_POS_RESTR DROP CONSTRAINT SYS_C0011701'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -02443 THEN RAISE; END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'DROP INDEX SYS_C0011701'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -01418 THEN RAISE; END IF;
END;
/

ALTER TABLE KSOR_ORG_POS_RESTR ADD CONSTRAINT KSOR_ORG_POS_RESTR_U1 UNIQUE (ORG, PERS_RELTN_TYPE)
/

BEGIN
  EXECUTE IMMEDIATE 'ALTER TABLE KSST_OBJ_TYP_JN_OBJ_SUB_TYP DROP CONSTRAINT SYS_C0011792'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -02443 THEN RAISE; END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'DROP INDEX SYS_C0011792'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -01418 THEN RAISE; END IF;
END;
/

ALTER TABLE KSST_OBJ_TYP_JN_OBJ_SUB_TYP ADD CONSTRAINT KSST_OBJ_TYP_JN_OBJ_SUB_TYP_U1 UNIQUE (OBJ_SUB_TYPE_ID)
/

BEGIN
  EXECUTE IMMEDIATE 'ALTER TABLE KSST_RC_JN_RC_FIELD DROP CONSTRAINT SYS_C0011797'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -02443 THEN RAISE; END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'DROP INDEX SYS_C0011797'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -01418 THEN RAISE; END IF;
END;
/

ALTER TABLE KSST_RC_JN_RC_FIELD ADD CONSTRAINT KSST_RC_JN_RC_FIELD_U1 UNIQUE (REQ_COM_FIELD_ID)
/

BEGIN
  EXECUTE IMMEDIATE 'ALTER TABLE KSST_STMT_JN_STMT DROP CONSTRAINT SYS_C0011839'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -02443 THEN RAISE; END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'DROP INDEX SYS_C0011839'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -01418 THEN RAISE; END IF;
END;
/

ALTER TABLE KSST_STMT_JN_STMT ADD CONSTRAINT KSST_STMT_JN_STMT_U1 UNIQUE (CHLD_STMT_ID)
/

BEGIN
  EXECUTE IMMEDIATE 'ALTER TABLE KSEM_CTX_T DROP CONSTRAINT SYS_C00285110'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -02443 THEN RAISE; END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'DROP INDEX SYS_C00285110'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -01418 THEN RAISE; END IF;
END;
/

ALTER TABLE KSEM_CTX_T ADD CONSTRAINT KSEM_CTX_T_U1 UNIQUE (CTX_KEY, CTX_VAL)
/

---
-- Rename foreign key constraints with auto-generated names
---

ALTER TABLE KSEN_MSTONE_ATTR RENAME CONSTRAINT FK3DFA6EE1BA0FC113 TO KSEN_MSTONE_ATTR_FK1
/