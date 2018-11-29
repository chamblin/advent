(ns day05.core-test
  (:require [clojure.test :refer :all]
            [day05.core :refer :all]))

(deftest test-has-3-vowels
  (testing "aaa"
    (is (has-3-vowels "aaa")))

  (testing "abebob"
    (is (has-3-vowels "abebob")))

  (testing "abcabc"
    (is (not (has-3-vowels "abcabc")))))

(deftest test-has-repeated-letter
  (testing "aa"
    (is (has-repeated-letter "aa")))
  (testing "abab"
    (is (not (has-repeated-letter "abab"))))
  (testing "baa"
    (is (has-repeated-letter "baa")))
  (testing "abbc"
    (is (has-repeated-letter "abbc")))
  (testing "abb"
    (is (has-repeated-letter "abb"))))

(deftest test-has-no-dumb-pairs
  (testing "ab"
    (is (not (has-no-dumb-pairs "cabz")))
    (is (has-no-dumb-pairs "acbz")))
  (testing "cd"
    (is (not (has-no-dumb-pairs "cdrp")))
    (is (has-no-dumb-pairs "crdp")))
  (testing "pq"
    (is (not (has-no-dumb-pairs "pqpq")))
    (is (has-no-dumb-pairs "qppp")))
  (testing "xy"
    (is (not (has-no-dumb-pairs "xyza")))
    (is (has-no-dumb-pairs "xzya"))))

(deftest test-is-nice
  (testing "ugknbfddgicrmopn"
    (is (is-nice "ugknbfddgicrmopn")))
  (testing "aaa"
    (is (is-nice "aaa")))
  (testing "jchzalrnumimnmhp"
    (is (not (is-nice "jchzalrnumimnmhp"))))
  (testing "haegwjzuvuyypxyu"
    (is (not (is-nice "haegwjzuvuyypxyu"))))
  (testing "dvszwmarrgswjxmb"
    (is (not (is-nice "dvszwmarrgswjxmb")))))

(deftest test-has-2-letters-that-appear-twice
  (testing "xyxy"
    (is (has-2-letters-that-appear-twice "xyxy")))
  (testing "aabcdefgaa"
    (is (has-2-letters-that-appear-twice "aabcdefgaa")))
  (testing "aabcdefga"
    (is (not (has-2-letters-that-appear-twice "aabcdefga")))))

(deftest test-has-2-of-the-same-letter-with-some-letter-between-them
  (testing "xyx"
    (is (has-2-of-the-same-letter-with-some-letter-between-them "xyx")))
  (testing "abcdefeghi"
    (is (has-2-of-the-same-letter-with-some-letter-between-them "abcdefeghi")))
  (testing "aaa"
    (is (has-2-of-the-same-letter-with-some-letter-between-them "aaa")))
  (testing "abba"
    (is (not (has-2-of-the-same-letter-with-some-letter-between-them "abba")))))
