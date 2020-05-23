(ns minilisp.formula)

(defn to-atom
  "Convert string to integer or double or symbol(=others)"
  [raw-value]
  (let [value (read-string raw-value)
        value-type (type value)]
    (condp = value-type
      java.lang.Long value
      java.lang.Double value
      java.lang.Boolean value
      raw-value)))

(defn is-symbol?
  "Returns whether raw-value is symbol or not"
  [raw-value]
  (try
    (let [value-type (-> raw-value (read-string) (type))]
      (or (= value-type clojure.lang.Symbol) (= value-type java.lang.String)))
    (catch Exception ex false)))
