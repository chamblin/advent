(ns day13.core-test
  (:require [clojure.test :refer :all]
            [day13.core :refer :all]))

(def simple "Alice would gain 54 happiness units by sitting next to Bob.
Alice would lose 79 happiness units by sitting next to Carol.
Bob would lose 0 happiness units by sitting next to Carol.")

(def example "Alice would gain 54 happiness units by sitting next to Bob.
Alice would lose 79 happiness units by sitting next to Carol.
Alice would lose 2 happiness units by sitting next to David.
Bob would gain 83 happiness units by sitting next to Alice.
Bob would lose 7 happiness units by sitting next to Carol.
Bob would lose 63 happiness units by sitting next to David.
Carol would lose 62 happiness units by sitting next to Alice.
Carol would gain 60 happiness units by sitting next to Bob.
Carol would gain 55 happiness units by sitting next to David.
David would gain 46 happiness units by sitting next to Alice.
David would lose 7 happiness units by sitting next to Bob.
David would gain 41 happiness units by sitting next to Carol.")

(deftest test-parse-names
  (testing "simple"
    (is (= '("Alice" "Bob" "Carol") (parse-names simple)))))

(deftest test-parse-scores
  (testing "simple"
    (is (= {"Alice" {"Bob" 54 "Carol" -79} "Bob" { "Carol" 0}} (parse-scores simple)))))

(deftest test-maximum-happiness-score
  (testing "example"
    (is (= 330 (maximum-happiness-score example)))))
