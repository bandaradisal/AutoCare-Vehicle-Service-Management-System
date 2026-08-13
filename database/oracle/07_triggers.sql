-- =========================================================
-- AutoCare Vehicle Service & Maintenance Management System
-- Script 07 - PL/SQL Triggers
-- =========================================================


-- ---------------------------------------------------------
-- 1. AUTO CALCULATE INVOICE ITEM SUBTOTAL
-- subtotal = quantity × unit price
-- ---------------------------------------------------------

CREATE OR REPLACE TRIGGER TRG_INVOICE_ITEM_SUBTOTAL
BEFORE INSERT OR UPDATE OF quantity, unit_price
ON INVOICE_ITEM
FOR EACH ROW
BEGIN

    :NEW.subtotal := :NEW.quantity * :NEW.unit_price;

END;
/

-- ---------------------------------------------------------
-- 2. CHECK AND REDUCE SPARE PART STOCK
-- Prevents using more parts than available
-- ---------------------------------------------------------

CREATE OR REPLACE TRIGGER TRG_REDUCE_SPARE_STOCK
BEFORE INSERT
ON INVOICE_ITEM
FOR EACH ROW

DECLARE
    v_stock NUMBER;

BEGIN

    SELECT quantity_in_stock
    INTO v_stock
    FROM SPARE_PART
    WHERE part_id = :NEW.part_id;

    IF v_stock < :NEW.quantity THEN

        RAISE_APPLICATION_ERROR(
            -20020,
            'Insufficient spare part stock.'
        );

    END IF;

    UPDATE SPARE_PART
    SET quantity_in_stock =
        quantity_in_stock - :NEW.quantity
    WHERE part_id = :NEW.part_id;

END;
/

-- ---------------------------------------------------------
-- 3. UPDATE INVOICE STATUS AFTER PAYMENT
-- Automatically changes invoice status
-- ---------------------------------------------------------

CREATE OR REPLACE TRIGGER TRG_UPDATE_INVOICE_PAYMENT_STATUS
FOR INSERT OR UPDATE OR DELETE
ON PAYMENT
COMPOUND TRIGGER

    v_invoice_id NUMBER;

    AFTER EACH ROW IS
    BEGIN

        IF INSERTING OR UPDATING THEN
            v_invoice_id := :NEW.invoice_id;

        ELSIF DELETING THEN
            v_invoice_id := :OLD.invoice_id;

        END IF;

    END AFTER EACH ROW;


    AFTER STATEMENT IS

        v_total_amount NUMBER;
        v_paid_amount  NUMBER;

    BEGIN

        IF v_invoice_id IS NOT NULL THEN

            SELECT total_amount
            INTO v_total_amount
            FROM INVOICE
            WHERE invoice_id = v_invoice_id;


            SELECT NVL(SUM(amount), 0)
            INTO v_paid_amount
            FROM PAYMENT
            WHERE invoice_id = v_invoice_id
              AND payment_status = 'Completed';


            IF v_paid_amount = 0 THEN

                UPDATE INVOICE
                SET invoice_status = 'Unpaid'
                WHERE invoice_id = v_invoice_id;


            ELSIF v_paid_amount < v_total_amount THEN

                UPDATE INVOICE
                SET invoice_status = 'Partially Paid'
                WHERE invoice_id = v_invoice_id;


            ELSE

                UPDATE INVOICE
                SET invoice_status = 'Paid'
                WHERE invoice_id = v_invoice_id;

            END IF;

        END IF;

    END AFTER STATEMENT;

END;
/