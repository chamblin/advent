(ns day07.core-test
  (:require [clojure.test :refer :all]
            [day07.core :refer :all]))

(deftest test-evaluate-expression
  (def xy-registers { "x" (fn [r] 123) "y" (fn [r] 456) })
  (testing "numbers"
    (is (= 25 ((evaluate-expression [:NUMBER "25"]) {}))))
  (testing "identifiers"
    (is (= 9000 ((evaluate-expression [:IDENTIFIER "adsf"])  { "adsf" (fn [registers] 9000) "fdsa" (fn [registers] 8000)}))))
  (testing "AND"
    (is (= 72 ((evaluate-expression [:OPERATED_EXPR [:IDENTIFIER "x"] [:SPACE " "] [:BINARY_OPERATOR "AND"] [:SPACE " "] [:IDENTIFIER "y"]]) xy-registers)))
    (is (= 72 ((evaluate-expression [:OPERATED_EXPR [:NUMBER "123"] [:SPACE " "] [:BINARY_OPERATOR "AND"] [:SPACE " "] [:IDENTIFIER "y"]]) xy-registers))))
  (testing "OR"
    (is (= 507 ((evaluate-expression [:OPERATED_EXPR [:IDENTIFIER "x"] [:SPACE " "] [:BINARY_OPERATOR "OR"] [:SPACE " "] [:IDENTIFIER "y"]]) xy-registers))))
  (testing "LSHIFT"
    (is (= 492 ((evaluate-expression [:OPERATED_EXPR [:IDENTIFIER "x"] [:SPACE " "] [:BINARY_OPERATOR "LSHIFT"] [:SPACE " "] [:NUMBER "2"]]) xy-registers))))
  (testing "RSHIFT"
    (is (= 114 ((evaluate-expression [:OPERATED_EXPR [:IDENTIFIER "y"] [:SPACE " "] [:BINARY_OPERATOR "RSHIFT"] [:SPACE " "] [:NUMBER "2"]]) xy-registers))))
  (testing "NOT"
    (is (= 65412 ((evaluate-expression [:NOT_EXPR [:NOT "NOT"] [:SPACE " "] [:IDENTIFIER "x"]]) xy-registers)))))

(deftest test-evaluate-statement
  (def xy-registers { "x" (fn [r] 123) "y" (fn [r] 456) })
  (defn get-register-value [register-id statement initial-registers]
    (let [registers (evaluate-statement initial-registers statement)]
      (examine-wire registers register-id)))
  (testing "simple"
    (is (= 100 (get-register-value "aa" (list [:EXPR [:NUMBER "100"]] [:SPACE " "] [:ARROW "->"] [:SPACE " "] [:IDENTIFIER "aa"]) {}))))
  (testing "complex"
    (is (= 72 (get-register-value "d" (list [:EXPR [:OPERATED_EXPR [:IDENTIFIER "x"] [:SPACE " "] [:BINARY_OPERATOR "AND"] [:SPACE " "] [:IDENTIFIER "y"]]] [:SPACE " "] [:ARROW "->"] [:SPACE " "] [:IDENTIFIER "d"]) xy-registers)))))

(def example-program "123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i")

(def out-of-order-program "x -> y
123 -> x" )

(deftest test-evaluate-program
  (defn get-register-value [register-id statements]
    (let [registers (evaluate-program statements)] ((registers register-id) registers)))
  (testing "example"
    (is (= 65412 (get-register-value "h" (statements-for-program (clojure.string/split-lines example-program))))))
  (testing "out of order"
    (is (= 123 (get-register-value "y" (statements-for-program (clojure.string/split-lines out-of-order-program)))))))
