(ns minilisp.env-test
  (:require [clojure.test :refer :all]
            [minilisp.env :refer :all]))

(deftest env-test
  (testing "treat integer"
    (let [env (make-Env nil)]
      (set-value env "test" 111)
      (is (= (get-value env "test") 111))))
  (testing "treat double"
    (let [env (make-Env nil)]
      (set-value env "test" 12.345)
      (is (= (get-value env "test") 12.345))))
  (testing "treat string"
    (let [env (make-Env nil)]
      (set-value env "test" "hello!")
      (is (= (get-value env "test") "hello!"))))
  (testing "non-existing key lookup"
    (let [env (make-Env nil)]
      (is (thrown? clojure.lang.ExceptionInfo (get-value env "hoge"))))))

(deftest outer-env-test
  (testing "refer 1-outer env kv"
    (let [outer-env (make-Env nil)]
      (set-value outer-env "outer-key" "outer-value")
      (let [env (make-Env outer-env)]
        (is (= (get-value env "outer-key") "outer-value")))))
  (testing "refer 2-outer env kv"
    (let [outer-env-1 (make-Env nil)]
      (set-value outer-env-1 "outer-key" "outer-value")
      (let [outer-env-2 (make-Env outer-env-1)]
        (let [env (make-Env outer-env-2)]
          (is (= (get-value env "outer-key") "outer-value"))))))
  (testing "refer multiple kv1"
    (let [outer-env-1 (make-Env nil)]
      (set-value outer-env-1 "outer-key" "outer-value")
      (let [outer-env-2 (make-Env outer-env-1)]
        (set-value outer-env-2 "outer-key2" "outer-value2")
        (let [env (make-Env outer-env-2)]
          (is (= (get-value env "outer-key") "outer-value"))
          (is (= (get-value env "outer-key2") "outer-value2"))))))
  (testing "refer multiple kv2"
    (let [outer-env-1 (make-Env nil)]
      (set-value outer-env-1 "outer-key" "outer-value")
      (let [outer-env-2 (make-Env outer-env-1)]
        (let [outer-env-3 (make-Env outer-env-2)]
          (set-value outer-env-2 "outer-key2" "outer-value2")
          (let [env (make-Env outer-env-3)]
            (is (= (get-value env "outer-key") "outer-value"))
            (is (= (get-value env "outer-key2") "outer-value2"))))))))

