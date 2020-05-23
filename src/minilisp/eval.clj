(ns minilisp.eval
  (:require [clojure.pprint :as pprint])
  (:require [minilisp.env :refer :all])
  (:require [minilisp.formula :refer :all]))

(declare evaluate)

(defn- eval-quote
  [s-form env]
  (let [[_ exp] s-form]
    exp))

(defn- eval-if
  [s-form env]
  (let [[_ condition conseq alt] s-form
        cond-result (evaluate env condition)]
    (if cond-result (evaluate env conseq)
        (do (evaluate env alt)))))

(defn- eval-set
  [s-form env]
  (let [[_ val-name exp] s-form
        evaluated (evaluate env exp)]
    (if-some [got (get-value env val-name)]
      (set-value env val-name evaluated))))

(defn- eval-define
  [s-form env]
  (let [[_ val-name exp] s-form
        evaluated (evaluate env exp)]
    (set-value env val-name evaluated)))

(defn- eval-lambda
  [s-form env]
  (let [[_ keys exp] s-form]
    (fn [& values]
      (let [next-env (make-Env-with-kv keys values env)]
        (evaluate next-env exp)))))

(defn- eval-begin
  [s-form env]
  (let [expressions (map (partial evaluate env) (rest s-form))]
    (last expressions)))

(defn evaluate
  [env s-forms]
  (cond
    (is-symbol? s-forms) (get-value env s-forms)
    (not (= (type s-forms) clojure.lang.PersistentVector)) s-forms
    :else
    (condp = (first s-forms)
      "quote" (eval-quote s-forms env)
      "if" (eval-if s-forms env)
      "define" (do (eval-define s-forms env) nil)
      "set!" (eval-set s-forms env)
      "begin" (eval-begin s-forms env)
      "lambda" (eval-lambda s-forms env)
      (let [expressions (map (partial evaluate env) s-forms)
            exp (first expressions)
            args (rest expressions)]
        (apply exp args)))))
