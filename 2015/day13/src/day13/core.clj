(ns day13.core
  (:gen-class)
  (:require [instaparse.core :as insta]
            [clojure.math.combinatorics :as combo]))

(def parser
  (insta/parser
"STATEMENT = NAME ' would ' GAINLOSE ' ' POINTS ' happiness units by sitting next to ' NAME '.'
POINTS = #'[0-9]+'
GAINLOSE = 'gain' | 'lose'
NAME = #'[A-Za-z]+'"))

(defn parse-names [s]
  (-> (map (fn [[_ [_ me] _ _ _ _ _ [_ other] _]] [me other]) (map parser (clojure.string/split-lines s))) flatten distinct sort))

(defn parse-scores [s]
  (let [parsed-output (->> s clojure.string/split-lines (map parser))
        fix-parsed-line (fn [[_ [_ me] _ [_ gainlose] _ [_ points] _ [_ other] _]]
                          (list me
                                other
                                (* (if (= "gain" gainlose) 1 -1) (Integer/parseInt points))))
        relations (map fix-parsed-line parsed-output)]
    (reduce (fn [scores [me other points]] (assoc-in scores [me other] points)) {} relations)))

(defn happiness-for-arrangement [names scores]
  (let [number-of-guests (count names)
        left-neighbor (fn [pos] (nth names (mod (- pos 1) number-of-guests)))
        right-neighbor (fn [pos] (nth names (mod (+ pos 1) number-of-guests)))
        happiness-for-pair (fn [pair] (get-in scores pair))]
    (->> (map (fn [pos]
            (list
             (happiness-for-pair [(nth names pos) (left-neighbor pos)])
             (happiness-for-pair [(nth names pos) (right-neighbor pos)]))) (range number-of-guests)) flatten (apply +))))

(defn maximum-happiness-score [s]
  (let [scores (parse-scores s)
        names (parse-names s)]
    (apply max (map #(happiness-for-arrangement % scores) (combo/permutations names)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (maximum-happiness-score (slurp (first args)))))
