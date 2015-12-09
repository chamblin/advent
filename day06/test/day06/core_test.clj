(ns day06.core-test
  (:require [clojure.test :refer :all]
            [day06.core :refer :all]))

(deftest test-make-square
  (testing "1x1"
    (is (= [[0]] (make-square 1 1 0))))
  (testing "2x2"
    (is (= [[1 1] [1 1]] (make-square 2 2 1))))
  (testing "3x3"
    (is (= [[3 3 3] [3 3 3] [3 3 3]] (make-square 3 3 3)))))

(deftest test-get-cell
  (testing "3x3"
    (is (= 1 (get-cell [[0 0 0] [0 1 0] [0 0 0]] '(1 1))))
    (is (= 1 (get-cell [[0 0 0] [1 0 0] [0 0 0]] '(0 1))))
    (is (= 1 (get-cell [[0 0 0] [0 0 0] [0 0 1]] '(2 2))))))

(deftest test-set-cell
  (testing "3x3"
    (is (= [[0 1] [0 0]] (set-cell (make-square 2 2 0) '(1 0) 1)))
    (is (= [[0 0 0] [0 0 0] [0 1 0]] (set-cell (make-square 3 3 0) '(1 2) 1)))))

(deftest test-toggle-cell
  (testing "3x3, middle"
    (is (= [[false false false] [false true false] [false false false]] (toggle-cell (make-square 3 3 false) '(1 1))))
    (is (= [[true true true] [true false true] [true true true]] (toggle-cell (make-square 3 3 true) '(1 1))))))

(deftest test-positions-between
  (testing "positions starting at 0, 0"
    (is (= '((0 0) (1 0) (0 1) (1 1)) (positions-between '(0 0) '(1 1))))
    (is (= '((0 0) (0 1) (0 2)) (positions-between '(0 0) '(0 2))))
    (is (= '((0 0) (1 0) (2 0)) (positions-between '(0 0) '(2 0))))))

(deftest test-parse-line
  (testing "which method?"
    (is (= :turn-on (first (parse-line "turn on 0,0 through 999,999"))))
    (is (= :turn-off (first (parse-line "turn off 499,499 through 500,500"))))
    (is (= :toggle (first (parse-line "toggle 0,0 through 999,0")))))
  (testing "first coords"
    (is (= '(400 500) (second (parse-line "turn off 400,500 through 999,999"))))
    (is (= '(250 260) (second (parse-line "toggle 250,260 through 999,999")))))
  (testing "second coords"
    (is (= '(400 500) (nth (parse-line "toggle 0,0 through 400,500") 2)))
    (is (= '(440 550) (nth (parse-line "toggle 9,10 through 440,550") 2)))))
