(ns day15.core-test
  (:require [clojure.test :refer :all]
            [day15.core :refer :all]))

(deftest test-parser
  (testing "example"
    (is (= { :capacity -1 :durability -2 :flavor 6 :texture 3 :calories 8 } (parser "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8")))))

(deftest test-n-sequences-adding-to-m [n m]
  (testing "simple"
    (is (= ))))
