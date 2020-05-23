(ns minilisp.core
  (:require [clojure.pprint :as pprint])
  (:require [minilisp.parser :refer :all])
  (:require [minilisp.env :refer :all])
  (:require [minilisp.eval :refer :all]))

(defn ^:private read-input
  []
  (print "minilisp > ")
  (flush)
  (read-line))

(def ^:private global-env
  (let [env (make-Env nil)]
    (set-value env "+" (fn [l r] (+ l r)))
    (set-value env "-" (fn [l r] (- l r)))
    (set-value env "*" (fn [l r] (* l r)))
    (set-value env "/" (fn [l r] (/ l r)))
    (set-value env "not" (fn [condition] (not condition)))
    (set-value env ">" (fn [l r] (> l r)))
    (set-value env "<" (fn [l r] (< l r)))
    (set-value env ">=" (fn [l r] (>= l r)))
    (set-value env "<=" (fn [l r] (<= l r)))
    (set-value env "=" (fn [l r] (= l r)))
    (set-value env "equal?" (fn [a b] (= a b)))
    (set-value env "eq?" (fn [a b] (= a b)))
    (set-value env "cons" (fn [x y] (conj [x] y)))
    (set-value env "car" (fn [x] (first x)))
    (set-value env "cdr" (fn [x] (rest x)))
    (set-value env "list" (fn [& elems] (apply list elems)))
    (set-value env "list?" (fn [coll] (list? coll)))
    (set-value env "length" (fn [coll] (count coll)))
    (set-value env "null?" (fn [coll] (= (count coll) 0)))
    (set-value env "symbol?" (fn [x] (or (= (type x) clojure.lang.Symbol) (= (type x) java.lang.String))))
    env))

(defn run-repl
  []
  (let [input (read-input)
        tokens (tokenize input)
        [_ s-forms] (parse tokens)
        result (evaluate global-env s-forms)]
    ; (pprint/pprint s-forms)
    (pprint/pprint result)
    (run-repl)))

(defn -main [& args]
  (run-repl))