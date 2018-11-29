(ns day09.core-test
  (:require [clojure.test :refer :all]
            [day09.core :refer :all]))

(def example-distance-map (distance-map "London to Dublin = 464
London to Belfast = 518
Dublin to Belfast = 141"))
(def cities ["London" "Belfast" "Dublin"])

(deftest test-distance-map
  (testing "example"
    (is (= { "BelfastLondon" 518 "DublinLondon" 464 "BelfastDublin" 141 } example-distance-map))))

(deftest test-shortest-distance
  (testing "example"
    (is (= 605 (shortest-distance example-distance-map cities)))))
