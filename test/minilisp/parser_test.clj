(ns minilisp.parser-test
  (:require [clojure.test :refer :all]
            [minilisp.parser :refer :all]))

(deftest tokenize-test
  (testing "basic"
    (let [text "(define a 2)"
          got (tokenize text)
          want ["(" "define" "a" "2" ")"]]
      (is (= got want)))))