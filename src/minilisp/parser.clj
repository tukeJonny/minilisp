(ns minilisp.parser
  (:require [clojure.string :as str])
  (:require [clojure.pprint :as pprint])
  (:require [minilisp.formula :refer :all]))

(defn tokenize
  [text]
  (-> text
      (str/replace "(" "( ")
      (str/replace ")" " )")
      (str/split #" ")))

(declare parse)

(defn- make-s-form
  [tokens s-form]
  (if (= (first tokens) ")")
    [tokens s-form]
    (let [[new-tokens new-s-form] (parse tokens)]
      (make-s-form new-tokens (conj s-form new-s-form)))))

(defn parse
  [tokens]
  (if (= (count tokens) 0)
    (do (throw (ex-info "Unexpected EOF while reading" {:tokens tokens}))))
  (let [first-token (first tokens)
        rest-tokens (rest tokens)]
    (condp = first-token
      "(" (let [[new-rest-tokens new-s-form] (make-s-form rest-tokens [])]
            [(rest new-rest-tokens) new-s-form])
      ")" (throw (ex-info "Unexpected `)`" {:tokens tokens :first-token first-token}))
      [(rest tokens) (to-atom first-token)])))
