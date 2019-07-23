# 14-simple-jpa-data-timestamp

## Index usage

```sql
-- WITH indices
EXPLAIN select * from triple where id_k1='k1' and id_k2 = 'k2' and id_k3 = 'k3';
-- PLAN:
SELECT
    "TRIPLE"."ID_END",
    "TRIPLE"."ID_K1",
    "TRIPLE"."ID_K2",
    "TRIPLE"."ID_K3",
    "TRIPLE"."ID_START",
    "TRIPLE"."VALUE"
FROM "PUBLIC"."TRIPLE"
    /* PUBLIC.KEYS_INDEX: ID_K3 = 'k3'
        AND ID_K1 = 'k1'
        AND ID_K2 = 'k2'
     */
WHERE ("ID_K3" = 'k3')
    AND (("ID_K1" = 'k1')
    AND ("ID_K2" = 'k2'))

-- use the primary key index
EXPLAIN select * from triple where id_k1='k1' and id_k2 = 'k2' and id_k3 = 'k3' and id_start = '2019-01-01T10:00:00' and id_end = '2019-01-01T12:00:00';
-- PLAN:
SELECT
    "TRIPLE"."ID_END",
    "TRIPLE"."ID_K1",
    "TRIPLE"."ID_K2",
    "TRIPLE"."ID_K3",
    "TRIPLE"."ID_START",
    "TRIPLE"."VALUE"
FROM "PUBLIC"."TRIPLE"
    /* PUBLIC.PRIMARY_KEY_9: ID_END = TIMESTAMP '2019-01-01 12:00:00'
        AND ID_START = TIMESTAMP '2019-01-01 10:00:00'
        AND ID_K3 = 'k3'
        AND ID_K1 = 'k1'
        AND ID_K2 = 'k2'
     */
WHERE ("ID_END" = TIMESTAMP '2019-01-01 12:00:00')
    AND (("ID_START" = TIMESTAMP '2019-01-01 10:00:00')
    AND (("ID_K3" = 'k3')
    AND (("ID_K1" = 'k1')
    AND ("ID_K2" = 'k2'))))

-- without indices
EXPLAIN select * from triple where id_k1='k1' and id_k2 = 'k2' and id_k3 = 'k3' order by id_start asc;
-- PLAN:
SELECT
    "TRIPLE"."ID_END",
    "TRIPLE"."ID_K1",
    "TRIPLE"."ID_K2",
    "TRIPLE"."ID_K3",
    "TRIPLE"."ID_START",
    "TRIPLE"."VALUE"
FROM "PUBLIC"."TRIPLE"
    /* PUBLIC.TRIPLE.tableScan */
WHERE ("ID_K3" = 'k3')
    AND (("ID_K1" = 'k1')
    AND ("ID_K2" = 'k2'))
ORDER BY 5
```

## Resources
- [Hibernate DateTime](https://www.baeldung.com/hibernate-date-time)
