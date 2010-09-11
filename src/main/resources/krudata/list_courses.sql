SELECT
a.id,
b.lng_name,
b.cd,
b.lvl,
c.plain,
d.org_Id
FROM kslu_clu a,
kslu_clu_ident b,
kslu_rich_text_t c,
kslu_clu_admin_org d
WHERE a.st NOT IN ('draft')
AND a.luType_id = 'kuali.lu.type.CreditCourse'
AND a.st = 'Active'
AND (LOWER(b.div) LIKE LOWER('ARTS') || '%' OR LOWER(b.div) LIKE '% ' || LOWER('ARTS') || '%')
and a.OFFIC_CLU_ID = b.id
and a.RT_DESCR_ID = c.id
and a.PRI_ADMIN_ORG_ID = d.id
