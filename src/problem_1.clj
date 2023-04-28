(ns problem_1)

(defn xor [a b] (and (or a b) (not (and a b))))
(def invoice-file (clojure.edn/read-string (slurp "invoice.edn")))

(defn filter-iva
  [invoice-item]
  (some #(= (:tax/rate %) 19) (:taxable/taxes invoice-item)))

(defn filter-ret-fuente
  [invoice-item]
  (some #(= (:retention/rate %) 1) (:retentionable/retentions invoice-item)))

(defn filter-invoice
  [invoice]
  (->>  (:invoice/items invoice)
        (filter #(xor (filter-iva %) (filter-ret-fuente %)))))

(println (filter-invoice invoice-file))
