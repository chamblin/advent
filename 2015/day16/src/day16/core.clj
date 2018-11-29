(ns day16.core
  (:gen-class)
  (:require [clojure.set :refer [subset?]])
  (:require [instaparse.core :as insta]))

(def parser (insta/parser "
LINE = 'Sue ' NUM ': ' SUEFACT ', ' SUEFACT ', ' SUEFACT
SUEFACT = #'[a-z]+' ': ' NUM
NUM = #'[0-9]+'
"))

(defn- parse-sue-fact [f sue-fact]
  (let [[_ category _ [_ qty]] sue-fact] (f category qty)))

(defn- parse-line-as-set [line]
  (let [[_ _ [_ sue-number] _ fact1 _ fact2 _ fact3] (parser line)]
    [sue-number (map (partial parse-sue-fact #(str %1 %2)) (list fact1 fact2 fact3))]))

(defn- parse-line-as-hash [line]
  (let [[_ _ [_ sue-number] _ fact1 _ fact2 _ fact3] (parser line)]
    [sue-number
     (into {} (map
               (partial parse-sue-fact #(vec (list %1 (Integer/parseInt %2))))
               (list fact1 fact2 fact3)))]))

(def mfcsam-results { "children"    3
                      "cats"        7
                      "samoyeds"    2
                      "pomeranians" 3
                      "akitas"      0
                      "vizslas"     0
                      "goldfish"    5
                      "trees"       3
                      "cars"        2
                      "perfumes"    1 })

(defn- mk-evidence-comparator [evidence f]
  (fn [possiboo]
    (or
     (not (contains? possiboo evidence))
     (f (possiboo evidence) (mfcsam-results evidence)))))

(defn is-the-right-sue? [possiboo]
  (every? #(% possiboo) (map (fn [[e c]] (mk-evidence-comparator e c))
                             (list ["cats" >]
                                   ["trees" >]
                                   ["pomeranians" <]
                                   ["goldfish" <]
                                   ["children" =]
                                   ["samoyeds" =]
                                   ["akitas" =]
                                   ["vizslas" =]
                                   ["cars" =]
                                   ["perfumes" =]))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [lines (->> (first args) slurp clojure.string/split-lines)]
   (println (filter #(subset?
                      (second %)
                      (set (map
                            (partial apply str)
                            (seq mfcsam-results))))
                    (map parse-line-as-set lines)))
   (println (filter #(is-the-right-sue? (second %))
                    (map parse-line-as-hash lines)))))
