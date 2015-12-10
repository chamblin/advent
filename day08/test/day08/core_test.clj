(ns day08.core-test
  (:require [clojure.test :refer :all]
            [day08.core :refer :all]))

(deftest test-evald-string-length
  (testing "simple cases"
    (is (= 0 (evald-string-length "\"\"")))
    (is (= 10 (evald-string-length "\"abcde12345\""))))
  (testing "escaped crap"
    (is (= 7 (evald-string-length "\"aaa\\\"aaa\"")))
    (is (= 3 (evald-string-length "\"w\\\\k\""))))
  (testing "hex'd chars"
    (is (= 7 (evald-string-length "\"daef\\x5fe\\x5b\"")))))
