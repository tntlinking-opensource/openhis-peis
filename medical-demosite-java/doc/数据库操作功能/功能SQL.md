##        

```sql
DELETE
FROM md_peispatientfeeitemon
WHERE (user_no, item_id) IN (SELECT user_no,
                                    item_id
                             FROM (SELECT user_no,
                                          item_id,
                                          ROW_NUMBER() OVER ( PARTITION BY user_no, item_id ORDER BY user_no ) AS rn
                                   FROM md_peispatientfeeitemon
                                   WHERE id_patient IS NULL) AS duplicates
                             WHERE rn > 1);


SELECT *
FROM (SELECT id_patient,
             id_examfeeitem,
             COUNT(*) AS count
      FROM
          md_peispatientfeeitem
      WHERE
          ( f_registered = 0
         OR f_registered IS NULL ) -- 								AND id_patient = 'APP0230003749'

      GROUP BY
          id_patient,
          id_examfeeitem
      HAVING
          COUNT (*)
           > 1) a
GROUP BY id_patient;


SELECT id_patient,
       id_examfeeitem
FROM md_peispatientfeeitemon
WHERE id_patient = 'APP0230003930'
ORDER BY id_examfeeitem

--去重操作
DELETE
FROM md_peispatientfeeitemon
WHERE id IN (SELECT duplicates.id
             FROM (SELECT id,
                          id_patient,
                          id_examfeeitem,
                          ROW_NUMBER() OVER (PARTITION BY id_patient, id_examfeeitem ORDER BY id_patient) AS rn
                   FROM md_peispatientfeeitemon
                   WHERE f_registered = 0
                      OR f_registered IS NULL) AS duplicates
             WHERE duplicates.rn > 1);


--体检者收费项目去重操作
DELETE
FROM md_peispatientfeeitem
WHERE id IN (SELECT id
             FROM (SELECT bbb.*,
                          ROW_NUMBER() OVER ( PARTITION BY bbb.id_patient, bbb.id_examfeeitem ORDER BY bbb.id_patient, bbb.f_mark_feereturn, bbb.f_registered DESC, bbb.f_examinated DESC ) AS rn
                   FROM (SELECT p1.id,
                                p1.id_patient,
                                p1.id_examfeeitem,
                                p1.examfeeitem_name,
                                p1.qty,
                                p1.f_registered,
                                p1.f_examinated,
                                p1.f_giveup,
                                p1.f_mark_feereturn
                         FROM md_peispatientfeeitem p1
                                  INNER JOIN (SELECT id_patient, id_examfeeitem
                                              FROM md_peispatientfeeitem
                                              WHERE f_mark_feereturn != 1
                                              GROUP BY id_patient, id_examfeeitem
                                              HAVING COUNT (*) > 1) aaa ON p1.id_patient = aaa.id_patient
                             AND p1.id_examfeeitem = aaa.id_examfeeitem
                         ORDER BY p1.id_patient,
                                  p1.id_examfeeitem,
                                  p1.f_mark_feereturn,
                                  p1.f_registered DESC,
                                  p1.f_examinated DESC) bbb) ccc
             WHERE ccc.rn > 1);

```