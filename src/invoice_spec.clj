(ns invoice-spec
  (:require [clojure.spec.alpha :as s])
  (:require [clojure.data.json :as json])
  (:require [invoice])
  (:import [java.text SimpleDateFormat]))

(s/def :customer/name string?)
(s/def :customer/email string?)
(s/def :invoice/customer (s/keys :req [:customer/name
                                       :customer/email]))

(s/def :tax/rate double?)
(s/def :tax/category #{:iva})
(s/def ::tax (s/keys :req [:tax/category
                           :tax/rate]))
(s/def :invoice-item/taxes (s/coll-of ::tax :kind vector? :min-count 1))

(s/def :invoice-item/price double?)
(s/def :invoice-item/quantity double?)
(s/def :invoice-item/sku string?)

(s/def ::invoice-item
  (s/keys :req [:invoice-item/price
                :invoice-item/quantity
                :invoice-item/sku
                :invoice-item/taxes]))

(s/def :invoice/issue-date inst?)
(s/def :invoice/items (s/coll-of ::invoice-item :kind vector? :min-count 1))

(s/def ::invoice
  (s/keys :req [:invoice/issue-date
                :invoice/customer
                :invoice/items]))

(defn value-reader [key value]
  (case key
    "issue_date" (.parse (SimpleDateFormat. "dd/MM/yyyy") value)
    "tax_category" :iva
    "tax_rate" (double value)
    value))
(def invoice-json (json/read-str (slurp "invoice.json") :value-fn value-reader))

(println (s/valid? ::invoice (invoice/parse-invoice (get invoice-json "invoice"))))
