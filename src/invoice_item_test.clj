(ns invoice_item_test
  (:require [invoice-item]))
(use 'clojure.test)

(deftest expect-double
  (is (instance? Double
                 (invoice-item/subtotal {:invoice-item/precise-quantity (int 4),
                                         :invoice-item/precise-price (int 400)}))))

(deftest discounted-values
  (is (= 1040.0
         (invoice-item/subtotal {:invoice-item/precise-quantity (int 4),
                                 :invoice-item/precise-price (int 400),
                                 :invoice-item/discount-rate (int 35)})))
  (is (= 90000000.0
         (invoice-item/subtotal {:invoice-item/precise-quantity (double 100),
                                 :invoice-item/precise-price (double 1000000),
                                 :invoice-item/discount-rate (double 10)})))
  (is (= 830000.0
         (invoice-item/subtotal {:invoice-item/precise-quantity (int 20000),
                                 :invoice-item/precise-price (float 50),
                                 :invoice-item/discount-rate (double 17)}))))

(deftest no-discounts
  (is (= 1600.0
         (invoice-item/subtotal {:invoice-item/precise-quantity (int 4),
                                 :invoice-item/precise-price (int 400)}))))

(deftest empty-invoice-item
  (is (thrown? NullPointerException (invoice-item/subtotal {})))
  (is (thrown? NullPointerException (invoice-item/subtotal nil))))

(deftest incorrect-values
  (is (thrown? ClassCastException (invoice-item/subtotal {:invoice-item/precise-quantity "asd",
                                                          :invoice-item/precise-price 400})))
  (is (thrown? ClassCastException (invoice-item/subtotal {:invoice-item/precise-quantity :asd,
                                                          :invoice-item/precise-price 400}))))

(run-tests 'invoice_item_test)