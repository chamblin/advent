(ns day03.core-test
  (:require [clojure.test :refer :all]
            [day03.core :refer :all]))

(deftest test-number-of-houses
  (testing "empty string"
    (is (= 1 (number-of-houses ""))))
  (testing "one direction"
    (is (= 2 (number-of-houses ">"))))
  (testing "square"
    (is (= 4 (number-of-houses "^>v<"))))
  (testing "lucky children"
    (is (= 2 (number-of-houses "^v^v^v^v^v")))))

(deftest test-string-alternator
  (testing "empty string"
    (is (= '("" "") (string-alternator ""))))
  (testing "4 chars"
    (is (= '("ac" "bd") (string-alternator "abcd"))))
  (testing "3 chars"
    (is (= '("ac" "b") (string-alternator "abc")))))

(deftest test-robo-santa-and-santa-number-of-houses
  (testing "empty string"
    (is (= 1) (robo-santa-and-santa-number-of-houses "")))
  (testing "two directions"
    (is (= 3) (robo-santa-and-santa-number-of-houses "^>v<")))
  (testing "ridiculous example"
    (is (= 11) (robo-santa-and-santa-number-of-houses "^v^v^v^v^v"))))
