(ns invoice
  (:require [clojure.set :as set]))

(def invoice-mapping
  {"issue_date" :invoice/issue-date, "customer" :invoice/customer, "items" :invoice/items})
(def invoice-item-mapping
  {"price" :invoice-item/price, "quantity" :invoice-item/quantity, "sku" :invoice-item/sku, "taxes" :invoice-item/taxes})
(def invoice-item-taxes-mapping
  {"tax_category" :tax/category, "tax_rate" :tax/rate})
(def invoice-customer-mapping
  {"company_name" :customer/name, "email" :customer/email})


(defn parse-base
  [invoice]
  (set/rename-keys (select-keys invoice (keys invoice-mapping)) invoice-mapping))

(defn parse-item-taxes
  [{taxes :invoice-item/taxes :as invoice-item}]
  (assoc invoice-item :invoice-item/taxes (into [] (map #(set/rename-keys % invoice-item-taxes-mapping) taxes))))

(defn parse-items
  [{invoice-items :invoice/items :as invoice}]
  (->> invoice-items
       (map #(set/rename-keys % invoice-item-mapping))
       (map parse-item-taxes)
       (into [])
       (assoc invoice :invoice/items)))

(defn parse-customer
  [{customer :invoice/customer :as invoice}]
  (assoc invoice :invoice/customer (set/rename-keys customer invoice-customer-mapping)))

(defn parse-invoice
  [invoice]
  (->> invoice
       (parse-base)
       (parse-customer)
       (parse-items)))
