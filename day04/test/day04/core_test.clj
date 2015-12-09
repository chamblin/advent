(ns day04.core-test
  (:require [clojure.test :refer :all]
            [day04.core :refer :all]))

(deftest test-advent-coin-gen
  (testing "abcdef"
    (is (= 609043 (advent-coin-gen "abcdef")))))

