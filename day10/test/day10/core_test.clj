(ns day10.core-test
  (:require [clojure.test :refer :all]
            [day10.core :refer :all]))

(deftest test-look-and-say
  (testing "examples"
    (is (= "11" (look-and-say "1")))
    (is (= "21" (look-and-say "11")))
    (is (= "111221" (look-and-say "1211")))
    (is (= "312211" (look-and-say "111221")))))
