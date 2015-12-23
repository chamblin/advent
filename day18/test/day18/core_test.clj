(ns day18.core-test
  (:require [clojure.test :refer :all]
            [day18.core :refer :all]))

(deftest test-neighbors
  (testing "middle"
    (is (= '((0 0)
             (0 1)
             (0 2)
(1 0) (1 2) (2 0) (2 1) (2 2)) (neighbors '(1 1))))))
