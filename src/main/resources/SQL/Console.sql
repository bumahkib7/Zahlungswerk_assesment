SELECT m.name, SUM(pt.gross_amount) as turnover
FROM payment_transaction pt
JOIN Merchant m ON pt.merchant_id = m.merchant_id
WHERE EXTRACT(YEAR FROM pt.transaction_date) = 2022
GROUP BY m.merchant_id, m.name
ORDER BY turnover DESC
LIMIT 1;


SELECT c, COUNT(DISTINCT t.customer_id) as transaction_count
FROM payment_transaction t
JOIN Customer c ON t.customer_id = c.customer_id
WHERE EXTRACT(YEAR FROM t.transaction_date) = :year AND c.is_active = false
GROUP BY c.customer_id, c.date_of_registration, c.email, c.is_active, c.name
ORDER BY transaction_count DESC;
/*
    This query will return customer_id and how many num_transactions they have
*/
SELECT customer_id, COUNT(*) as num_transactions
                   FROM payment_transaction
                   WHERE EXTRACT(YEAR FROM transaction_date) = 2022 AND customer_id NOT IN (
                     SELECT customer_id FROM payment_transaction WHERE EXTRACT(YEAR FROM transaction_date) = 2023
                   )
                   GROUP BY customer_id
                   ORDER BY num_transactions DESC
                   LIMIT 5;
