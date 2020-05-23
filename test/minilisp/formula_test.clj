(ns minilisp.formula-test
  (:require [clojure.test :refer :all]
            [minilisp.formula :refer :all]))

(deftest to-atom-test
  (testing "Long"
    (is (= (to-atom "12345") 12345))))
(testing "Double"
  (is (= (to-atom "1.2345") 1.2345)))
(testing "Symbol"
  (is (= (to-atom "hoge") "hoge")))
(testing "Boolean Symbol"
  (is (= (to-atom "true") "true")))