(ns day11.core-test
  (:require [clojure.test :refer :all]
            [day11.core :refer :all]))

(deftest test-succ
  (testing "succ"
    (is (= "aab" (succ "aaa")))
    (is (= "abb" (succ "aba")))
    (is (= "baa" (succ "azz")))))

(deftest test-valid-password
  (testing "examples"
    (is (not (valid-password "hijklmmn")))
    (is (not (valid-password "abbceffg")))
    (is (not (valid-password "abbcegjk")))
    (is (valid-password "abcdffaa"))
    (is (valid-password "ghjaabcc"))))

(deftest test-next-valid-password
  (testing "examples"
    (is (= "abcdffaa" (next-valid-password "abcdefgh")))
    (is (= "ghjaabcc" (next-valid-password "ghijklmn")))))
