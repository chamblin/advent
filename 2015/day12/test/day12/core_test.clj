(ns day12.core-test
  (:require [clojure.test :refer :all]
            [day12.core :refer :all]))

(deftest test-add-numbers-in-string
  (testing "examples"
    (is (= 6 (add-numbers-in-string "{\"a\":2,\"b\":4")))
    (is (= 6 (add-numbers-in-string "[1,2,3]")))
    (is (zero? (add-numbers-in-string "{\"a\":[-1,1]}")))
    (is (zero? (add-numbers-in-string "[-1,{\"a\":1}]")))
    (is (zero? (add-numbers-in-string "[]")))
    (is (zero? (add-numbers-in-string "{}")))))

(deftest test-remove-objects-with-red
  (testing "examples"
    (is (= [] (remove-objects-with-red {"a" "red" "b" 2})))
    (is (= [2 3] (remove-objects-with-red {"b" 2 "c" 3})))
    (is (= [2]) (remove-objects-with-red {"a" {"c" "red" "d" 1} "b" 2}))
    (is (= [1 2 3 [4]] (remove-objects-with-red [1 2 3 {"b" 4}])))))
